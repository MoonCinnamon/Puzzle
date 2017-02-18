package com.cinnamon.moon.puzzle.TimeLine;

import android.os.Handler;
import android.os.Message;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 * Created by moonp on 2017-02-18.
 */

public class HomeTimeLine extends Thread implements Runnable {

    private Handler handler;
    private Twitter twitter;

    public HomeTimeLine(Handler handler , Twitter twitter) {
        this.handler = handler;
        this.twitter = twitter;
    }

    public void run() {
        try {
            List<Status> statuses = twitter.getHomeTimeline();
            Message msg = Message.obtain();
            msg.what = 0;
            msg.obj = statuses;
            handler.sendMessage(msg);
        } catch (TwitterException te) {
            te.printStackTrace();
        }
    }
}
