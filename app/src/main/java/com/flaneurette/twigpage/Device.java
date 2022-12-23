package com.flaneurette.twigpage;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class Device {

    public boolean shouldOverrideUrlLoading;

    Context mContext;

    public Device(Context c) {
        mContext = c;
    }

    String deviceDensity = null;

    @JavascriptInterface
    public CharSequence density(String density)  {
        if(shouldOverrideUrlLoading == false) {
            if(density == "high") {
                deviceDensity = "high";
            } else {
                deviceDensity = "low";
            }
        }
        return deviceDensity;
    }
}