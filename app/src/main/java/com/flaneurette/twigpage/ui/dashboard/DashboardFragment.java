package com.flaneurette.twigpage.ui.dashboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.flaneurette.twigpage.R;
import com.flaneurette.twigpage.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private WebView webview;
    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private static final String website = "https://www.twigpage.com/timeline";
    private static final String original = "www.twigpage.com/timeline";
    private ValueCallback<Uri[]> mFilePathCallback;
    private ProgressBar progressBar;
    public boolean shouldOverrideUrlLoading;
    public int status;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        WebView webView = (WebView) rootView.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webView.setWebChromeClient(new DashboardFragment.ChromeClient());
        webView.addJavascriptInterface(new DashboardFragment.Toaster(root.getContext()), "Android");
        webView.addJavascriptInterface(new DashboardFragment.Progress(root.getContext()), "AndroidProgress");
        webView.addJavascriptInterface(new DashboardFragment.Device(root.getContext()), "AndroidDevice");
        webView.loadUrl(website);

        return rootView;

    }

    private class MyWebViewClient extends WebViewClient {

        public int status(WebView view, WebResourceRequest request) {
            return status;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (original.equals(request.getUrl().getHost())) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
            startActivity(intent);
            return true;
        }
    }

    /** The Toaster is called from twigpage.com, inside main.js which triggers a Android dialog */

    public class Toaster {

        Context mContext;

        Toaster(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showToast(String toast) {
            if(shouldOverrideUrlLoading == false) {
                Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class Progress {

        Context mContext;

        Progress(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void uploadSettings(String progress) throws InterruptedException {

            if(shouldOverrideUrlLoading == false) {
                Toast.makeText(mContext, progress, Toast.LENGTH_LONG).show();
            }
        }
    }

    public class Device {

        Context mContext;

        Device(Context c) {
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

    // File chooser.

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

        // For Android 5.0
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