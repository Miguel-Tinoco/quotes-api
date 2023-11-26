package com.challenge.quotes.extensions

import reactor.util.function.Tuple2

object TupleExtensions {
    operator fun <T1, T2> Tuple2<T1, T2>.component1(): T1 {
        return t1
    }

    operator fun <T1, T2> Tuple2<T1, T2>.component2(): T2 {
        return t2
    }
}
