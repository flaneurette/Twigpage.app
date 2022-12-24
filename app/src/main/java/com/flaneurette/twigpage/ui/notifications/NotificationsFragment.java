package com.flaneurette.twigpage.ui.notifications;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.flaneurette.twigpage.MyWebViewClient;
import com.flaneurette.twigpage.R;
import com.flaneurette.twigpage.Device;
import com.flaneurette.twigpage.Progress;
import com.flaneurette.twigpage.Toaster;
import com.flaneurette.twigpage.databinding.FragmentNotificationsBinding;
import com.flaneurette.twigpage.MyWebViewClient;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private static final String website = "https://www.twigpage.com/mentions";
    private static final String original = "www.twigpage.com";
    private ValueCallback<Uri[]> mFilePathCallback;
    public int status;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        WebView webView = (WebView) rootView.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webView.setWebChromeClient(new NotificationsFragment.ChromeClient());
        webView.addJavascriptInterface(new Toaster(root.getContext()), "Android");
        webView.addJavascriptInterface(new Progress(root.getContext()), "AndroidProgress");
        webView.addJavascriptInterface(new Device(root.getContext()), "AndroidDevice");
        webView.loadUrl(website);
        MyWebViewClient webviewclient = new MyWebViewClient();
        webviewclient.setOriginal(original);
        boolean overload = webviewclient.shouldOverrideUrlLoading;
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        Uri[] results = null;

        if (resultCode == Activity.RESULT_OK) {
            String dataString = data.getDataString();
            if (dataString != null) {
                results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mFilePathCallback.onReceiveValue(results);
        mFilePathCallback = null;
    }

    public class ChromeClient extends WebChromeClient {

        public void onProgressChanged(WebView view, int progress) {
            status = progress;
        }

        public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, WebChromeClient.FileChooserParams fileChooserParams) {

            if (mFilePathCallback != null) {
                mFilePathCallback.onReceiveValue(null);
            }
            mFilePathCallback = filePath;

            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType("image/*");
            Intent[] intentArray;
            intentArray = new Intent[0];

            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
            startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}