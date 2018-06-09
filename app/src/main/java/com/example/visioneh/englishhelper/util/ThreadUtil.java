package com.example.visioneh.englishhelper.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by 62588 on 2018/5/1.
 */

public class ThreadUtil {
    private static ThreadUtil instance;
    private  ThreadPoolExecutor  executor;

    public static ThreadUtil  getInstance(){
        if(instance==null){
            synchronized (ThreadUtil.class){
                if(instance==null){
                    instance=new ThreadUtil();
                }
            }
        }
        return getInstance();
    }
    private ThreadUtil(){
        executor= (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    public ThreadPoolExecutor getExecutor(){
        return executor;
    }

    public void submit(Runnable runnable){
        if(executor!=null){
            executor.submit(runnable);
        }
    }

}
