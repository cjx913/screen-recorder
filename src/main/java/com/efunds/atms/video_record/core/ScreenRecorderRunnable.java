package com.efunds.atms.video_record.core;

import lombok.Getter;
import lombok.Setter;
import org.bytedeco.javacv.FrameRecorder;

public abstract class ScreenRecorderRunnable implements Runnable {
    @Setter
    @Getter
    private FrameRecorder frameRecorder;

    @Setter
    @Getter
    private ScreenRecorderRunnableStatus status = ScreenRecorderRunnableStatus.STOP;

    @Setter
    @Getter
    private long startTime;
    @Setter
    @Getter
    private long pauseTime;
    @Setter
    @Getter
    private long goOnTime;
    @Setter
    @Getter
    private long stopTime;

    public ScreenRecorderRunnable() {
    }

    public ScreenRecorderRunnable(FrameRecorder frameRecorder) {
        this.frameRecorder = frameRecorder;
    }

    public enum ScreenRecorderRunnableStatus {
        PLAYING("playing"), PAUSE("pause"), STOP("stop");

        @Getter
        private String status;

        ScreenRecorderRunnableStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return this.status;
        }
    }
}
