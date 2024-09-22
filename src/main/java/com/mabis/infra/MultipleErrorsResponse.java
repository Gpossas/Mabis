package com.mabis.infra;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Getter
@Setter
public class MultipleErrorsResponse
{
    private HttpStatus http_status;
    private List<HashMap<String, String>> errors = new ArrayList<>();

    public MultipleErrorsResponse(HttpStatus http_status, List<FieldError> errors)
    {
        this.http_status = http_status;
        errors.forEach( (error) -> {
            HashMap<String, String> error_map = new HashMap<>();
            error_map.put("field", error.getField());
            error_map.put("message", error.getDefaultMessage());
            this.errors.add(error_map);
        });
    }

}
