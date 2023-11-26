package com.challenge.quotes.services

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import kotlin.math.ceil

@Service
class PageableService {
    fun getFilter(request: ServerRequest): Int {
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

    companion object {
        private const val DEFAULT_PAGE_VALUE = "1"
        private const val PAGE_PARAM = "page"
    }
}
