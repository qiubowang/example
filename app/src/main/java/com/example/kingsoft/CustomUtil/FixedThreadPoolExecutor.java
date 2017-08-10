package com.example.kingsoft.CustomUtil;

import android.content.pm.ProviderInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by kingsoft on 2017/6/22.
 */

public class FixedThreadPoolExecutor extends ThreadPoolExecutor{
    private Thread[] retentionThreads = null;
    private static int currentThreadCount = 10;
    private static int FIEXED_THREAD_COUNT = 10;
    private static FixedThreadPoolExecutor THREAD_POOL = null;
    private Handler mainHandler = null;
    private CustomThreadFactory mThreadFactory = null;
    ThreadPoolExecutor mThreadPoolExecutor = null;

//    private FixedThreadPoolExecutor(){
//
//        mThreadFactory = new CustomThreadFactory();
//        mThreadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(FIEXED_THREAD_COUNT, new CustomThreadFactory());
//        String uuid = UUID.randomUUID().toString();
//
//        retentionThreads = new Thread[FIEXED_THREAD_COUNT];
//        for (int i =0; i < FIEXED_THREAD_COUNT; i++){
//            mThreadFactory.newThread(new CusmHandlerThread(uuid));
//            mThreadPoolExecutor.execute();
//        }
//    }

    public FixedThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, corePoolSize, 0, unit, workQueue);
//        Executors.newFixedThreadPool()
    }

    @Override
    public void execute(Runnable command) {
        currentThreadCount++;
        if(currentThreadCount >= FIEXED_THREAD_COUNT)
            currentThreadCount = 0;
        retentionThreads[currentThreadCount] = (Thread) command;
        super.execute(command);
    }


//    private FixedThreadPoolExecutor(int maxThreadCount){
//        FIEXED_THREAD_COUNT = maxThreadCount;
//    }

    public void aa(){
        ExecutorService executorService = Executors.newFixedThreadPool(FIEXED_THREAD_COUNT, new CustomThreadFactory());
    }

    public static void extractMainThread(Runnable runnable){
        THREAD_POOL.extractMainThread(runnable, 0);
    }

    public synchronized static void extractMainThread(Runnable runnable, int delayTime){
//        if (null == THREAD_POOL)
//            THREAD_POOL = new FixedThreadPoolExecutor();
        THREAD_POOL.mainHandler.postDelayed(runnable, delayTime);
    }

    public static void extractInThread(Runnable runnable){
        THREAD_POOL.extractThread(runnable, 0);
    }

    public synchronized static void extractThread(Runnable runnable, int delayTime){
//        if (null == THREAD_POOL)
//            THREAD_POOL = new FixedThreadPoolExecutor();
        currentThreadCount++;
        if(currentThreadCount >= FIEXED_THREAD_COUNT)
            currentThreadCount = 0;
//        THREAD_POOL.retentionThreads[currentThreadCount].getHandler().postDelayed(runnable, delayTime);
    }


    /** HandlerThread内部中已经封装好了线程安全，因此不必担心多线程问题
     *
     */
    public class CusmHandlerThread extends HandlerThread {
        private Handler mMyHandler = null;
        public CusmHandlerThread(String name) {
            super(name);
            mMyHandler = new Handler(this.getLooper());
        }

        public void setHandler(Handler handler){
            mMyHandler = handler;
        }

        public Handler getHandler(){
            return this.mMyHandler;
        }
    }


    private class CustomThreadFactory implements ThreadFactory{
        public CustomThreadFactory(){

        }
        @Override
        public Thread newThread(@NonNull Runnable r) {
            ((Thread) r).start();
            return (Thread) r;
        }
    }



}
