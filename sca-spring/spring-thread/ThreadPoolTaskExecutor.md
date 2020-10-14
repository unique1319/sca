#ThreadPoolTaskExecutor使用说明

###SpringBoot配置
    @Bean
    public ThreadPoolTaskExecutor threadPool() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(3);
        threadPoolTaskExecutor.setMaxPoolSize(15);
        threadPoolTaskExecutor.setQueueCapacity(3);
        threadPoolTaskExecutor.setKeepAliveSeconds(1000);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setThreadNamePrefix("custom-thread-");
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

###提交任务

- 无返回值的任务使用execute(Runnable)
- 有返回值的任务使用submit(Runnable)

###处理流程
1. 当一个任务被提交到线程池时，首先查看线程池的核心线程是否都在执行任务，否就选择一条线程执行任务，是就执行第二步。
2. 查看核心线程池是否已满，不满就创建一条线程执行任务，否则执行第三步。
3. 查看任务队列是否已满，不满就将任务存储在任务队列中，否则执行第四步。
4. 查看线程池是否已满，不满就创建一条线程执行任务，否则就按照策略处理无法执行的任务。

###在ThreadPoolExecutor中表现为:
1. 如果当前运行的线程数小于corePoolSize，那么就创建线程来执行任务（执行时需要获取全局锁）。
2. 如果运行的线程大于或等于corePoolSize，那么就把task加入BlockQueue。
3. 如果创建的线程数量大于BlockQueue的最大容量，那么创建新线程来执行该任务。
4. 如果创建线程导致当前运行的线程数超过maximumPoolSize，就根据饱和策略来拒绝该任务。

###关闭线程池
调用shutdown或者shutdownNow，两者都不会接受新的任务，而且通过调用要停止线程的interrupt方法来中断线程，有可能线程永远不会被中断，不同之处在于shutdownNow会首先将线程池的状态设置为STOP，然后尝试停止所有线程（有可能导致部分任务没有执行完）然后返回未执行任务的列表。而shutdown则只是将线程池的状态设置为shutdown，然后中断所有没有执行任务的线程，并将剩余的任务执行完。

###配置线程个数
- 如果是CPU密集型任务，那么线程池的线程个数应该尽量少一些，一般为CPU的个数+1条线程。
- 如果是IO密集型任务，那么线程池的线程可以放的很大，如2*CPU的个数。
- 对于混合型任务，如果可以拆分的话，通过拆分成CPU密集型和IO密集型两种来提高执行效率；如果不能拆分的的话就可以根据实际情况来调整线程池中线程的个数。


###监控线程池状态
常用状态：

- taskCount：线程需要执行的任务个数。
- completedTaskCount：线程池在运行过程中已完成的任务数。
- largestPoolSize：线程池曾经创建过的最大线程数量。
- getPoolSize获取当前线程池的线程数量。
- getActiveCount：获取活动的线程的数量

通过继承线程池，重写beforeExecute，afterExecute和terminated方法来在线程执行任务前，线程执行任务结束，和线程终结前获取线程的运行情况，根据具体情况调整线程池的线程数量。

###并发

service:

    @Slf4j
    @Service
    public class ExecuteService {

    @Async("threadPool")
    public void execute() {
        log.info("start executeAsync");
        try {
            System.out.println("当前运行的线程名称：" + Thread.currentThread().getName());
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("end executeAsync");
    }




controller:
    
    @RestController
    public class ProviderController {

    	@GetMapping("/provider")
    	public String provider() {
        	return "Hello Provider";
    	}
    }

在浏览器用F5按钮快速多刷新几次

output:

    2020-10-14 16:04:52.520  INFO 10456 --- [custom-thread-1] c.w.s.thread.service.ExecuteService      : start executeAsync
    当前运行的线程名称：custom-thread-1
    2020-10-14 16:04:53.999  INFO 10456 --- [custom-thread-2] c.w.s.thread.service.ExecuteService      : start executeAsync
    当前运行的线程名称：custom-thread-2
    2020-10-14 16:04:55.572  INFO 10456 --- [custom-thread-3] c.w.s.thread.service.ExecuteService      : start executeAsync
    当前运行的线程名称：custom-thread-3
	2020-10-14 16:05:00.860  INFO 10456 --- [custom-thread-4] c.w.s.thread.service.ExecuteService      : start executeAsync
	当前运行的线程名称：custom-thread-4
	2020-10-14 16:05:02.520  INFO 10456 --- [custom-thread-1] c.w.s.thread.service.ExecuteService      : end executeAsync
	2020-10-14 16:05:02.520  INFO 10456 --- [custom-thread-1] c.w.s.thread.service.ExecuteService      : start executeAsync
	当前运行的线程名称：custom-thread-1
	2020-10-14 16:05:03.999  INFO 10456 --- [custom-thread-2] c.w.s.thread.service.ExecuteService      : end executeAsync
	2020-10-14 16:05:03.999  INFO 10456 --- [custom-thread-2] c.w.s.thread.service.ExecuteService      : start executeAsync
	当前运行的线程名称：custom-thread-2
	2020-10-14 16:05:05.573  INFO 10456 --- [custom-thread-3] c.w.s.thread.service.ExecuteService      : end executeAsync
	2020-10-14 16:05:05.573  INFO 10456 --- [custom-thread-3] c.w.s.thread.service.ExecuteService      : start executeAsync
	当前运行的线程名称：custom-thread-3
	2020-10-14 16:05:06.267  INFO 10456 --- [custom-thread-5] c.w.s.thread.service.ExecuteService      : start executeAsync
	当前运行的线程名称：custom-thread-5
	2020-10-14 16:05:06.467  INFO 10456 --- [custom-thread-6] c.w.s.thread.service.ExecuteService      : start executeAsync
	当前运行的线程名称：custom-thread-6
	2020-10-14 16:05:06.663  INFO 10456 --- [custom-thread-7] c.w.s.thread.service.ExecuteService      : start executeAsync
	当前运行的线程名称：custom-thread-7
	2020-10-14 16:05:07.483  INFO 10456 --- [custom-thread-8] c.w.s.thread.service.ExecuteService      : start executeAsync
	当前运行的线程名称：custom-thread-8
	2020-10-14 16:05:07.657  INFO 10456 --- [custom-thread-9] c.w.s.thread.service.ExecuteService      : start executeAsync
	当前运行的线程名称：custom-thread-9
	2020-10-14 16:05:10.861  INFO 10456 --- [custom-thread-4] c.w.s.thread.service.ExecuteService      : end executeAsync
	2020-10-14 16:05:10.861  INFO 10456 --- [custom-thread-4] c.w.s.thread.service.ExecuteService      : start executeAsync
	当前运行的线程名称：custom-thread-4
	2020-10-14 16:05:12.520  INFO 10456 --- [custom-thread-1] c.w.s.thread.service.ExecuteService      : end executeAsync
	2020-10-14 16:05:12.520  INFO 10456 --- [custom-thread-1] c.w.s.thread.service.ExecuteService      : start executeAsync
	当前运行的线程名称：custom-thread-1
	2020-10-14 16:05:13.999  INFO 10456 --- [custom-thread-2] c.w.s.thread.service.ExecuteService      : end executeAsync
	2020-10-14 16:05:13.999  INFO 10456 --- [custom-thread-2] c.w.s.thread.service.ExecuteService      : start executeAsync
	当前运行的线程名称：custom-thread-2
	2020-10-14 16:05:15.573  INFO 10456 --- [custom-thread-3] c.w.s.thread.service.ExecuteService      : end executeAsync
	2020-10-14 16:05:16.267  INFO 10456 --- [custom-thread-5] c.w.s.thread.service.ExecuteService      : end executeAsync
	2020-10-14 16:05:16.468  INFO 10456 --- [custom-thread-6] c.w.s.thread.service.ExecuteService      : end executeAsync
	2020-10-14 16:05:16.664  INFO 10456 --- [custom-thread-7] c.w.s.thread.service.ExecuteService      : end executeAsync
	2020-10-14 16:05:17.483  INFO 10456 --- [custom-thread-8] c.w.s.thread.service.ExecuteService      : end executeAsync
	2020-10-14 16:05:17.659  INFO 10456 --- [custom-thread-9] c.w.s.thread.service.ExecuteService      : end executeAsync
	2020-10-14 16:05:20.861  INFO 10456 --- [custom-thread-4] c.w.s.thread.service.ExecuteService      : end executeAsync
	2020-10-14 16:05:22.520  INFO 10456 --- [custom-thread-1] c.w.s.thread.service.ExecuteService      : end executeAsync
	2020-10-14 16:05:24.000  INFO 10456 --- [custom-thread-2] c.w.s.thread.service.ExecuteService      : end executeAsync


###多线程池

某些情况下，我们需要在项目中对多种任务分配不同的线程池进行执行。从而通过监控不同的线程池来控制不同的任务。

    
    @Bean
    public ThreadPoolTaskExecutor threadPool() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(3);
        threadPoolTaskExecutor.setMaxPoolSize(15);
        threadPoolTaskExecutor.setQueueCapacity(3);
        threadPoolTaskExecutor.setKeepAliveSeconds(1000);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setThreadNamePrefix("custom-thread-");
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

    @Bean
    public ThreadPoolTaskExecutor threadPool2() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(3);
        threadPoolTaskExecutor.setMaxPoolSize(15);
        threadPoolTaskExecutor.setQueueCapacity(3);
        threadPoolTaskExecutor.setKeepAliveSeconds(1000);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        threadPoolTaskExecutor.setThreadNamePrefix("custom-thread-");
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }