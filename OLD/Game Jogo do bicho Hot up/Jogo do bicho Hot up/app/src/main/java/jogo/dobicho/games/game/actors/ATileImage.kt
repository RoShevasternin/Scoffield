package jogo.dobicho.games.game.actors

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import jogo.dobicho.games.game.utils.actor.animHide
import jogo.dobicho.games.game.utils.actor.animShow
import jogo.dobicho.games.game.utils.actor.disable
import jogo.dobicho.games.game.utils.actor.enable
import jogo.dobicho.games.game.utils.actor.setOnClickListener
import jogo.dobicho.games.game.utils.advanced.AdvancedGroup
import jogo.dobicho.games.game.utils.advanced.AdvancedScreen

class ATileImage(override val screen: AdvancedScreen, var tile: ATileGroup.Obj.Tile): AdvancedGroup() {

    private val TIME_ANIM = 0.25f
    private val default = screen.game.gameAssets.TILE

    private val img = Image(default)

    override fun addActorsOnGroup() {
        addAndFillActor(img)
    }

    fun animShowTile() {
        disable()
        animHide(TIME_ANIM) {
            img.drawable = TextureRegionDrawable(tile.region)
            animShow(TIME_ANIM)
        }
    }

    fun animDefault() {
        animHide(TIME_ANIM) {
            img.drawable = TextureRegionDrawable(default)
            animShow(TIME_ANIM) {
                enable()
            }
        }
    }

}