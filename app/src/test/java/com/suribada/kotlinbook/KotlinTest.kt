package com.suribada.kotlinbook

import android.os.SystemClock
import kotlinx.coroutines.experimental.*
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

/**
 * Created by lia on 2017-07-03.
 */
class KotlinTest {

    @Test
    fun coroutineTest() {
        println("Start")

        // Start a coroutine
        launch(CommonPool) {
            delay(1000)
            println("Hello")
        }

        Thread.sleep(2000) // wait for 2 seconds
        println("Stop")
    }

    @Test
    fun manyThreads() {
        val start = System.currentTimeMillis()
        val c = AtomicInteger()

        for (i in 1..1_000_000)
            thread(start = true) { // OOM이 발생하기도 한다.
                c.addAndGet(i)
            }

        println(c.get())
        println("elapsed=${System.currentTimeMillis() - start}") // 56s 943ms
    }

    @Test
    fun threadPool() {
        val start = System.currentTimeMillis()
        val threadPool = Executors.newCachedThreadPool()
        val c = AtomicInteger()
        for (i in 1..1_000_000) {
            threadPool.submit { c.addAndGet(i)  }
        }
        println(c.get())
        println("elapsed=${System.currentTimeMillis() - start}") // 1s 297ms
    }

    @Test
    fun corountineFirst() {
        val start = System.currentTimeMillis()
        val c = AtomicInteger()

        for (i in 1..1_000_000)
            launch(CommonPool) {
                c.addAndGet(i)
            }

        println(c.get())
        println("elapsed=${System.currentTimeMillis() - start}") // 368ms
    }

    @Test
    fun applyDefered() {
        val start = System.currentTimeMillis()
        val deferred = (1..1_000_000).map { n ->
            async (CommonPool) {
                n
            }
        }
        runBlocking {
            val sum = deferred.sumBy { it.await() }
            println("Sum: $sum")
            println("elapsed=${System.currentTimeMillis() - start}")
        }
    }

    suspend fun workload(n: Int): Int {
        delay(1000)
        return n
    }

    @Test
    fun callWorkload() {
        async(CommonPool) {
            workload(10)
        }
    }

    /**
     * runBlocking으로 감싸지 않으면 join에서 컴파일 되지 않는다.
     */
    @Test
    fun waitJob() = runBlocking<Unit> {
            val job = launch(CommonPool) {
                // create new coroutine and keep a reference to its Job
                delay(1000L)
                println("World!")
            }
            println("Hello,")
            job.join() // wait until child coroutine completes
    }

    @Test
    fun delay() = runBlocking<Unit> {
        val jobs = List(100_000) { // create a lot of coroutines and list their jobs
            launch(CommonPool) {
                delay(1000L)
                print(".")
            }
        }
        jobs.forEach { it.join() } // wait for all jobs to complete
    }

    @Test
    fun threadPoolDelay() {
        val count = 2;
        val countDownLatch = CountDownLatch(count)
        val threadPool = Executors.newCachedThreadPool()
        for (i in 1..count) {
            threadPool.submit {
                SystemClock.sleep(1000L)
                println(".")
                countDownLatch.countDown()
            }
        }
        countDownLatch.await()
    }
}