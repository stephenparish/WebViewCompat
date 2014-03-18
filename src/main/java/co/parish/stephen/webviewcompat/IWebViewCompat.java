package co.parish.stephen.webviewcompat;

import android.webkit.ValueCallback;

/**
 * Created by stephen.parish on 3/17/14.
 */
public interface IWebViewCompat {

    public void evaluateJavascript(String script, ValueCallback<String> resultCallback);

}
