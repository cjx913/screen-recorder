package cn.cjx913.video_record.core;

import cn.cjx913.video_record.exception.ScreenRecorderException;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacpp.avutil;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.bytedeco.javacpp.helper.opencv_imgcodecs.cvLoadImage;

public class DefaultScreenRecorder extends AbstractScreenRecorder {
    private String path;

    public DefaultScreenRecorder(String path, double frameRate) {
        FrameRecorder recorder = defaultFrameRecorder(path, frameRate);
        setScreenRecorderRunnable(new VideoScreenRecorderRunnable(recorder));

        this.path = path;
    }

    private FrameRecorder defaultFrameRecorder(String path, double frameRate) {
        FrameRecorder recorder = new FFmpegFrameRecorder(path, screenshotRectangle.width, screenshotRectangle.height, 0);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4);
        recorder.setFormat("mp4");

        recorder.setInterleaved(true);
        recorder.setVideoOption("tune", "zerolatency");
        recorder.setGopSize((int) (frameRate * 2));

        recorder.setSampleRate(44100);
        recorder.setFrameRate(frameRate);

        recorder.setVideoQuality(0);
        recorder.setVideoOption("crf", String.valueOf(frameRate));
        // 2000 kb/s, 720P视频的合理比特率范围
        recorder.setVideoBitrate(1000000);
        /**
         * 权衡quality(视频质量)和encode speed(编码速度) values(值)： ultrafast(终极快),superfast(超级快),
         * veryfast(非常快), faster(很快), fast(快), medium(中等), slow(慢), slower(很慢),
         * veryslow(非常慢)
         * ultrafast(终极快)提供最少的压缩（低编码器CPU）和最大的视频流大小；而veryslow(非常慢)提供最佳的压缩（高编码器CPU）的同时降低视频流的大小
         * 参考：https://trac.ffmpeg.org/wiki/Encode/H.264 官方原文参考：-preset ultrafast as the
         * name implies provides for the fastest possible encoding. If some tradeoff
         * between quality and encode speed, go for the speed. This might be needed if
         * you are going to be transcoding multiple streams on one machine.
         */
        recorder.setVideoOption("preset", "slower");
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P); // yuv420p

//        recorder.setAudioChannels(0);
//        recorder.setAudioOption("crf", "0");
//        recorder.setAudioQuality(0);
//        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);
        return recorder;
    }

    private class VideoScreenRecorderRunnable extends ScreenRecorderRunnable {

        private Robot robot;
        /**
         * 鼠标指针图标
         */
        BufferedImage cursor;
        private OpenCVFrameConverter.ToIplImage conveter;
        private boolean showCursor = true;

        {
            try {
                robot = new Robot();
                cursor = ImageIO.read(this.getClass().getResourceAsStream("/image/cursor.png"));
                conveter = new OpenCVFrameConverter.ToIplImage();
            } catch (AWTException | IOException e) {
                throw new ScreenRecorderException("init params error",e);
            }
        }

        public VideoScreenRecorderRunnable(FrameRecorder frameRecorder) {
            super(frameRecorder);
        }

        @Override
        public void run() {
            if (ScreenRecorderRunnableStatus.PLAYING.equals(this.getStatus())) {
                opencv_core.IplImage image = null;
                File file = null;
                try {
                    //截图
                    BufferedImage screenshot = robot.createScreenCapture(screenshotRectangle);
                    if(showCursor) {
                        //添加鼠标
                        Point point = MouseInfo.getPointerInfo().getLocation();
                        screenshot.createGraphics().drawImage(cursor, point.x, point.y, null);
                    }
                    // 将screenshot对象写入图像文件
                    String name = path.substring(0, path.lastIndexOf(".")) + ".JPEG";
                    file = new File(name);
                    ImageIO.write(screenshot, "JPEG", file);

                    //截图添加到视频
                    image = cvLoadImage(name);
                    if (image != null && image.height() > 0 && image.width() > 0) {
                        FrameRecorder frameRecorder = getFrameRecorder();
                        long current = System.currentTimeMillis();
                        long offset = 1000 * ((current - getStartTime()) - getTotalPauseTime());
                        // 检查偏移量
                        if (offset > frameRecorder.getTimestamp()) {
                            frameRecorder.setTimestamp(offset);
                        }
                        // 写入帧中
                        frameRecorder.record(conveter.convert(image));
                        opencv_core.cvReleaseImage(image);
                    }

                } catch (Exception e) {
                    throw new ScreenRecorderException(e.getMessage(), e);
                } finally {
                    if (image != null) {
                        image.release();
                    }
                    if (file != null && file.exists()) {
                        file.delete();
                    }
                }
            }
        }
    }


}
