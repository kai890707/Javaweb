package com.example.javaweb.dto.response;

public class ApiResponse<T> {

    private int statusCode; // HTTP Code
    private String message; // 描述
    private T data;         // 資料
    private String timestamp; // 響應時間戳

    public ApiResponse(int statusCode, String message, T data, String timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.timestamp = timestamp;
    }

    // 靜態方法生成成功200響應
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                200,
                "Success",
                data,
                java.time.LocalDateTime.now().toString()
        );
    }

    // 靜態方法生成錯誤響應
    public static <T> ApiResponse<T> error(int statusCode, String message) {
        return new ApiResponse<>(
                statusCode,
                message,
                null,
                java.time.LocalDateTime.now().toString()
        );
    }

    // Getters 和 Setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
