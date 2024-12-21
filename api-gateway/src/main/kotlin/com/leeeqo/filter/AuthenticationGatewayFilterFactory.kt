package com.leeeqo.filter

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.server.ServerWebExchange
import java.lang.RuntimeException
import java.net.URI

@Component
class AuthenticationGatewayFilterFactory(
    @Value("\${urls.validate}")
    private val validateUrl: String,

    private val restTemplate: RestTemplate
) : AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config>() {

    override fun apply(config: Config): GatewayFilter =
        GatewayFilter { exchange, chain ->
            val responseEntity = sendValidationRequest(exchange)

            if (!responseEntity.statusCode.is2xxSuccessful)
                throw RuntimeException("Request failed, status code: " + responseEntity.statusCode)

            val request = addClientHeader(responseEntity.body!!, exchange)
            chain.filter(exchange.mutate().request(request).build())
        }

    private fun sendValidationRequest(exchange: ServerWebExchange): ResponseEntity<Long> {
        val headers = HttpHeaders()
        val jwt = exchange.request.headers.getFirst("Authorization")
        headers["Authorization"] = jwt
        val requestEntity = RequestEntity<Void>(headers, HttpMethod.GET, URI.create(validateUrl))

        return restTemplate.exchange(requestEntity, Long::class.java)
    }

    private fun addClientHeader(clientId: Long, exchange: ServerWebExchange): ServerHttpRequest =
        exchange.request
            .mutate()
            .header("clientId", clientId.toString())
            .build()

    class Config
}
