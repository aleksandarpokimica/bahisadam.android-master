package com.bahisadam.model;

/**
 * Created by fatih on 17.06.2017.
 */
public class BaseResponse<T> {
    public boolean isSuccess;
    public String errorType;
    public String error;
    public T data;
}
