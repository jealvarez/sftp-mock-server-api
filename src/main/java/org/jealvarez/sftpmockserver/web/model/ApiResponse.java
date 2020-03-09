package org.jealvarez.sftpmockserver.web.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse extends ResponseEntity {

    public ApiResponse(final Object body, final HttpStatus httpStatus) {
        super(body, httpStatus);
    }

    public ApiResponse(final HttpStatus httpStatus) {
        super(httpStatus);
    }

}
