package com.flaneurette.twigpage;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import com.flaneurette.twigpage.MyWebViewClient;

/** The Toaster is called from twigpage.com, inside main.js which triggers a Android dialog */

public class Toaster {

    public boolean urlOverload = new MyWebViewClient().getOriginal();

    Context mContext;

    public Toaster(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void showToast(String toast) {
        if(urlOverload == false) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }
}