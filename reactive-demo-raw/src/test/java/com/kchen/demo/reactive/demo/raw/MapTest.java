package com.kchen.demo.reactive.demo.raw;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;

/**
 * @author chenkunyun
 * @date 2019-05-25
 */
public class MapTest {

    private static final List<Integer> integerList = Lists.newArrayList(1, 2, 3, 4);
    private static final Duration delay = Duration.ofSeconds(RandomUtils.nextInt(1, 5));

    /**
     * 同步做元素转换
     * 慢慢输出
     */
    @Test
    public void mapTest() {
        Flux.fromIterable(integerList)
                .map(integer -> "string: " + integer)
                .delayElements(delay)
                .toStream().forEach(System.out::println);
    }

    /**
     * 异步转换元素(不保证顺序)
     * 存在subscribeOn(Schedulers.elastic())的时候，不保证顺序
     * 不存在的时候，一定是顺序一致，因为都在一个main线程
     * 一次性输出, 输出不一定是2，4，6，8
     */
    @Test
    public void flatMapTest() {
        Flux.fromIterable(integerList)
                .flatMap(integer -> Flux.just(integer * 2).delayElements(delay))
                .subscribeOn(Schedulers.elastic())
                .doOnNext(System.out::println).blockLast()
                ;
    }

    /**
     * 异步转换元素(保证顺序)
     * 一次性输出, 输出一定是2，4，6，8，
     * 并且耗时一定是4个元素中生成时间最长那个
     */
    @Test
    public void flatMapSequentialTest() {
        Flux.fromIterable(integerList)
                .flatMapSequential(integer -> Mono.just(integer * 2).delayElement(delay))
                .toStream().forEach(System.out::println);
    }

    /**
     * 异步转换元素(保证顺序)
     * 慢慢输出， 输出一定是2，4，6，8
     * 耗时一定是4个元素耗时之和，因为这里会按照顺序来排队
     *
     * concatMap 操作符的作用也是把流中的每个元素转换成一个流，再把所有流进行合并。
     * 与 flatMap 不同的是，concatMap 会根据原始流中的元素顺序依次把转换之后的流进行合并；
     * 与 flatMapSequential 不同的是，concatMap 对转换之后的流的订阅是动态进行的，
     * 而 flatMapSequential 在合并之前就已经订阅了所有的流。
     */
    @Test
    public void concatMapTest() {
        Flux.fromIterable(integerList)
                .concatMap(integer -> Flux.just(integer * 2).delayElements(delay))
                .toStream().forEach(System.out::println);
    }
}
