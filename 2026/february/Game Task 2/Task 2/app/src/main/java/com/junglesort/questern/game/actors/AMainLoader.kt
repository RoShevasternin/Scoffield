package com.junglesort.questern.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.junglesort.questern.game.screens.LoaderScreen
import com.junglesort.questern.game.utils.Acts
import com.junglesort.questern.game.utils.advanced.AdvancedGroup
import com.junglesort.questern.game.utils.gdxGame

class AMainLoader(override val screen: LoaderScreen): AdvancedGroup() {

    private val loaderImg = Image(gdxGame.assetsLoader.loader)

    override fun addActorsOnGroup() {
        addLoader()
    }

    // Actors ------------------------------------------------------------------------

    private fun addLoader() {
        addActor(loaderImg)
        loaderImg.apply {
            setBounds(307f, 726f, 467f, 467f)
            setOrigin(Align.center) // Обов'язково для симетричного збільшення
            addAction(Acts.forever(Acts.rotateBy(360f, 2f)))
        }
    }

}