package com.challenge.quotes.handlers

import net.logstash.logback.argument.StructuredArguments.kv
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.UUID

@Component
class RequestLogFilter : WebFilter {
    override fun filter(
        exchange: ServerWebExchange,
        chain: WebFilterChain
    ): Mono<Void> {
        MDC.put("request_id", UUID.randomUUID().toString())
        logger.info(
            "Request received",
            kv("method", exchange.request.method.name()),
            kv("uri", exchange.request.uri.path),
            kv("query_params", exchange.request.queryParams)
        )
        return chain.filter(exchange).doFinally {
            logger.info(
                "Request processed",
                kv("method", exchange.request.method.name()),
                kv("uri", exchange.request.uri.path),
                kv("status_code", exchange.response.statusCode?.value())
            )
            MDC.clear()
        }
    }

    companion object {
        var logger: Logger = LoggerFactory.getLogger(RequestLogFilter::class.java)
    }
}
