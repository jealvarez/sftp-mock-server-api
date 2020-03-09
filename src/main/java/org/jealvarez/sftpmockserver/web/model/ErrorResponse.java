package org.jealvarez.sftpmockserver.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Getter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(NON_EMPTY)
@JsonPropertyOrder({"name", "message"})
public class ErrorResponse {

    private final String name;
    private final String message;

    @JsonIgnore
    private final Error error;

    public ErrorResponse(final Error error) {
        this.error = error;
        this.name = error.toString();
        this.message = error.getMessage();
    }

}
