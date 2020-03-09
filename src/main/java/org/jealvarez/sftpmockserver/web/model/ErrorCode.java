package org.jealvarez.sftpmockserver.web.model;

public enum ErrorCode implements Error {

    INTERNAL_SERVER_ERROR("An internal server error has occurred");

    private final String message;

    ErrorCode(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
