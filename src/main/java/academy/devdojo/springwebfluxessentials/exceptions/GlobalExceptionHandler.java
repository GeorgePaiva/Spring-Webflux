package academy.devdojo.springwebfluxessentials.exceptions;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.OptionalInt;

import static org.springframework.boot.web.error.ErrorAttributeOptions.defaults;

@Order(-2)
@Component
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalExceptionHandler(
            ErrorAttributes errorAttributes,
            WebProperties.Resources resources,
            ServerCodecConfigurer serverCodecConfigurer,
            ApplicationContext applicationContext) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }


    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::formatErrorResponse);
    }

    private Mono<ServerResponse> formatErrorResponse(ServerRequest request) {
        String query = request.uri().getQuery();
        ErrorAttributeOptions errorAttributeOptions = isTraceEnable(query) ? ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE) : defaults();

        Map<String, Object> errorAttributes = getErrorAttributes(request, errorAttributeOptions);

        int status = OptionalInt.of((Integer) errorAttributes.get("status")).orElse(500);

        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorAttributes));
    }

    private boolean isTraceEnable(String query) {
        return !StringUtils.isEmpty(query) && query.contains("trace=true");
    }
}
