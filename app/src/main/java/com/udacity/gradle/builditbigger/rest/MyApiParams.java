package com.udacity.gradle.builditbigger.rest;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Andre Martins dos Santos on 22/06/2018.
 */
public class MyApiParams {

    public static final String SAY_HI = "sayHi";
    public static final String DO_JOKE = "doJoke";
    @StringDef({SAY_HI,DO_JOKE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ApiMethods{}

    @ApiMethods
    private String method;
    private Object[] params;

    public MyApiParams(@ApiMethods String method, Object[] params){
        this.method = method;
        this.params = params;
    }
    public String getMethod() {
        return method;
    }
    public Object[] getParams() {
        return params;
    }
}