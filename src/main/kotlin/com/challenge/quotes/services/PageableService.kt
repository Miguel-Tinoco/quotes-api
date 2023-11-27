package com.challenge.quotes.services

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import kotlin.math.ceil

@Service
class PageableService {
//    fun getFilter(request: ServerRequest): PageableFilter {
//        val page = request.queryParam(PAGE_PARAM).orElse(DEFAULT_PAGE_VALUE).toIntOrNull()
//        val limit = request.queryParam(LIMIT_PER_PAGE).orElse(DEFAULT_LIMIT_PER_PAGE).toIntOrNull()
//
//        return PageableFilter(
//            page!!,
//            limit!!
//        )
//    }

    fun getLimit(request: ServerRequest): Int {
        val limit = request.queryParam(LIMIT_PER_PAGE).orElse(DEFAULT_LIMIT_PER_PAGE).toIntOrNull()
        return limit!!
    }

    fun getPage(request: ServerRequest): Int {
        val page = request.queryParam(PAGE_PARAM).orElse(DEFAULT_PAGE_VALUE).toIntOrNull()
        return page!!
    }

    fun getTotalPages(size: Int, total: Long): Int {
        return ceil(total.toDouble() / size.toDouble()).toInt()
    }

    fun getNextPage(page: Int, totalPages: Int): Int? {
        return when {
            page < totalPages -> page + 1
            page == totalPages -> null
            else -> null
        }
    }

//    data class PageableFilter(
//        val page: Int,
//        val limit: Int
//    )

    companion object {
        private const val DEFAULT_PAGE_VALUE = "1"
        private const val DEFAULT_LIMIT_PER_PAGE = "10"
        private const val PAGE_PARAM = "page"
        private const val LIMIT_PER_PAGE = "limit"
    }
}
