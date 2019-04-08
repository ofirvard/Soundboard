package com.example.soundboard;

import android.media.AudioManager;
import android.media.MediaPlayer;

public class SoundHandler
{
    private MediaPlayer mediaPlayer;

    public SoundHandler()
    {
        if (this.mediaPlayer == null)
        {
            this.mediaPlayer = new MediaPlayer();

            this.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        else
            this.mediaPlayer.reset();
    }

    void play(String path)
    {
        try
        {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void reset()
    {
        mediaPlayer.stop();
        mediaPlayer.reset();
    }
}
