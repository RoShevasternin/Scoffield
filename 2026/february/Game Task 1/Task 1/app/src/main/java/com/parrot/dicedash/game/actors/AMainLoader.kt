package com.parrot.dicedash.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import com.parrot.dicedash.game.screens.LoaderScreen
import com.parrot.dicedash.game.utils.Acts
import com.parrot.dicedash.game.utils.advanced.AdvancedGroup
import com.parrot.dicedash.game.utils.gdxGame

class AMainLoader(override val screen: LoaderScreen): AdvancedGroup() {

    private val imgA    = Image(gdxGame.assetsLoader.a)
    private val imgB    = Image(gdxGame.assetsLoader.b)
    private val imgUp   = Image(gdxGame.assetsLoader.up)

    val aProgressLoader    = AProgressLoader(screen)
    private val loadingImg = Image(gdxGame.assetsLoader.loading)
    private val loaImg     = Image(gdxGame.assetsLoader.loa)

    override fun addActorsOnGroup() {
        //addLogo()
        //addUp()
        //addAB()

        addActor(loaImg)
        loaImg.setBounds(393f, 813f, 294f, 294f)
        loaImg.setOrigin(Align.center)
        loaImg.addAction(Acts.forever(Acts.rotateBy(-360f, 2.3f)))
    }

    // Actors ------------------------------------------------------------------------

    private fun addLogo() {
        addActor(aProgressLoader)
        aProgressLoader.setBounds(174f, 1582f, 731f, 116f)

        addActor(loadingImg)
        loadingImg.setBounds(424f, 1610f, 230f, 66f)
    }

    private fun addUp() {
        addActor(imgUp)
        imgUp.setBounds(421f, 367f, 238f, 507f)
          //  setOrigin(Align.center or Align.bottom)
          //  addAction(Acts.forever(Acts.sequence(
          //      Acts.scaleTo(0.98f, 0.98f, 0.55f),
          //      Acts.scaleTo(1.0f, 1.0f, 0.55f),
          //  )))
    }

    private fun addAB() {
        addActors(imgA, imgB)
        imgA.setBounds(-52f, 455f, 842f, 1154f)
        imgB.setBounds(504f, -44f, 624f, 1006f)

        imgA.setOrigin(Align.left or Align.bottom)
        imgA.addAction(Acts.forever(Acts.sequence(
            Acts.scaleTo(0.99f, 0.99f, 0.55f),
            Acts.scaleTo(1.0f, 1.0f, 0.55f),
        )))
        imgB.setOrigin(Align.right or Align.bottom)
        imgB.addAction(Acts.forever(Acts.sequence(
            Acts.scaleTo(0.99f, 0.99f, 0.55f),
            Acts.scaleTo(1.0f, 1.0f, 0.55f),
        )))
    }

}