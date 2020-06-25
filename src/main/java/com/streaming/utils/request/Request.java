package com.streaming.utils.request;

public class Request {
    private String data;
    private String method = "GET";
    private String path;

    public Request() {

    }

    public Request(final String path) {
        this.method = "GET";
        this.path = path;
    }

    public Request(final String path, final String method, final String data) {
        this.method = method;
        this.path = path;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(final String data) {
        this.data = data;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }
}
