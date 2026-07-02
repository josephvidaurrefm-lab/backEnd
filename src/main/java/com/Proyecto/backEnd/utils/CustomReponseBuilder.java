package com.Proyecto.backEnd.utils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomReponseBuilder {
	
    public ResponseEntity <ApiResponse> buildResponse(String message, Object data) {
        return new ApiResponse.ApiResponseBuilder<>(message).withData(data).build(); 
    }
    
}
