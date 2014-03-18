package co.parish.stephen.webviewcompat;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import javax.inject.Inject;

import dagger.ObjectGraph;

public class WebViewCompat extends WebView implements IWebViewCompat {

    // region Constructors

    public WebViewCompat(Context context) {
        super(context);
        setupJavascript();
    }

    public WebViewCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupJavascript();
    }

    public WebViewCompat(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setupJavascript();
    }

    // endregion

    @Inject IWebViewCompat mWebViewCompat;

    @Override public void evaluateJavascript(String script, ValueCallback<String> resultCallback) {
        mWebViewCompat.evaluateJavascript(script, resultCallback);
    }

    private ObjectGraph mObjectGraph;

    private void setupJavascript() {
        mObjectGraph = ObjectGraph.create(new WebViewModule(this));
    }

    @Override public void destroy() {
        mObjectGraph = null;
        super.destroy();
    }

}
