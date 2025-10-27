package fortunetiger.jogos.tighrino.game.manager.util

import com.badlogic.gdx.audio.Sound
import fortunetiger.jogos.tighrino.game.manager.AudioManager
import fortunetiger.jogos.tighrino.game.manager.SoundManager
import fortunetiger.jogos.tighrino.game.utils.runGDX

class SoundUtil {

    // Common
    val BTN_CLICK   = SoundManager.EnumSound.BTN_CLICK.data.sound
    val LOSE        = SoundManager.EnumSound.LOSE.data.sound
    val THING_TOUCH = SoundManager.EnumSound.THING_TOUCH.data.sound
    val VICTORY     = SoundManager.EnumSound.VICTORY.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound) = runGDX { if (isPause.not()) sound.play(volumeLevel / 100f) }
}