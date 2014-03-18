package co.parish.stephen.webviewcompat;

import android.os.Build;
import android.webkit.WebView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = WebViewCompat.class)
public class WebViewModule {

    private static final boolean KITKAT = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    private WebView mWebView;

    public WebViewModule(WebView webView) {
        mWebView = webView;
    }

    @Provides @Singleton public IWebViewCompat provideWebViewCompat() {
        return KITKAT ? new ChromiumWebViewCompat(mWebView) : new StockWebViewCompat(mWebView);
    }

}
