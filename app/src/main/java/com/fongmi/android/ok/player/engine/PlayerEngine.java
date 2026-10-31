package com.fongmi.android.ok.player.engine;

import androidx.media3.common.PlaybackException;
import androidx.media3.common.Player;

import com.fongmi.android.ok.bean.Sub;
import com.fongmi.android.ok.player.effect.PlayerEffect;
import com.fongmi.android.ok.player.media.PlaySpec;

public interface PlayerEngine {

    int SOFT = 0;
    int HARD = 1;

    Type getType();

    Player getPlayer();

    int getAudioChannelCount();

    void release();

    void setDecode(int decode);

    default PlayerEffect getEffect() {
        return PlayerEffect.NONE;
    }

    void start(PlaySpec spec, long startPositionMs);

    default void preload(PlaySpec spec, long startPositionMs) {
    }

    default void clearPreload() {
    }

    void stop();

    default void setSubtitleStyle() {
    }

    default boolean addSubtitle(Sub sub) {
        return false;
    }

    String getErrorMessage(PlaybackException e);

    ErrorAction handleError(PlaybackException e);

    enum ErrorAction {
        RECOVERED,
        DECODE,
        FATAL
    }

    enum Type {
        EXO,
        MPV
    }
}
