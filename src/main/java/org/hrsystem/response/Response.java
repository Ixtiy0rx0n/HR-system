package org.hrsystem.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Response<T> {
    private String message;
    private T data;

    public Response(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
