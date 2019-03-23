package com.hearatale.bw2000.service;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.hearatale.bw2000.Application;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AudioPlayerHelper {
    private static final String EXTENSION_FILE = ".mp3";

    private static volatile AudioPlayerHelper Instance = null;

    MediaPlayer mMediaPlayer;
    Handler mHandlerDelay;
    Runnable mRunnableDelay;
    MediaPlayer mIdiomPlayer;


    private AudioPlayerHelper() {
        mHandlerDelay = new Handler();
        mMediaPlayer = new MediaPlayer();
    }

    public static AudioPlayerHelper getInstance() {
        AudioPlayerHelper localInstance = Instance;
        if (localInstance == null) {
            synchronized (AudioPlayerHelper.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new AudioPlayerHelper();
                }
            }
        }
        return localInstance;
    }

    public void playAudioWithPrefix(String prefixPath, int position) {
        AssetManager assets = Application.Context.getAssets(); // get app's AssetManager
        try {
            //get all the path of the files inside Animals folder
            String[] path = assets.list(prefixPath + "/sounds");
            // get an InputStream to the asset representing the next item
            //provide access to the file
            playAudio(prefixPath + "/sounds/" + path[position]);
        } // end try
        catch (IOException e) {

        } // end catch
    }

    public void playAudioWithPrefix(String prefixPath, String fileName) {
        String pathAudio = prefixPath + fileName + EXTENSION_FILE;
        playAudio(pathAudio, null);
    }

    public void playAudioWithPrefix(String prefixPath, String fileName, DonePlayingListener listener) {
        String pathAudio = prefixPath + fileName + EXTENSION_FILE;
        playAudio(pathAudio, listener);
    }

    /**
     * Play audio has stop last play
     *
     * @param pathAudio start from assets with assets/<pathAudio>
     */
    public void playAudio(String pathAudio) {
        playAudio(pathAudio, null);
    }


    public void playAudio(String pathAudio, @Nullable final DonePlayingListener listener) {
        try {
            stopPlayer();
            prepareDataForPlayer(pathAudio);
            mMediaPlayer.start();
            mRunnableDelay = new Runnable() {
                @Override
                public void run() {
                    stopPlayer();
                    if (listener != null) {
                        listener.donePlaying();
                    }
                }
            };
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                    if (listener != null) {
                        listener.donePlaying();
                    }
                }
            });
        } catch (IOException e) {
            stopPlayer();
            if (listener != null) {
                listener.donePlaying();
            }
            e.printStackTrace();
        }
    }

    private void prepareDataForPlayer(String pathFile) throws IOException {
        try {
            AssetFileDescriptor afd = Application.Context.getAssets().openFd(pathFile);
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mMediaPlayer.prepare();
        }catch (FileNotFoundException ex)
        {
            Log.d("FileNotFoundException",pathFile);
        }
    }


    private int convertSecondStringToMilliSeconds(String durationString) {
        if (TextUtils.isEmpty(durationString)) return 0;
        return (int) (Float.parseFloat(durationString) * 1000);
    }

    public void stopPlayer() {
//        mHandlerDelay.removeCallbacks(mRunnableDelay);
        if (null != mHandlerDelay) {
            mHandlerDelay.removeCallbacksAndMessages(null);
        }
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.reset();
    }

    public void playCorrect(CompletedListener listener) {
        playNewPlayer("Sounds/correct", listener);

    }

    public void playInCorrect() {
        playNewPlayer("Sounds/incorrect", null);
    }


    public void playIdiom(String path, final CompletedListener listener) {
        mIdiomPlayer = new MediaPlayer();
        try {
            AssetFileDescriptor afd = Application.Context.getAssets().openFd(path + EXTENSION_FILE);
            mIdiomPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mIdiomPlayer.prepare();
            mIdiomPlayer.start();
            mIdiomPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopIdiom();
                    if (null != listener) {
                        listener.onCompleted();
                    }
                }
            });
        } catch (IOException e) {
            stopIdiom();
            if (null != listener) {
                listener.onCompleted();
            }
            e.printStackTrace();
        }
    }

    public void stopIdiom() {
        if (null != mIdiomPlayer) {
            if (mIdiomPlayer.isPlaying()) {
                mIdiomPlayer.stop();
            }
            mIdiomPlayer.reset();
            mIdiomPlayer.release();
            mIdiomPlayer = null;
        }
    }


    private void playNewPlayer(String path, final CompletedListener listener) {
        try {
            final MediaPlayer mediaPlayer = new MediaPlayer();
            AssetFileDescriptor afd = Application.Context.getAssets().openFd(path + EXTENSION_FILE);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.release();
                    if (null != listener) {
                        listener.onCompleted();
                    }
                }
            });
        } catch (IOException e) {
            stopPlayer();
            if (null != listener) {
                listener.onCompleted();
            }
            e.printStackTrace();
        }
    }

    public void playWithOffset(String path, int offset) {
        try {
            stopPlayer();
            prepareDataForPlayer(path + EXTENSION_FILE);
            mMediaPlayer.seekTo(offset);
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        } catch (IOException e) {
            stopPlayer();
            e.printStackTrace();
        }
    }

    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }


    /**
     * Listener done playing after delay time
     */
    public interface DonePlayingListener {
        void donePlaying();
    }


    /**
     * Listener play to end file audio
     */
    public interface CompletedListener {
        void onCompleted();
    }

}
