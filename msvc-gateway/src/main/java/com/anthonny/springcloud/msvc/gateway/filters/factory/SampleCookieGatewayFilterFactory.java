package com.anthonny.springcloud.msvc.gateway.filters.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

/**
 * Custom Gateway Filter Factory that applies ONLY to specific routes.
 * <p>
 * Unlike a GlobalFilter, this filter must be explicitly declared in the application.yml
 * under a specific route's 'filters' section. It allows passing dynamic arguments
 * from the configuration to the filter logic.
 * <p>
 * Example YAML usage:
 * <pre>
 * filters:
 * - name: ProductCookieName
 * args:
 * message: "Hello"
 * name: "user"
 * value: "Anthonny"
 * </pre>
 */
@Component
public class SampleCookieGatewayFilterFactory
        extends AbstractGatewayFilterFactory<SampleCookieGatewayFilterFactory.ConfigurationCookie> {

    private final Logger logger = LoggerFactory.getLogger(SampleCookieGatewayFilterFactory.class);

    /**
     * Constructor is required to tell the AbstractGatewayFilterFactory
     * which configuration class to use for mapping the YAML properties.
     */
    public SampleCookieGatewayFilterFactory() {
        super(ConfigurationCookie.class);
    }

    /**
     * This is where the core logic of the filter resides.
     * It receives the parsed configuration object from the YAML file.
     */
    @Override
    public GatewayFilter apply(ConfigurationCookie config) {
        return (exchange, chain) -> {

            // -------------------------------------------------------------
            // 1. PRE-FILTER LOGIC
            //    (Runs BEFORE sending the request to the specific microservice)
            // -------------------------------------------------------------
            logger.info("EXECUTION START: Pre-filter FACTORY: {}", config.getMessage());

            // -------------------------------------------------------------
            // 2. CHAIN EXECUTION & POST-FILTER LOGIC
            //    (Continues the chain and defines what to do upon response)
            // -------------------------------------------------------------
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {

                // -------------------------------------------------------------
                // 3. POST-FILTER LOGIC
                //    (Runs AFTER the microservice has responded)
                // -------------------------------------------------------------
                if (config.getValue() != null) {
                    // Add a custom cookie to the response using arguments from the YAML
                    exchange.getResponse().addCookie(
                            ResponseCookie.from(config.getName(), config.getValue()).build()
                    );
                }

                logger.info("EXECUTION END: Post-filter FACTORY: {}", config.getMessage());
            }));
        };
    }

    /**
     * Optional: Overrides the default naming convention.
     * <p>
     * By default, Spring removes the "GatewayFilterFactory" suffix, meaning the name
     * would be "SampleCookie". By overriding this method, we force the filter to be
     * recognised as "ProductCookieName" in the application.yml.
     */
    @Override
    public String name() {
        return "ProductCookieName";
    }

    /**
     * Configuration class (DTO) used to map the arguments provided in the application.yml.
     * The properties here must match the keys inside the 'args' block in the YAML.
     */
    public static class ConfigurationCookie {
        private String name;
        private String value;
        private String message;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}