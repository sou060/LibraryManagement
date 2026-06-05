package com.java.librarymanagement.advice;

public class ApiResponse <T>{
    T data;
    String message;
    ApiResponse(T data, String message){
        this.data = data;
        this.message = message;
    }
}
