package co.parish.stephen.webviewcompat;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;

public class StockWebViewCompat implements IWebViewCompat {

    public static final String TAG = StockWebViewCompat.class.getSimpleName();

    private WebView mWebView;
    private EvaluateJavascriptInterface mJavascriptInterface;

    public StockWebViewCompat(WebView webView) {
        mWebView = webView;
        mJavascriptInterface = new EvaluateJavascriptInterface(mWebView);
        mWebView.addJavascriptInterface(mJavascriptInterface, EvaluateJavascriptInterface.INSTANCE_NAME);
    }

    @Override public void evaluateJavascript(String script, ValueCallback<String> callback) {
        mJavascriptInterface.evaluateJavacript(script, callback);
    }

    private static class EvaluateJavascriptInterface {

        static final String INSTANCE_NAME = "EvaluateJavascriptInterface";
        private static final long TIMEOUT = 5000;

        private final WebView mWebView;

        private boolean mIsEvaluated;
        private String mEvaluatedString;

        public EvaluateJavascriptInterface(WebView webView) {
            mWebView = webView;
        }

        private synchronized void notifyIsEvaluated() {
            mIsEvaluated = true;
            notify();
        }

        private synchronized void waitForResult() {
            while (!mIsEvaluated) {
                try {
                    wait(TIMEOUT);
                } catch (Exception e) {
                    continue;
                }
                if (!mIsEvaluated) {
                    Log.e(TAG + ".javascriptEvaluated", "DID NOT RETURN VALUE");
                    break;
                }
            }
            mIsEvaluated = false;
        }

        public synchronized void evaluateJavacript(final String script, final ValueCallback<String> resultCallback) {
            mWebView.post(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG + ".evaluateJavascript", script);
                    String resultFormat = "(function(){"
                            + "    var result = %s;"
                            + "    result = result instanceof Object ? JSON.stringify(result) : result;"
                            + "    return result;"
                            + "})()";
                    String result = String.format(resultFormat, script);
                    mWebView.loadUrl(String.format("javascript:%s.javascriptEvaluated(%s)", INSTANCE_NAME, result));
                    waitForResult();
                    if (resultCallback != null) resultCallback.onReceiveValue(mEvaluatedString);
                    mEvaluatedString = null;
                }
            });
        }

        @JavascriptInterface public synchronized void javascriptEvaluated(String message) {
            Log.i(TAG + ".javascriptEvaluated", message);
            mEvaluatedString = message;
            notifyIsEvaluated();
        }

    }

}
