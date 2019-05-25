package com.kchen.demo.reactive.demo.raw;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chenkunyun
 * @date 2019-05-11
 */
public class Main {

    public static void main(String[] args) {
        ArrayList<String> list = Lists.newArrayList("1", "2", "3", "4");
        Flux<String> flux = Flux.fromIterable(list);
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(1, 5, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));
        Scheduler scheduler = Schedulers.fromExecutorService(executorService);
        flux.publishOn(scheduler).onErrorContinue((throwable, o) -> Flux.just("error"))
                .subscribe(s -> process(s),
                    throwable -> System.out.println(throwable),
                    () -> System.out.println("finished"));

        waitForTerminationQuietly(executorService, 3);
    }

    private static void process(String s) {
        int randomInt = RandomUtils.nextInt(1, 3);
        if (randomInt == 1) {
            throw new IllegalArgumentException("bad state");
        }

        System.out.println("process [" + s + "] in thread:" + Thread.currentThread().getName());
    }

    private static void waitForTerminationQuietly(ThreadPoolExecutor threadPoolExecutor, long timeoutSeconds) {
        try {
            Stopwatch stopwatch = Stopwatch.createStarted();
            threadPoolExecutor.shutdown();
            // true if this executor terminated and false if the timeout elapsed before termination
            boolean termination = threadPoolExecutor.awaitTermination(timeoutSeconds, TimeUnit.SECONDS);
            stopwatch.stop();
            String terminationMsg = termination ? "executor terminated" : "the timeout elapsed before termination";
            System.out.println("waited " + stopwatch.elapsed(TimeUnit.SECONDS) + " seconds before termination. [" + terminationMsg + "]");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
