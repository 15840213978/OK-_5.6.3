package com.fongmi.android.ok.event;

import com.fongmi.android.ok.bean.Config;
import com.fongmi.android.ok.bean.Device;
import com.fongmi.android.ok.bean.History;

import org.greenrobot.eventbus.EventBus;

public record CastEvent(Config config, Device device, History history) {

    public static void post(Config config, Device device, History history) {
        EventBus.getDefault().post(new CastEvent(config, device, history));
    }
}
