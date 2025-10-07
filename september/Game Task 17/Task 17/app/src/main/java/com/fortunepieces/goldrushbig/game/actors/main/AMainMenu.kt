package com.fortunepieces.goldrushbig.game.actors.main

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.fortunepieces.goldrushbig.game.actors.ABtns
import com.fortunepieces.goldrushbig.game.actors.button.AButton
import com.fortunepieces.goldrushbig.game.screens.MenuScreen
import com.fortunepieces.goldrushbig.game.utils.Block
import com.fortunepieces.goldrushbig.game.utils.TIME_ANIM_SCREEN
import com.fortunepieces.goldrushbig.game.utils.actor.animDelay
import com.fortunepieces.goldrushbig.game.utils.actor.animHide
import com.fortunepieces.goldrushbig.game.utils.actor.animShow
import com.fortunepieces.goldrushbig.game.utils.advanced.AdvancedMainGroup
import com.fortunepieces.goldrushbig.game.utils.gdxGame

var GLOB_REG: Drawable? = null
    private set

class AMainMenu(override val screen: MenuScreen): AdvancedMainGroup() {

    private val imgText  = Image(gdxGame.assetsAll.CAP)
    private val aBtns    = ABtns(screen)
    private val btnLeft  = AButton(screen, AButton.Type.Left)
    private val btnRight = AButton(screen, AButton.Type.Right)

    private val listRegions = gdxGame.assetsAll.listPuzle
    private val listImg     = List(3) { Image(listRegions[it]) }

    private var currentIndex = 2

    override fun addActorsOnGroup() {
        color.a = 0f

        addImgMenu()
        addBtnS()
        addBtnLeftRight()
        addImages()

        GLOB_REG = listImg[1].drawable

        animShowMain()
    }

    // Actors ------------------------------------------------------------------------

    private fun addImgMenu() {
        addActor(imgText)
        imgText.setBounds(420f, 904f, 1060f, 136f)
    }

    private fun addBtnS() {
        addActor(aBtns)
        aBtns.setBounds(638f, 126f, 645f, 173f)
    }

    private fun addBtnLeftRight() {
        addActor(btnLeft)
        btnLeft.setBounds(62f, 498f, 84f, 84f)
        addActor(btnRight)
        btnRight.setBounds(1774f, 498f, 84f, 84f)

        btnLeft.setOnClickListener {
            if (currentIndex - 1 >= 0) currentIndex-- else currentIndex = 5

            listImg[0].drawable = TextureRegionDrawable(listRegions[currentIndex])

            when(currentIndex) {
                5 -> {
                    listImg[1].drawable = TextureRegionDrawable(listRegions[0])
                    listImg[2].drawable = TextureRegionDrawable(listRegions[1])
                }
                4 -> {
                    listImg[1].drawable = TextureRegionDrawable(listRegions[5])
                    listImg[2].drawable = TextureRegionDrawable(listRegions[0])
                }
                else -> {
                    listImg[1].drawable = TextureRegionDrawable(listRegions[currentIndex+1])
                    listImg[2].drawable = TextureRegionDrawable(listRegions[currentIndex+2])
                }
            }

            GLOB_REG = listImg[1].drawable
        }
        btnRight.setOnClickListener {
            if (currentIndex + 1 <= 5) currentIndex++ else currentIndex = 0

            listImg[2].drawable = TextureRegionDrawable(listRegions[currentIndex])

            when(currentIndex) {
                0 -> {
                    listImg[0].drawable = TextureRegionDrawable(listRegions[4])
                    listImg[1].drawable = TextureRegionDrawable(listRegions[5])
                }
                1 -> {
                    listImg[0].drawable = TextureRegionDrawable(listRegions[5])
                    listImg[1].drawable = TextureRegionDrawable(listRegions[0])
                }
                else -> {
                    listImg[1].drawable = TextureRegionDrawable(listRegions[currentIndex-1])
                    listImg[0].drawable = TextureRegionDrawable(listRegions[currentIndex-2])
                }
            }

            GLOB_REG = listImg[1].drawable
        }
    }

    private fun addImages() {
        listImg.forEachIndexed { index, image ->
            addActor(image)
            when(index) {
                0 -> image.setBounds(229f, 349f, 412f, 412f)
                1 -> image.setBounds(694f, 347f, 532f, 532f)
                2 -> image.setBounds(1279f, 349f, 412f, 412f)
            }
        }
    }

    // Anim ------------------------------------------------

    override fun animShowMain(blockEnd: Block) {
//        screen.stageBack.root.animShow(TIME_ANIM_SCREEN)

        this.animShow(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

    override fun animHideMain(blockEnd: Block) {
//        screen.stageBack.root.animHide(TIME_ANIM_SCREEN)

        this.animHide(TIME_ANIM_SCREEN)
        this.animDelay(TIME_ANIM_SCREEN) { blockEnd.invoke() }
    }

}