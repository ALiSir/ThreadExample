# ThreadExample
android 四种进程；进程；thread;安卓。

# 程序运行过程

![图](https://github.com/ALiSir/ThreadExample/raw/master/example.png "实际运行图") </br>
 1.点击运行，每种线程初始化运行20次，其中如果指定线程数，则它的核心线程数为5 </br>
 2.通过显示的时间和显示的先后顺序，很容易看出线程之间的并行关系 </br>


# 基础

*@ ThreadPoolExecutor构造参数介绍

     `public ThreadPoolExecutor(`
    
//核心线程数，除非allowCoreThreadTimeOut被设置为true，否则它闲着也不会死

         `int corePoolSize,`
         
//最大线程数，活动线程数量超过它，后续任务就会排队

            `int maximumPoolSize,`
            
//超时时长，作用于非核心线程（allowCoreThreadTimeOut被设置为true时也会同时作用于核心线程），闲置超时便被回收

            `long keepAliveTime,`
            
//枚举类型，设置keepAliveTime的单位，有TimeUnit.MILLISECONDS（ms）、TimeUnit. SECONDS（s）等

            `TimeUnit unit,`
            
//缓冲任务队列，线程池的execute方法会将Runnable对象存储起来

            `BlockingQueue<Runnable> workQueue,`
            
//线程工厂接口，只有一个new Thread(Runnable r)方法，可为线程池创建新线程

            `ThreadFactory threadFactory)`

### 说明

（1）当currentSize<corePoolSize时，没什么好说的，直接启动一个核心线程并执行任务。

（2）当currentSize>=corePoolSize、并且workQueue未满时，添加进来的任务会被安排到workQueue中等待执行。

（3）当workQueue已满，但是currentSize<maximumPoolSize时，会立即开启一个非核心线程来执行任务。

（4）当currentSize>=corePoolSize、workQueue已满、并且currentSize>maximumPoolSize时，调用handler默认抛出RejectExecutionExpection异常。

# 联系方式：

QQ群：584616826
  
![QQ群](https://github.com/ALiSir/Resource/raw/master/Images/qq.JPG "扫一扫，加入QQ群！")
