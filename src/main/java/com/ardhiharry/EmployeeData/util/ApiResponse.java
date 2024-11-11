package com.ardhiharry.EmployeeData.util;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
  private int statusCode;
  private String message;
  private T data;
  private String error;

  private ApiResponse(int statusCode, String message, T data, String error) {
    this.statusCode = statusCode;
    this.message = message;
    this.data = data;
    this.error = error;
  }

  public static <T> ApiResponse<T> success(int statusCode, String message, T data) {
    return new ApiResponse<>(statusCode, message, data, null);
  }

  public static <T> ApiResponse<T> error(int statusCode, String message, String error) {
    return new ApiResponse<>(statusCode, message, null, error);
  }

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

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }
}
