package com.cinnamon.moon.puzzle.Login;

import android.os.Handler;
import android.webkit.WebView;


import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

/**
 * Created by moonp on 2017-01-08.
 */

public class LoginThread extends Thread implements Runnable {

    private WebView webView = null;
    private Handler handler = null;

    public LoginThread() {

    }

    public LoginThread(WebView webView, Handler handler) {
        this.webView = webView;
        this.handler = handler;
    }


    public void run() {
        if (webView != null) {
            Twitter twitter = new TwitterFactory().getInstance();
            LoginData.setTwitter(LoginData.getNum(), twitter);
            twitter.setOAuthConsumer(LoginData.getConsumerKey(), LoginData.getConsumerSecret());
            try {
                RequestToken token = twitter.getOAuthRequestToken();
                LoginData.setToken(token);
                final String url = token.getAuthorizationURL();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadUrl(url);
                    }
                });
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        } else {

        }
    }
}
