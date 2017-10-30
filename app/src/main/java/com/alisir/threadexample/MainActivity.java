package com.alisir.threadexample;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout linearContain;
    final int handlerTager = 0x123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        findViewById(R.id.single).setOnClickListener(this);
        findViewById(R.id.cached).setOnClickListener(this);
        findViewById(R.id.fix).setOnClickListener(this);
        findViewById(R.id.scheduled).setOnClickListener(this);
        findViewById(R.id.clear).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.single:
                startTest("single",getSingleThreadPool());
                break;
            case R.id.fix:
                startTest("fix",getFixThreadPool(5));
                break;
            case R.id.cached:
                startTest("cached",getCachedThreadPool());
                break;
            case R.id.scheduled:
                startScheduledThread();
                break;
            case R.id.clear:
                clearLogStopThread();
                break;

        }
    }

    void clearLogStopThread(){
        linearContain.removeAllViews();
        TextView tv = new TextView(this);
        tv.setText("测试日志：");
        linearContain.addView(tv);
    }

    void startTest(final String title, ExecutorService service){
        TextView tv1 = new TextView(this);
        tv1.setText("   ");
        linearContain.addView(tv1);
        for (int i = 0; i < 20; i++) {
            final int index = i;
            service.execute(new Runnable(){
                @Override
                public void run() {
                    try{
                        Thread.sleep(2000);
                        Message msg = new Message();
                        msg.what = handlerTager;
                        String str = title+"线程index值:" + index + ";时间："+(new Date().getTime()/1000 +"秒");
                        msg.obj = str;
                        handler.sendMessage(msg);
                        Log.i("线程", "run: "+str);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    //FixThreadPool  类似于  Executors.newFixedThreadPool(threads).execute(runnable);
    ExecutorService getFixThreadPool(int threads){
            return new ThreadPoolExecutor(threads,threads,0L, TimeUnit.MICROSECONDS,new LinkedBlockingDeque<Runnable>());
    }

    //SingleThreadPool 类似于 Executors.newSingleThreadExecutor().execute(runnable);
    ExecutorService getSingleThreadPool(){
        return new ThreadPoolExecutor(1,1,0L,TimeUnit.MICROSECONDS,new LinkedBlockingQueue<Runnable>());
    }

    //cachedThreadPool 类似于  Executors.newCachedThreadPool().execute(runnable);
    ExecutorService getCachedThreadPool(){
        return new ThreadPoolExecutor(0,Integer.MAX_VALUE,60L,TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
    }

    void startScheduledThread(){
        for (int i = 0; i < 20; i++) {
            final int index = i;
            new ScheduledThreadPoolExecutor(5).scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(2000);
                        Message msg = new Message();
                        msg.what = handlerTager;
                        String str = "scheduled线程index值:" + index + ";时间："+(new Date().getTime()/1000 +"秒");
                        msg.obj = str;
                        handler.sendMessage(msg);
                        Log.i("线程", "run: "+str);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            },1000,2000,TimeUnit.MICROSECONDS);
            //延迟1000ms,每2000ms执行一次
        }
    }

    void initView(){
        linearContain = (LinearLayout) findViewById(R.id.linear_contain);
        TextView tv = new TextView(this);
        tv.setText("测试日志：");
        linearContain.addView(tv);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch ((int) msg.what){
                case handlerTager:
                    linearContain = (LinearLayout) findViewById(R.id.linear_contain);
                    TextView tvs = new TextView(MainActivity.this);
                    tvs.setText(msg.obj.toString());
                    linearContain.addView(tvs);
                    break;
                default:
                    break;
            }
        }
    };

}










