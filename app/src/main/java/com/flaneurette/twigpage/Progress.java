package com.flaneurette.twigpage;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class Progress {

    public boolean shouldOverrideUrlLoading;

    Context mContext;

    public Progress(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void uploadSettings(String progress) throws InterruptedException {
        if(shouldOverrideUrlLoading == false) {
            Toast.makeText(mContext, progress, Toast.LENGTH_LONG).show();
        }
    }
}
