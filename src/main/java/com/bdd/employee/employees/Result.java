package com.bdd.employee.employees;

public class Result<T> {
    private ErrorEnum errorNumber;
    private String errorMessage;
    private T result;

    public Result(T result) {
        this.result = result;
        this.errorNumber = ErrorEnum.Success;
        this.errorMessage = "";
    }

    public Result(ErrorEnum errorNumber, String errorMessage) {
        this.errorNumber = errorNumber;
        this.errorMessage = errorMessage;
        result = null;
    }

    public ErrorEnum getErrorNumber() {
        return errorNumber;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public T getResult() {
        return result;
    }
}
