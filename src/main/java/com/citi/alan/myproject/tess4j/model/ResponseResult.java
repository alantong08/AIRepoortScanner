package com.citi.alan.myproject.tess4j.model;

import java.io.Serializable;

public class ResponseResult  implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private boolean status;
    private String view;
    private String message;
    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public String getView() {
        return view;
    }
    public void setView(String view) {
        this.view = view;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
}
