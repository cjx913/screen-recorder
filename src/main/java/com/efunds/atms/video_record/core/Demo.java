package com.efunds.atms.video_record.core;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        String path = "/home/cjx913/Videos/demo.mp4";
        ScreenRecorder screenRecorder = new DefaultScreenRecorder(path, 25);
        screenRecorder.start();
        sleep(10,"start");
        screenRecorder.pause();
        sleep(10,"pause");
        screenRecorder.goOn();
        sleep(8,"goOn");
        screenRecorder.stop();
    }

    public static void sleep(int seconds,String s) throws InterruptedException {
        for(int i=0;i<seconds;i++){
            System.out.println((i+1)+" second "+s);
            Thread.sleep(1000);
        }
    }
}
