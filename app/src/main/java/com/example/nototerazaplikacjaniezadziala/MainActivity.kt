package com.example.nototerazaplikacjaniezadziala

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.math.pow
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun bruteForce(text: String, wzorzec: String) {
            val n = text.length
            val m = wzorzec.length
            for (i in 0..n - m) {
                var j = 0
                while (j < m && text[i + j] == wzorzec[j]) {
                    j++
                }
            }
        }

        fun prefi(wzorzec: String): IntArray {
            val m = wzorzec.length
            val lps = IntArray(m)
            var l = 0
            var i = 1

            while (i < m) {
                when {
                    wzorzec[i] == wzorzec[l] -> {
                        l++
                        lps[i] = l
                        i++
                    }
                    l > 0 -> l = lps[l - 1]
                    else -> {
                        lps[i] = 0
                        i++
                    }
                }
            }

            return lps
        }

        fun KMP(text: String, wzorzec: String) {
            val n = text.length
            val m = wzorzec.length
            val lps = prefi(wzorzec)

            var i = 0
            var j = 0
            while (i < n) {
                when {
                    wzorzec[j] == text[i] -> {
                        i++
                        j++
                    }
                    j > 0 -> j = lps[j - 1]
                    else -> i++
                }

                if (j == m) {
                    return
                }
            }
        }


        fun BM(text: String, wzorzec: String) {
            val n = text.length
            val m = wzorzec.length
            val b = IntArray(256) { -1 }
            for (i in 0 until m) {
                b[wzorzec[i].toInt()] = i
            }

            var i = m - 1
            var j = m - 1
            while (i < n) {
                if (text[i] == wzorzec[j]) {
                    if (j == 0) {
                        return
                    }
                    i--
                    j--
                } else {
                    i += m - java.lang.Math.min(j, 1 + b[text[i].toInt()])
                    j = m - 1
                }
            }
        }

        fun RK(text: String, wzorzec: String) {
            val pri = 101
            val n = text.length
            val m = wzorzec.length
            val patternHash = wzorzec.hashCode()
            var textHash = 0
            for (i in 0 until m) {
                textHash += (text[i].toInt() * pri.toDouble().pow(m - i - 1).toInt())
            }

            for (i in 0..n - m) {
                if (textHash == patternHash && text.substring(i, i + m) == wzorzec) {
                    return
                }
                if (i < n - m) {
                    textHash =
                        (textHash - (text[i].toInt() * pri.toDouble().pow(m - 1).toInt()))
                    textHash = textHash * pri + text[i + m].toInt()
                }
            }
        }

        fun strGenerator(ildan: Int): String {
            val alphabet = "abcdefghijklmnopqrstuvwxyz"
            val lancuch = (1..ildan)
                .map { alphabet[Random.nextInt(alphabet.length)] }
                .joinToString("")
            return lancuch
        }




    }
}