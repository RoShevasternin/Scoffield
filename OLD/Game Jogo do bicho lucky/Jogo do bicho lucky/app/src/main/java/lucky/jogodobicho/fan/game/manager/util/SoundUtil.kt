package lucky.jogodobicho.fan.game.manager.util

import com.badlogic.gdx.audio.Sound
import lucky.jogodobicho.fan.game.manager.AudioManager
import lucky.jogodobicho.fan.game.manager.SoundManager
import lucky.jogodobicho.fan.game.utils.runGDX

class SoundUtil {

    // Common
    val soft_notice = SoundManager.EnumSound.soft_notice.data.sound
    val success_fanfare = SoundManager.EnumSound.success_fanfare.data.sound
    val violin_lose = SoundManager.EnumSound.violin_lose.data.sound

    var volumeLevel = AudioManager.volumeLevelPercent / 100f

    var isPause = (volumeLevel <= 0f)

    fun play(sound: Sound) = runGDX { if (isPause.not()) sound.play(volumeLevel) }
}