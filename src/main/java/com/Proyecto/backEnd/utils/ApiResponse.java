package com.Proyecto.backEnd.utils;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"message","data"})
public class ApiResponse<T> {
	private final String message;
	private final T data;

	private ApiResponse(ApiResponseBuilder builder) {
		this.message = builder.message;
		this.data = (T) builder.data;
	}

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
    
	public static class ApiResponseBuilder<T>{
		private final String message;
		private T data;
		
        public ApiResponseBuilder(String message) {
                 this.message = message;
        }
        public ApiResponseBuilder<T> withData(T data) {
            this.data = data;
            return this;
        }

        public ResponseEntity<ApiResponse> build() {
            ApiResponse<T> apiResponse = new ApiResponse<>(this);
            return ResponseEntity.ok().body(apiResponse);
        }
	}

}
