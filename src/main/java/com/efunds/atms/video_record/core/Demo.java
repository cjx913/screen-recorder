package com.efunds.atms.video_record.core;

public class Demo {
    public static void main(String[] args) {
        try {
            String path = "/home/cjx913/Videos/demo.mp4";
            ScreenRecorder screenRecorder = new DefaultScreenRecorder(path, 15);
            screenRecorder.start();
            sleep(10, "start");
            screenRecorder.pause();
            sleep(10, "pause1");
            screenRecorder.goOn();
            sleep(8, "goOn1");
            screenRecorder.pause();
            sleep(5, "pause2");
            screenRecorder.goOn();
            sleep(8, "goOn2");
            screenRecorder.pause();
            sleep(7, "pause3");
            screenRecorder.goOn();
            sleep(10, "goOn3");
            screenRecorder.stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void sleep(int seconds,String s) throws InterruptedException {
        for(int i=0;i<seconds;i++){
            System.out.println((i+1)+" second "+s);
            Thread.sleep(1000);
        }
    }
}
