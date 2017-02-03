package com.cinnamon.moon.puzzle.Util;

import android.os.Handler;
import android.util.Log;
import com.cinnamon.moon.puzzle.Login.LoginData;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;


/**
 * Created by moonp on 2017-02-03.
 */
public class Tweet implements Runnable {

    private static int id = 0;
    private Twitter twitter;
    private String message;
    private Handler handler;

    public void setId(int idnum){
        this.id = idnum;
    }

    public Tweet(Handler handler ){
        this.handler = handler;
    }

    public void setMessage(String message){
        this.message = message;
    }

    @Override
    public void run() {
        twitter = LoginData.getTwitter(id);
        try {
            Log.d("message :",message);
            Status status = twitter.updateStatus(message);
            handler.sendEmptyMessage(0);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
