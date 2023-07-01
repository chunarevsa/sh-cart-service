package com.smarthome.shcartservice.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface ResponseUtil {
    static <RESP> ResponseEntity<RESP> wrapOrNotFound(Optional<RESP> maybeResponse) {
        return wrapOrNotFound(maybeResponse, null);
    }

    static <RESP> ResponseEntity wrapOrNotFound(Optional<RESP> maybeResponse, HttpHeaders header) {
        return maybeResponse.map((response) -> {
            return (ResponseEntity.ok().headers(header)).body(response);
        }).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }
}