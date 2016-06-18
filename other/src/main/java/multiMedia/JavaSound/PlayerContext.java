package multiMedia.JavaSound;

import com.sun.media.sound.JavaSoundAudioClip;

import java.io.IOException;
import java.io.InputStream;

public class PlayerContext {
    private static JavaSoundAudioClip player;

    private static class sigleInstance {
        static PlayerContext instance = new PlayerContext();
    }

    public static PlayerContext getInstance() {
        return sigleInstance.instance;
    }

    public void play() throws IOException {
        if (player != null) player.play();
    }

    /**
     * 停止，暂停
     *
     * @param stop 停止 stop
     *             暂停 push
     */
    public void playStop(String stop) {
        if ("stop".equals(stop) && this.player != null) {
            player.stop();
            player = null;
        }
        if ("push".equals(stop) && this.player != null) {
            player.stop();
        }
    }

    /**
     * 设置音频文件
     *
     * @param file
     * @throws IOException
     */
    public void setFile(InputStream file) throws IOException {
        if (file == null) return;
        player = new JavaSoundAudioClip(file);
    }
}
