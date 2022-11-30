package com.example.demojava;

import android.content.Context;

import com.example.demojava.model.Person;
import com.example.demojava.model.Zuzhang;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.test.espresso.core.internal.deps.guava.util.concurrent.ListeningExecutorService;
import androidx.test.espresso.core.internal.deps.guava.util.concurrent.MoreExecutors;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.demojava", appContext.getPackageName());
    }

    private int count = 0;

    @Test
    public void t() {
        Zuzhang zuzhang = new Zuzhang();

//        ListeningExecutorService pool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            pool.submit(() -> {
                zuzhang.addPerson(new Person("" + finalI));
                System.out.println("zuzhang" + zuzhang.getList().size());
                count++;
                System.out.println("count=" +count);
                if (count==100){
                        System.out.println("zuzhang"+zuzhang.toString());

                }
//                if (zuzhang.getList().size() == 100) {
//                    pool.shutdown();
//
//                }

            });
        }

//        pool.shutdown();
        while (true) {
            if (pool.isTerminated()) {
                System.out.println("结束了！");
                break;
            }
            //Thread.sleep(200);
        }
        System.out.println("我还没结束");
        System.out.println("zuzhang" + zuzhang.toString());
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                        System.out.println("zuzhang"+zuzhang.toString());
//            }
//        },3000);
    }


}
