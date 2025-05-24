package com.bonanza.pazzleground.game.actors.checkbox

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.bonanza.pazzleground.game.manager.util.SoundUtil
import com.bonanza.pazzleground.game.utils.advanced.AdvancedGroup
import com.bonanza.pazzleground.game.utils.advanced.AdvancedScreen
import com.bonanza.pazzleground.game.utils.gdxGame
import com.bonanza.pazzleground.game.utils.region
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.set
import kotlin.math.round

open class ACheckBox(
    override val screen: AdvancedScreen,
    type: Type? = null,
) : AdvancedGroup() {

    private val defaultImage by lazy {  if (type != null) Image(getStyleByType(type).default) else Image() }
    private val checkImage   by lazy { (if (type != null) Image(getStyleByType(type).checked) else Image()).apply { isVisible = false } }

    private var onCheckBlock: (Boolean) -> Unit = { }

    private var isInvokeCheckBlock: Boolean = true
    var checkBoxGroup: ACheckBoxGroup? = null

    val checkFlow = MutableStateFlow(false)

    private var radius = 0

    override fun addActorsOnGroup() {
        addAndFillActors(getActors())
        addListener(getListener())
        asyncCollectCheckFlow()
    }

    private fun getActors() = listOf<Actor>(
        defaultImage,
        checkImage,
    )

    private fun asyncCollectCheckFlow() {
        coroutine?.launch { checkFlow.collect { isCheck -> if (isInvokeCheckBlock) onCheckBlock(isCheck) } }
    }

    open fun getListener() = object : InputListener() {

        val touchPointDown = Vector2()
        val touchPointUp   = Vector2()

        var isWithin = false

        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            touchPointDown.set(round(x), round(y))
            touchDragged(event, x, y, pointer)
            return true
        }

        override fun touchDragged(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            isWithin = x in 0f..width && y in 0f..height
        }

        override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            touchPointUp.set(round(x), round(y))

            if (isWithin) {
                if (touchPointDown.x in (touchPointUp.x - radius..touchPointUp.x + radius) &&
                    touchPointDown.y in (touchPointUp.y - radius..touchPointUp.y + radius)) {
                    gdxGame.soundUtil.apply { play(click) }

                    if (checkBoxGroup != null) {
                        if (checkFlow.value.not()) check()
                    } else {
                        if (checkFlow.value) uncheck() else check()
                    }

                }
            }
        }
    }

    fun check(isInvokeCheckBlock: Boolean = true) {
        this.isInvokeCheckBlock = isInvokeCheckBlock

        checkBoxGroup?.let {
            it.currentCheckedCheckBox?.uncheck()
            it.currentCheckedCheckBox = this
        }

        defaultImage.isVisible = false
        checkImage.isVisible   = true

        checkFlow.value = true
    }

    fun uncheck(isInvokeCheckBlock: Boolean = true) {
        this.isInvokeCheckBlock = isInvokeCheckBlock

        defaultImage.isVisible = true
        checkImage.isVisible   = false

        checkFlow.value = false
    }

    fun checkAndDisable() {
        check()
        disable()
    }

    fun uncheckAndEnabled() {
        uncheck()
        enable()
    }

    fun enable() {
        touchable = Touchable.enabled
    }

    fun disable() {
        touchable = Touchable.disabled
    }

    fun setStyle(style: ACheckBoxStyle) {
        defaultImage.drawable = TextureRegionDrawable(style.default)
        checkImage.drawable   = TextureRegionDrawable(style.checked)
    }

    fun setOnCheckListener(radius: Int = 100, block: (Boolean) -> Unit) {
        this.radius = radius
        onCheckBlock = block
    }

    fun getStyleByType(type: Type) = when(type) {
        Type.MUSIC -> ACheckBoxStyle(
            default = gdxGame.assetsAll.mdef,
            checked = gdxGame.assetsAll.mcheck,
        )
        Type.PAUSE -> ACheckBoxStyle(
            default = gdxGame.assetsAll.pausedef,
            checked = gdxGame.assetsAll.pausecheck,
        )
    }

    // ---------------------------------------------------
    // Style
    // ---------------------------------------------------

    data class ACheckBoxStyle(
        val default: TextureRegion,
        val checked: TextureRegion,
    )

    enum class Type {
        MUSIC, PAUSE
    }

}