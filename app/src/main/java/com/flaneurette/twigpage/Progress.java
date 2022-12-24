package com.flaneurette.twigpage;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import com.flaneurette.twigpage.MyWebViewClient;

public class Progress {

    public boolean urlOverload = new MyWebViewClient().shouldOverrideUrlLoading;

    Context mContext;

    public Progress(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void uploadSettings(String progress) throws InterruptedException {
        if(urlOverload == false) {
            Toast.makeText(mContext, progress, Toast.LENGTH_LONG).show();
        }
    }
}
