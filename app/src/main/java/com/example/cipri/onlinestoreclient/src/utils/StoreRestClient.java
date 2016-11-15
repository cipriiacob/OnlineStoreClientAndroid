package com.example.cipri.onlinestoreclient.src.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by Cipri on 12-Nov-16.
 */

public class StoreRestClient {

    private static final String BASE_URL = "http://10.0.2.2:8080";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(Context context, String url, StringEntity stringEntity, AsyncHttpResponseHandler
            responseHandler) {
        client.get(context, getAbsoluteUrl(url), stringEntity, "application/json", responseHandler);
    }

    public static void post(Context context, String url, StringEntity stringEntity, AsyncHttpResponseHandler
            responseHandler) {
        client.post(context, getAbsoluteUrl(url), stringEntity, "application/json", responseHandler);
    }

    public static void getByUrl(Context context, String url, StringEntity stringEntity, AsyncHttpResponseHandler
            responseHandler) {
        client.get(context, getAbsoluteUrl(url), stringEntity, "application/json", responseHandler);
    }

    public static void postByUrl(Context context, String url, StringEntity stringEntity, AsyncHttpResponseHandler
            responseHandler) {
        client.post(context, getAbsoluteUrl(url), stringEntity, "application/json", responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
