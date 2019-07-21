package com.efunds.atms.video_record.core;

/**
 * 录屏接口
 */
public interface ScreenRecorder {
    /**
     * 开始
     */
    void start();

    /**
     * 暂停
     */
    void pause();

    /**
     * 继续
     */
    void goOn();

    /**
     * 停止
     */
    void stop();
}
