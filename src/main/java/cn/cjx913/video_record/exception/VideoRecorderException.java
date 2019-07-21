package cn.cjx913.video_record.exception;

public class VideoRecorderException extends RuntimeException {
    public VideoRecorderException() {
    }

    public VideoRecorderException(String message) {
        super(message);
    }

    public VideoRecorderException(String message, Throwable cause) {
        super(message, cause);
    }

    public VideoRecorderException(Throwable cause) {
        super(cause);
    }

    public VideoRecorderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
