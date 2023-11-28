package com.challenge.quotes.services

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import kotlin.math.ceil

@Service
class PageableService {
    fun getLimit(request: ServerRequest): Int {
        return request.queryParam(LIMIT_PER_PAGE).orElse(DEFAULT_LIMIT_PER_PAGE).toInt()
    }

    fun getPage(request: ServerRequest): Int {
        return request.queryParam(PAGE_PARAM).orElse(DEFAULT_PAGE_VALUE).toInt()
    }

    fun getTotalPages(size: Int, total: Long): Int {
        return ceil(total.toDouble() / size.toDouble()).toInt()
    }

    fun getNextPage(page: Int, totalPages: Int): Int? {
        return if (page < totalPages) page + 1 else null
    }

    companion object {
        private const val DEFAULT_PAGE_VALUE = "1"
        private const val DEFAULT_LIMIT_PER_PAGE = "10"
        private const val PAGE_PARAM = "page"
        private const val LIMIT_PER_PAGE = "limit"
    }
}
