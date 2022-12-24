package com.flaneurette.twigpage;

import android.app.Activity;
import android.content.Intent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {

    public int status;
    public String original;
    public boolean shouldOverrideUrlLoading;

    public int status(WebView view, WebResourceRequest request) {
        return status;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (this.original.equals(request.getUrl().getHost())) {
            this.shouldOverrideUrlLoading = false;
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
        Activity activity = new Activity();
        activity.startActivity(intent);
        this.shouldOverrideUrlLoading = true;
        return true;
    }

    public boolean getOriginal() {
        return this.shouldOverrideUrlLoading;
    }

}