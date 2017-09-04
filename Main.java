package com.gospell.nms.service.netty.base;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Main {

    public static class MyCallable implements Callable<Integer> {
        public Integer call() throws Exception {
           Thread.sleep(10000);
            return 1;
        }

    }

    public static void main(String[] args) {
        MyCallable callable = new MyCallable();
        FutureTask<Integer> task = new FutureTask<Integer>(callable);
        Thread t = new Thread(task);
        try {
            t.start();
            
            System.out.println("here");
            System.out.println(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}