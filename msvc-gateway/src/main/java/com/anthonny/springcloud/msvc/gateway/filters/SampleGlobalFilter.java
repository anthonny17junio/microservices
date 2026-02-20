package com.anthonny.springcloud.msvc.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * Custom Global Filter that intercepts every request in the Gateway.
 * <p>
 * This filter demonstrates the "Pre" and "Post" filter pattern:
 * <ul>
 * <li><b>PRE:</b> Logic executed before sending the request to the microservice (e.g., logging, adding headers).</li>
 * <li><b>POST:</b> Logic executed after receiving the response from the microservice (e.g., adding cookies, modifying response headers).</li>
 * </ul>
 */
@Component
public class SampleGlobalFilter implements GlobalFilter {

    private final Logger logger = LoggerFactory.getLogger(SampleGlobalFilter.class);

    private static final String HEADER_NAME = "token";
    private static final String HEADER_VALUE = "token-value-abc";
    private static final String COOKIE_NAME = "color";
    private static final String COOKIE_VALUE = "blue";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // -------------------------------------------------------------
        // 1. PRE-FILTER LOGIC
        //    (Code here runs BEFORE the request reaches the microservice)
        // -------------------------------------------------------------
        logger.info("EXECUTION START: Pre-filter logic initiated.");

        /*
         * In WebFlux, the 'exchange' object is immutable.
         * To modify the request (e.g., add a header), we must create a copy using .mutate()
         */
        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(builder -> builder.header(HEADER_NAME, HEADER_VALUE))
                .build();

        // -------------------------------------------------------------
        // 2. CHAIN EXECUTION & POST-FILTER LOGIC
        //    (We pass the modified exchange to the next filter in the chain)
        // -------------------------------------------------------------
        return chain.filter(mutatedExchange)
                .then(Mono.fromRunnable(() -> {

                    // -------------------------------------------------------------
                    // 3. POST-FILTER LOGIC
                    //    (Code here runs AFTER the microservice has responded)
                    // -------------------------------------------------------------
                    logger.info("EXECUTION END: Post-filter logic initiated.");

                    // Retrieve the token we added in the Pre-step to verify it persisted
                    String token = mutatedExchange.getRequest().getHeaders().getFirst(HEADER_NAME);

                    if (token != null) {
                        logger.info("Token verified in response phase: {}", token);

                        // Add the token to the RESPONSE headers so the client can see it
                        mutatedExchange.getResponse().getHeaders().add(HEADER_NAME, token);
                    }

                    // Add a sample cookie to the response
                    mutatedExchange.getResponse().addCookie(
                            ResponseCookie.from(COOKIE_NAME, COOKIE_VALUE).build()
                    );

                    // Modify the Content-Type of the response (just for testing)
                    //mutatedExchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
                }));
    }

}