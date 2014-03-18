package co.parish.stephen.webviewcompat;

import android.annotation.TargetApi;
import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebView;

/**
 * Created by stephen.parish on 3/17/14.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class ChromiumWebViewCompat implements IWebViewCompat {

    private WebView mWebView;

    public ChromiumWebViewCompat(WebView webView) {
        mWebView = webView;
    }

    @Override public void evaluateJavascript(String script, ValueCallback<String> resultCallback) {
        mWebView.evaluateJavascript(script, resultCallback);
    }

}
