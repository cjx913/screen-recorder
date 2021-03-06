package cn.cjx913.video_record.core;

import cn.cjx913.video_record.exception.ScreenRecorderException;
import cn.cjx913.video_record.util.FrameRecorderUtil;
import lombok.Getter;
import lombok.Setter;
import org.bytedeco.javacv.FrameRecorder;

import java.awt.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Getter
public abstract class AbstractScreenRecorder implements ScreenRecorder {
    /**
     * 帧率
     */
    private double frameRate;

    /**
     * 截图区域
     */
    @Setter
    @Getter
    protected Rectangle screenshotRectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

    /**
     * 录屏线程
     */
    private ScreenRecorderRunnable screenRecorderRunnable;
    /**
     * 录屏的任务执行器
     */
    private ScheduledThreadPoolExecutor videoExecutor = new ScheduledThreadPoolExecutor(1);

    public AbstractScreenRecorder() {
    }

    public AbstractScreenRecorder(ScreenRecorderRunnable screenRecorderRunnable) {
        if (screenRecorderRunnable == null) {
            throw new ScreenRecorderException("screenRecorderRunnable can be null");
        }
        setScreenRecorderRunnable(screenRecorderRunnable);
    }


    @Override
    public void start() {
        if (screenRecorderRunnable == null) {
            throw new ScreenRecorderException("screenRecorderRunnable can be null");
        }
        System.out.println("-------start------");
        if (ScreenRecorderRunnable.ScreenRecorderRunnableStatus.STOP.equals(screenRecorderRunnable.getStatus())) {
            //记录开始时间
            screenRecorderRunnable.setStartTime(System.currentTimeMillis());
            screenRecorderRunnable.setPauseTime(0L);
            screenRecorderRunnable.setGoOnTime(0L);
            screenRecorderRunnable.setStopTime(0L);
            screenRecorderRunnable.setTotalPauseTime(0L);
            //设置任务状态
            screenRecorderRunnable.setStatus(ScreenRecorderRunnable.ScreenRecorderRunnableStatus.PLAYING);
            //frameRecorder设置启动
            FrameRecorderUtil.start(screenRecorderRunnable.getFrameRecorder());
            //执行任务
            videoExecutor.scheduleAtFixedRate(screenRecorderRunnable, (long) (1000 / frameRate), (long) (1000 / frameRate), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void pause() {
        System.out.println("-------pause------");
        if (ScreenRecorderRunnable.ScreenRecorderRunnableStatus.PLAYING.equals(screenRecorderRunnable.getStatus())) {
            if (screenRecorderRunnable != null && !videoExecutor.isShutdown()) {
                screenRecorderRunnable.setStatus(ScreenRecorderRunnable.ScreenRecorderRunnableStatus.PAUSE);
                screenRecorderRunnable.setPauseTime(System.currentTimeMillis());
            }
        }
    }

    @Override
    public void goOn() {
        System.out.println("-------goOn------");
        if (ScreenRecorderRunnable.ScreenRecorderRunnableStatus.PAUSE.equals(screenRecorderRunnable.getStatus())) {
            if (!videoExecutor.isShutdown()) {
                screenRecorderRunnable.setStatus(ScreenRecorderRunnable.ScreenRecorderRunnableStatus.PLAYING);
                screenRecorderRunnable.setGoOnTime(System.currentTimeMillis());
                screenRecorderRunnable.setTotalPauseTime(screenRecorderRunnable.getTotalPauseTime()+(screenRecorderRunnable.getGoOnTime()-screenRecorderRunnable.getPauseTime()));
            }
        }
    }

    @Override
    public void stop() {
        System.out.println("-------stop------");
        if (!ScreenRecorderRunnable.ScreenRecorderRunnableStatus.STOP.equals(screenRecorderRunnable.getStatus())) {
            if (!videoExecutor.isShutdown()) {
                screenRecorderRunnable.setStatus(ScreenRecorderRunnable.ScreenRecorderRunnableStatus.STOP);
                screenRecorderRunnable.setStopTime(System.currentTimeMillis());

                FrameRecorder frameRecorder = screenRecorderRunnable.getFrameRecorder();
                FrameRecorderUtil.stop(frameRecorder);
                FrameRecorderUtil.release(frameRecorder);
                FrameRecorderUtil.close(frameRecorder);

                videoExecutor.shutdown();
            }
        }
    }


    public void setScreenRecorderRunnable(ScreenRecorderRunnable screenRecorderRunnable) {
        this.screenRecorderRunnable = screenRecorderRunnable;
        this.frameRate = screenRecorderRunnable.getFrameRecorder().getFrameRate();
    }
}
