package com.cinnamon.moon.puzzle.Login;

import java.util.ArrayList;
import java.util.HashMap;

import twitter4j.Twitter;
import twitter4j.User;
import twitter4j.auth.RequestToken;

/**
 * Created by moonp on 2017-01-08.
 */

public class LoginData {
    private static ArrayList<Twitter> twitter = new ArrayList<>();
    private static ArrayList<User> user = new ArrayList<>();
    private static ArrayList<String> page = new ArrayList<>();
    private static String ConsumerKey = "XYkinCC1cdR3C9H2SYW7IWt0f";
    private static String ConsumerSecret = "fny7WyIS10kCTwZG1MiRLtpQRUiD6qompUdDrPmQOflvQOKaD9";
    private static RequestToken token;

    public static void setPage(ArrayList<String> list) {
        page = list;
    }

    public static void setPage(String page) {
        LoginData.page.add(page);
    }

    static void setTwitter(int num, Twitter twitter) {
        LoginData.twitter.add(num, twitter);
    }

    static void setToken(RequestToken token) {
        LoginData.token = token;
    }

    public static Twitter getTwitter(int num) {
        return twitter.get(num);
    }

    public static String getConsumerKey() {
        return ConsumerKey;
    }

    public static String getConsumerSecret() {
        return ConsumerSecret;
    }

    public static int getNum() {
        return twitter.size();
    }

    static RequestToken getToken() {
        return token;
    }

    static void setUser(int num, User user) {
        LoginData.user.add(num, user);
    }

    public static ArrayList<String> getPage() {
        return page;
    }

    public static User getUser(int num) {
        return user.get(num);
    }
}
