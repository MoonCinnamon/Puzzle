package com.cinnamon.moon.puzzle;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import com.cinnamon.moon.puzzle.Login.LoginThread;
import com.cinnamon.moon.puzzle.Login.OauthThread;


/**
 * Created by moonp on 2017-01-07.
 */

public class LoginActivity extends AppCompatActivity {

    private String pinnumber;
    private Handler handler;
    private EditText pinE;
    private WebView webView;
    private Intent intent;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    public void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intent = getIntent();

        webView = (WebView) findViewById(R.id.loginSite);
        handler = new Handler();

        LoginThread loginThread = new LoginThread(webView, handler);
        loginThread.start();

        Button button = (Button) findViewById(R.id.enter);
        pinE = (EditText) findViewById(R.id.pinCode);

        button.setOnClickListener(enter);

        webView.setWebViewClient(client);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JavaScriptInterface(), "PINCODE");
    }

    public WebViewClient client = new WebViewClient() {
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (url != null && url.equals("http://mobile.twitter.com/")) {
                finish();
            } else if (url != null) {
                if (view.getUrl().equals("https://twitter.com/oauth/authorize") || view.getUrl().equals("https://api.twitter.com/oauth/authorize")) {
                    view.loadUrl("javascript:window.PINCODE.getPinCode(document.getElementById('oauth_pin').innerHTML);");
                }
            }
        }
    };

    public View.OnClickListener enter = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pinnumber = pinE.getText().toString();
            if (pinnumber != null && !pinnumber.equals("")) {
                intent.putExtra("pinCode", pinnumber);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    };

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class JavaScriptInterface {
        @JavascriptInterface
        public void getPinCode(final String pin) {
            if (pin.length() > 0) {
                final String pincode = pin.substring(pin.indexOf("<code>") + 6, pin.indexOf("</code>"));
                new Thread(new Runnable() {
                    public void run() {
                        handler.post(new Runnable() {
                            public void run() {
                                if (!pincode.equals("")) {
                                    pinE.setText(pincode);
                                }
                            }

                        });
                    }
                }).start();
            }
        }
    }
}
