package com.forwd.backend;

/** The object model for the data we are sending through endpoints */
class Bean {

    private String myData;

    public String getData() { return myData; }

    public void setData(String data) {
        myData = data;
    }
}