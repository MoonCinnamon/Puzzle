package com.cinnamon.moon.puzzle.Login;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.webkit.WebView;

import com.cinnamon.moon.puzzle.Data.PreferencesUtils;


import java.util.concurrent.Callable;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by moonp on 2017-01-08.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class OauthThread implements Callable<AccessToken> {

    private String pincode = "";
    private String[] array;

    public OauthThread(String pincode) {
        this.pincode = pincode;
    }

    public OauthThread(String[] array) {
        this.array = array;
    }

    @Override
    public AccessToken call() throws Exception {
        AccessToken accessToken = null;
        try {
            if (pincode.length() > 0) {
                accessToken =
                        LoginData.getTwitter(LoginData.getNum() - 1)
                                .getOAuthAccessToken(LoginData.getToken(), pincode);
            } else {
                TwitterFactory factory = new TwitterFactory();
                accessToken = new AccessToken(array[1], array[2]);
                Twitter twitter = factory.getInstance();
                twitter.setOAuthConsumer(array[3], array[4]);
                twitter.setOAuthAccessToken(accessToken);
                LoginData.setTwitter(LoginData.getNum(), twitter);
                LoginData.setUser(LoginData.getNum()-1, twitter.showUser(twitter.getId()));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return accessToken;
    }
}
