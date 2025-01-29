package org.matrixsukhoi.voidmei.prog

import org.matrixsukhoi.voidmei.VoidMeiMain
import org.matrixsukhoi.voidmei.logger
import org.matrixsukhoi.voidmei.prog.VoiceWarning.playCompleted
import java.io.File
import java.io.IOException
import javax.sound.sampled.*
import kotlin.math.log10


class AudioClip(path: String, coolDownSeconds: Long) {
    var clip: Clip?
    var cnt: Int = 0
    var isAct: Boolean
    var lastTimePlay: Long = 0
    var coolDown: Long
    var available: Boolean

    init {
        val audioFile = File(path)
        playCompleted = false
        var audioClip: Clip? = null
        available = true
        try {
            val audioStream = AudioSystem.getAudioInputStream(audioFile)

            val format = audioStream.format

            val info = DataLine.Info(Clip::class.java, format)

            audioClip = AudioSystem.getLine(info) as Clip

            // audioClip.addLineListener(this);
            audioClip.open(audioStream)

            val gainControl = audioClip.getControl(FloatControl.Type.MASTER_GAIN) as FloatControl
            val range = gainControl.maximum - gainControl.minimum
            // VoidMeiMain.debugPrint(range);
            //
            val rangen = 0 - gainControl.minimum
            val rangep = gainControl.maximum - 0
            var value = 0.0f
            if (VoidMeiMain.voiceVolumn <= 100) {
                value = gainControl.minimum + log10(VoidMeiMain.voiceVolumn.toDouble()).toFloat() * rangen / 2.0f
                if (value < gainControl.minimum) value = gainControl.minimum
            }

            // 大于100属于增益
            if (VoidMeiMain.voiceVolumn > 100) {
                value = (VoidMeiMain.voiceVolumn - 100) * rangep / 100.0f
                if (value > gainControl.maximum) value = gainControl.maximum
            }

            // VoidMeiMain.debugPrint(val);
            if (gainControl != null) gainControl.value = value

            // Math.log10(VoidMeiMain.voiceVolumn)/2.0f;
            // 映射使用log方式
            // f(x) [0, 200] -> [0, ]; 越接近1的越密

            // audioClip.start();

            // audioClip.close();
        } catch (ex: UnsupportedAudioFileException) {
            logger.warn { "The specified audio file is not supported: $path" }
            // ex.printStackTrace();
            available = false
        } catch (ex: LineUnavailableException) {
            logger.warn { "Audio line for playing back is unavailable: $path" }
            // ex.printStackTrace();
            available = false
        } catch (ex: IOException) {
            logger.warn { "Error playing the audio file: $path" }
            // ex.printStackTrace();
            available = false
        }
        // 获得clip
        this.clip = audioClip
        this.cnt = 0
        this.isAct = false
        this.coolDown = coolDownSeconds * 1000
    }

    fun playOnce(time: Long) {
        if (!this.isPlaying(time)) {
            this.isAct = true
            this.lastTimePlay = time

            // this.clip.stop();
            if (!this.available) return
            if (cnt++ == 0) clip!!.start()
            else clip!!.loop(1)
            // this.clip.start();
            // cnt++;
            // VoidMeiMain.debugPrint(cnt);
        }
    }

    fun isPlaying(time: Long): Boolean {
        if (!this.available) return true
        if (this.isAct) {
            if (time - this.lastTimePlay <= this.coolDown) {
                return true
            }
            this.isAct = clip!!.isRunning
            return this.isAct
        } else return this.isAct
    }

    fun close() {
        if (!this.available) clip!!.close()
        this.clip = null
    }
}