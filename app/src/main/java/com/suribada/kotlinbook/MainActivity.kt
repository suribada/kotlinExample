package com.suribada.kotlinbook

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUi().setContentView(this)
    }

    class MainActivityUi : AnkoComponent<MainActivity> {

        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
            verticalLayout {
                button {
                    text = "ThreadPool-1,000"
                    onClick {
                        view -> threadPool(1_000)
                        toast("end ThreadPool! :)") // ui.toast
                    }
                }
                button {
                    text = "ThreadPool-10,000"
                    onClick {
                        view -> threadPool(10_000)
                        toast("end ThreadPool! :)") // ui.toast
                    }
                }
                button {
                    text = "ThreadPool-50,000" // 에뮬 기준으로 여기까지는 문제 없음
                    onClick {
                        view -> threadPool(50_000)
                        toast("end ThreadPool! :)") // ui.toast
                    }
                }
                button {
                    text = "ThreadPool-100,000"
                    onClick {
                        view -> threadPool(100_000)
                        toast("end ThreadPool! :)") // ui.toast
                    }
                }
                button {
                    text = "ThreadPool-200,000"
                    onClick {
                        view -> threadPool(200_000)
                        toast("end ThreadPool! :)") // ui.toast
                    }
                }
                button {
                    text = "ThreadPool-500,000"
                    onClick {
                        view -> threadPool(500_000)
                        toast("end ThreadPool! :)") // ui.toast
                    }
                }
                button {
                    text = "ThreadPool-1,000,000"
                    onClick {
                        view -> threadPool(1_000_000)
                        toast("end ThreadPool! :)") // ui.toast
                    }
                }
                button {
                    text = "coroutine"
                    onClick {
                        view -> coroutine()
                        toast("end Coroutine! :)") // AnkoContext 안에서 가능
                    }
                }
            }
        }.view()

        // threadPool 함수 안에 넣으면 여러 번 실행하면 메모리 문제 발생. 이렇게 해도 스레드 개수가 커지면서 나중에는 메모리 문제 발생
        val threadPool = Executors.newCachedThreadPool()

        fun threadPool(count : Int) {
            val c = AtomicInteger()
            for (i in 1..count) {
                threadPool.submit { c.addAndGet(i)  }
            }
            println(c.get())
        }

        fun coroutine() {
            val c = AtomicInteger()

            for (i in 1..1_000_000) // 에뮬레이터 기준 1000만까지는 무리가 없고 억 단위로 가면 3분 넘게 소요.
                launch(CommonPool) {
                    c.addAndGet(i)
                }
            println(c.get())
        }
    }

}