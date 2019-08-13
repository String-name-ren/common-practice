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
 * @date: 2019-08-13 11:22
 **/
public class ExecutorsTest {

    public static void main(String[] args) {
        List<String> queryNos = new ArrayList(Arrays.asList("1,2,3,4,5,6,7".split(",")));
        //每秒钟放5个令牌，即每200毫秒放入一个
        RateLimiter limiter = RateLimiter.create(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (final String queryNo : queryNos) {
            //从RateLimiter获取一个令牌，会被阻塞直到获取一个令牌
            //可以看到效果，每200毫秒执行一次
            limiter.acquire();
            executorService.submit(new Runnable() {
                public void run() {
                //...发送请求
                System.out.println(queryNo + ":" + Calendar.getInstance().getTimeInMillis());
                }
            });
        }
    }
}
