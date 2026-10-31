package com.fongmi.android.ok.playback;

import com.fongmi.android.ok.bean.Result;

public record PlaybackResult<T>(T request, Result result) {
}
