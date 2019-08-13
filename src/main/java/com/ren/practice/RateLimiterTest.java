package com.ren.practice;

import com.google.common.util.concurrent.RateLimiter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述
 *
 * @author: renpenghui
 * @date: 2019-08-13 11:44
 **/
public class RateLimiterTest {

    public static void main(String[] args) throws Exception {
        List<String> queryNos = new ArrayList(Arrays.asList("1,2,3,4,5,6,7,8,9".split(",")));
        //每秒钟放5个令牌，即每200毫秒放入一个
        final RateLimiter limiter = RateLimiter.create(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //休眠一秒是为了把5个令牌都放入令牌桶内
        Thread.sleep(1000);
        for (final String queryNo : queryNos) {
            executorService.submit(new Runnable() {
                public void run() {
                    //尝试获取令牌，非阻塞，可以看到效果：无顺序，切后面的获取不到令牌，没有执行
                    boolean flag = limiter.tryAcquire();
                    if(flag){
                        //...发送请求
                        System.out.println(queryNo + ":" + Calendar.getInstance().getTimeInMillis());
                    }

                }
            });
        }
    }
}
