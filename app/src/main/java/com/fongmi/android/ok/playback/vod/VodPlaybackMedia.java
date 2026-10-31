package com.fongmi.android.ok.playback.vod;

import androidx.media3.common.MediaMetadata;

import com.fongmi.android.ok.api.DanmakuApi;
import com.fongmi.android.ok.api.config.VodConfig;
import com.fongmi.android.ok.bean.Danmaku;
import com.fongmi.android.ok.bean.Episode;
import com.fongmi.android.ok.bean.History;
import com.fongmi.android.ok.bean.Result;
import com.fongmi.android.ok.player.media.MediaItemFactory;
import com.fongmi.android.ok.setting.DanmakuSetting;

import java.util.function.Consumer;

public final class VodPlaybackMedia {

    public static MediaMetadata metadata(History history, Episode episode) {
        String title = history.getVodName();
        String name = episode.getName();
        if (name.equals(title)) name = "";
        return MediaItemFactory.buildMetadata(title, name, history.getVodPic(), name);
    }

    public static void searchDanmaku(Result result, History history, Episode episode, Consumer<Danmaku> set, Consumer<Danmaku> add) {
        if (!DanmakuApi.canSearch()) return;
        if (VodConfig.get().getSite(result.getKey()).getDanmaku() == 0) return;
        DanmakuApi.search(history.getVodName(), episode.getName(), danmaku -> {
            if (DanmakuSetting.isSpiderFirst() && !result.getDanmaku().isEmpty()) add.accept(danmaku);
            else set.accept(danmaku);
        });
    }
}
