package com.flaneurette.twigpage;

import android.content.Context;
import android.webkit.JavascriptInterface;
import com.flaneurette.twigpage.MyWebViewClient;

public class Device {

    public boolean urlOverload = new MyWebViewClient().shouldOverrideUrlLoading;

    Context mContext;

    public Device(Context c) {
        mContext = c;
    }

    String deviceDensity = null;

    @JavascriptInterface
    public CharSequence density(String density)  {
        if(urlOverload == false) {
            if(density == "high") {
                deviceDensity = "high";
            } else {
                deviceDensity = "low";
            }
        }
        return deviceDensity;
    }
}