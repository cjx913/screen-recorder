package com.efunds.atms.video_record.exception;

public class FrameRecorderUtilException extends VideoRecorderException {
    public FrameRecorderUtilException() {
    }

    public FrameRecorderUtilException(String message) {
        super(message);
    }

    public FrameRecorderUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public FrameRecorderUtilException(Throwable cause) {
        super(cause);
    }

    public FrameRecorderUtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
