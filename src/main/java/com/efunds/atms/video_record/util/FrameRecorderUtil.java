package com.efunds.atms.video_record.util;

import com.efunds.atms.video_record.exception.FrameRecorderUtilException;
import org.bytedeco.javacv.FrameRecorder;

public class FrameRecorderUtil {
    public static void start(FrameRecorder frameRecorder){
        if(frameRecorder!=null){
            try {
                frameRecorder.start();
            } catch (FrameRecorder.Exception e) {
                throw new FrameRecorderUtilException("frameRecorder start error!",e);
            }
        }
    }

    public static void stop(FrameRecorder frameRecorder){
        if(frameRecorder!=null){
            try {
                frameRecorder.stop();
            } catch (FrameRecorder.Exception e) {
                throw new FrameRecorderUtilException("frameRecorder stop error!",e);
            }
        }
    }

    public static void release(FrameRecorder frameRecorder){
        if(frameRecorder!=null){
            try {
                frameRecorder.release();
            } catch (FrameRecorder.Exception e) {
                throw new FrameRecorderUtilException("frameRecorder release error!",e);
            }
        }
    }

    public static void close(FrameRecorder frameRecorder){
        if(frameRecorder!=null){
            try {
                frameRecorder.close();
            } catch (FrameRecorder.Exception e) {
                throw new FrameRecorderUtilException("frameRecorder close error!",e);
            }
        }
    }
}
