package jkulan.software.dto;

import java.io.Serializable;

public class RESTDataWrapperDTO<T extends Serializable> {

    private T data;

    private String errorDetails;

    public RESTDataWrapperDTO() {
    }

    public RESTDataWrapperDTO(T data, boolean success) {
        this.data = data;
        this.success = success;
    }

    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }
}