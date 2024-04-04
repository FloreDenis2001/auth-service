package org.authservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ListEmptyException extends RuntimeException{

    public ListEmptyException() {
        super("The list is empty!");
    }
}

