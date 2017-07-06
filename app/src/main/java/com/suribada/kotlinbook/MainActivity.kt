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
                    text = "ThreadPool"
                    onClick {
                        view -> threadPool()
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

        fun threadPool() {
            val threadPool = Executors.newCachedThreadPool()
            val c = AtomicInteger()
            for (i in 1..1_000_000) {
                threadPool.submit { c.addAndGet(i)  }
            }
            println(c.get())
        }

        fun coroutine() {
            val c = AtomicInteger()

            for (i in 1..1_000_000)
                launch(CommonPool) {
                    c.addAndGet(i)
                }

            println(c.get())
        }
    }

}