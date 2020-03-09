package org.jealvarez.sftpmockserver.web.factories;

import org.jealvarez.sftpmockserver.web.model.Error;
import org.jealvarez.sftpmockserver.web.model.ErrorResponse;
import org.springframework.stereotype.Component;

@Component
public class ErrorResponseFactory {

    public ErrorResponse buildErrorResponse(final Error error) {
        return new ErrorResponse(error);
    }

}
