package com.java.librarymanagement.advice;

public record ApiResponse<T>(
        T data,
        String message
) {
}
