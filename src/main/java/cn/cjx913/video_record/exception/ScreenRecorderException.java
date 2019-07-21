package cn.cjx913.video_record.exception;

public class ScreenRecorderException extends VideoRecorderException {
    public ScreenRecorderException() {
    }

    public ScreenRecorderException(String message) {
        super(message);
    }

    public ScreenRecorderException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScreenRecorderException(Throwable cause) {
        super(cause);
    }

    public ScreenRecorderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
