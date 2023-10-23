package com.phucthinh.ungdungdatthucan.model;

import java.util.List;

public class LoaiMonAnModel {
    boolean success;
    String message;
    List<LoaiMonAn> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LoaiMonAn> getResult() {
        return result;
    }

    public void setResult(List<LoaiMonAn> result) {
        this.result = result;
    }
}
