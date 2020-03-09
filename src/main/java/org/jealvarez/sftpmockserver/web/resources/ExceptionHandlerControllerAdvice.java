package org.jealvarez.sftpmockserver.web.resources;

import org.jealvarez.sftpmockserver.web.factories.ApiResponseFactory;
import org.jealvarez.sftpmockserver.web.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.jealvarez.sftpmockserver.web.model.ErrorCode.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

    private final ApiResponseFactory apiResponseFactory;

    public ExceptionHandlerControllerAdvice(final ApiResponseFactory apiResponseFactory) {
        this.apiResponseFactory = apiResponseFactory;
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse handleException(final Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        return apiResponseFactory.buildInternalServerErrorResponse(INTERNAL_SERVER_ERROR);
    }

}
