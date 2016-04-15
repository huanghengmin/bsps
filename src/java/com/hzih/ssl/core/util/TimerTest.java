package com.hzih.ssl.core.util;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-9-11
 * Time: 上午11:02
 * To change this template use File | Settings | File Templates.
 */


import java.util.Timer;

public class TimerTest {
    private  Long time;         //每隔多少时间执行一次
    private boolean flag;    //定一个标示 （是否开启了线程）
    private Timer timer ;

    public TimerTest( Long time){
        this.time=time;

    }

    public  void execute(){
        timer = new Timer();
        timer.schedule(new MyTask(time), 5000, time);//在5秒后执行此任务,以后每隔5秒执行这个MyTask任务.
    }
    public void stop(){
        timer.cancel();// 终止此计时器，丢弃所有当前已安排的任务。
    }
   public static void main(String[] args){
       TimerTest test=new TimerTest(5000l);
       test.execute();
//       timer.schedule(new MyTask(null,null,5000l), 1000, 5000);//在1秒后执行此任务,以后每隔1秒执行这个MyTask任务.
//        while(true){
//            Scanner scan = new Scanner(System.in);
//            String str = scan.nextLine();
//            if(str.equals("ok")){
//                 test.stop();
//            }
//            if(str.equals("yes")){
//                Timer timers = new Timer();
//                timers.schedule(new MyTask(5000l), 1000, 5000);//在1秒后执行此任务,以后每隔1秒执行这个MyTask任务.
//            }
//        }


    }
    static class MyTask extends java.util.TimerTask{

       private  Long time;
       public  MyTask(Long time){
           this.time=time;
        }
        public void run() {
//            UserUtil.duration();   // 持续时间
//            UserUtil.endTime();
//            System.out.println("-----------------间隔"+time+"秒打印一次！-----------------------");
        }
    }
}