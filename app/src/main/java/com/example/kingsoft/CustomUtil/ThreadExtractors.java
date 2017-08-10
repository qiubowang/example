package com.example.kingsoft.CustomUtil;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.method.NumberKeyListener;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by kingsoft on 2017/6/12.
 */

public class ThreadExtractors {
    public class WorkThead extends Thread {
        private boolean mIsQuiting = false;
        private boolean mIsLoopEnd = false;
        Handler myHandler = null;
        private final String mThreadId;
        public WorkThead(String threadId){
            mThreadId = threadId;
        }
        @Override
        public void run() {
            //Looper.prepare() 和 Looper.loop()成对存在，创建Handler必须指定looper否则会抛异常
            //需要通过synchronized来保证线程安全
            Looper.prepare();
            synchronized (this){
                myHandler = new Handler();
                //myHandler创建成功通知所有等待的线程
                notifyAll();
            }
            Looper.loop();
        }

        public Handler getHandler(){
            synchronized (this) {
                while (null == myHandler) {
                    try {
                        //如果myHandler没创建成功，就需要等待，直到myHandler被创建成功
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return myHandler;
        }

        public void  quit(){
            //退出时需要清除句柄，避免内存泄露
            if (mIsQuiting)
                return;
            mIsQuiting = true;
            if (null != myHandler){
                //清除message队列及其回调
                myHandler.removeCallbacksAndMessages(null);
                myHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Looper.myLooper().quit();
                        mIsLoopEnd = true;
                    }
                });
            }
        }

        public boolean isQuiting(){
            return mIsQuiting;
        }

        public boolean isLoopEnd(){
            return this.mIsLoopEnd;
        }

    }

    //最大线程数量
    private static final int MAX_TREAD_COUNT = 10;
    private static final int minThreadCount = 5;
    //当前线程数
    private static int currentThreadCount = 0;
    //保留的线程数
    private static int RETENTION_THREAD_COUNT = 10;

    private WorkThead[] retentionThreads;

    private static ThreadExtractors THREAD_POOL = null;
    private static Handler mainHandler = null;

    private ThreadExtractors(){
        mainHandler = new Handler(Looper.getMainLooper());
        //子线程数目为10个
        retentionThreads = new WorkThead[RETENTION_THREAD_COUNT];
        for(int i = 0; i < RETENTION_THREAD_COUNT; i ++){
            retentionThreads[i] = newThread();
        }
    }

    private WorkThead newThread(){
        String threadId = UUID.randomUUID().toString();
        WorkThead workThead = new WorkThead(threadId);
        workThead.start();
        return workThead;
    }

    public void OnDestroy(){
        if (null == retentionThreads)
            return;

        for (int i = 0, length = retentionThreads.length; i < length; i++){
            retentionThreads[i].quit();
        }
        mainHandler.removeCallbacksAndMessages(null);
        retentionThreads = null;
        THREAD_POOL = null;
    }

    public static void extractMainThread(Runnable runnable){
        THREAD_POOL.extractMainThread(runnable, 0);
    }

    public synchronized static void extractMainThread(Runnable runnable, int delayTime){
        if (null == THREAD_POOL)
            THREAD_POOL = new ThreadExtractors();
        THREAD_POOL.mainHandler.postDelayed(runnable, delayTime);
    }

    public static void extractInThread(Runnable runnable){
        THREAD_POOL.extractThread(runnable, 0);
    }

    public synchronized static void extractThread(Runnable runnable, int delayTime){
        if (null == THREAD_POOL)
            THREAD_POOL = new ThreadExtractors();
        currentThreadCount++;
        if(currentThreadCount >= MAX_TREAD_COUNT)
            currentThreadCount = 0;
        THREAD_POOL.retentionThreads[currentThreadCount].getHandler().postDelayed(runnable, delayTime);
    }

    private HandlerThread newHandlerThread(){
        HandlerThread handlerThread = new CusmHandlerThread(UUID.randomUUID().toString());
        handlerThread.start();
        return handlerThread;
    }

    /** HandlerThread内部中已经封装好了线程安全，因此不必担心多线程问题
     *
     */
    public class CusmHandlerThread extends  HandlerThread{
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
}
