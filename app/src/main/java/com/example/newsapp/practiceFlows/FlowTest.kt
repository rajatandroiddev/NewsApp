package com.example.newsapp.practiceFlows

import kotlinx.coroutines.flow.flow

fun main(){

    flow { (1..10).forEach {

        println( emit(it))
    } }



}

