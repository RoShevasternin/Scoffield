package com.obezana.playtocrypto.game.box2d

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.obezana.playtocrypto.game.utils.*
import com.obezana.playtocrypto.game.utils.actor.setBounds
import com.obezana.playtocrypto.game.utils.actor.setOrigin
import com.obezana.playtocrypto.game.utils.actor.setPosition
import com.obezana.playtocrypto.game.utils.advanced.AdvancedBox2dScreen
import com.obezana.playtocrypto.game.utils.advanced.AdvancedGroup
import com.obezana.playtocrypto.util.cancelCoroutinesAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class AbstractBody {
    abstract val screenBox2d  : AdvancedBox2dScreen
    abstract val name         : String
    abstract val bodyDef      : BodyDef
    abstract val fixtureDef   : FixtureDef

    open var actor : AdvancedGroup? = null
    open var id    : String         = BodyId.NONE
    open val collisionList          = mutableListOf<String>()

    val size     = Vector2()
    val position = Vector2()
    var coroutine: CoroutineScope? = null
        private set

    val scale  by lazy { size.x.toB2 }
    val center by lazy { screenBox2d.worldUtil.bodyEditor.getOrigin(name, scale) }
    var body: Body? = null
        private set

    var beginContactBlock: (AbstractBody) -> Unit = {}
    var endContactBlock  : (AbstractBody) -> Unit = {}
    var renderBlock      : (Float)        -> Unit = {}


    open fun render(deltaTime: Float) {
        renderActor()
        renderBlock(deltaTime)
    }

    open fun destroy() {
        if (body != null) {
            cancelCoroutinesAll(coroutine)
            actor?.dispose()
            actor = null
            collisionList.clear()

            with(screenBox2d.worldUtil) {
                world.destroyBody(body)
                body = null
            }
        }
    }

    open fun beginContact(contactBody: AbstractBody) {
        beginContactBlock(contactBody)
    }

    open fun endContact(contactBody: AbstractBody) {
        endContactBlock(contactBody)
    }



    private fun addActor() {
        actor?.apply {
            screenBox2d.stageUI.addActor(this)
            setBounds(position, size)
        }
    }

    private fun renderActor() {
        body?.let { b ->
            actor?.apply {
                setPosition(b.position.cpy().sub(center).toUI)
                setOrigin(center.cpy().toUI)
                rotation = b.angle * RADTODEG
            }
        }
    }

    private fun createBody() {
        body = screenBox2d.worldUtil.world.createBody(bodyDef).apply { userData = this@AbstractBody }
        screenBox2d.worldUtil.bodyEditor.attachFixture(body!!, name, fixtureDef, scale)
        actor?.let { addActor() }
    }


    fun create(layoutData: Layout.LayoutData) {
        create(layoutData.position(), layoutData.size())
    }

    fun create(x: Float, y: Float, w: Float, h: Float) {
        create(Vector2(x, y), Vector2(w, h))
    }

    fun create(position: Vector2, size: Vector2) {
        this.position.set(position)
        this.size.set(size)

        bodyDef.position.set(position.toB2.add(center))

        coroutine = CoroutineScope(Dispatchers.Default)
        createBody()
    }

    fun startDestroy(time: Long = 0) {
        coroutine?.launch {
            delay(time)
            screenBox2d.worldUtil.destroyBodyList.add(this@AbstractBody)
        }
    }

}