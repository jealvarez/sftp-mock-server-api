package org.jealvarez.sftpmockserver.web.factories;

import org.jealvarez.sftpmockserver.web.model.ApiResponse;
import org.jealvarez.sftpmockserver.web.model.Error;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@Component
public class ApiResponseFactory {

    private final ErrorResponseFactory errorResponseFactory;

    public ApiResponseFactory(final ErrorResponseFactory errorResponseFactory) {
        this.errorResponseFactory = errorResponseFactory;
    }

    public <T> ApiResponse buildOkResponse(final T body) {
        return new ApiResponse(body, OK);
    }

    public ApiResponse buildOkResponse() {
        return new ApiResponse(NO_CONTENT);
    }

    public ApiResponse buildInternalServerErrorResponse(final Error error) {
        return new ApiResponse(errorResponseFactory.buildErrorResponse(error), INTERNAL_SERVER_ERROR);
    }

}
