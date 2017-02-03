package com.cinnamon.moon.puzzle.DirectMessage;

import java.util.List;

import android.util.Log;
import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by moonp on 2017-01-13.
 */

public class getSentTask implements Runnable {

    private ResultMessage result;
    private Twitter twitter;

    public getSentTask(Twitter twitter, ResultMessage result) {
        this.result = result;
        this.twitter = twitter;
    }

    @Override
    public void run() {
        try {
            Paging page = new Paging(1, 50);
            List<DirectMessage> directMessages;
            do {
                directMessages = twitter.getSentDirectMessages(page);
                page.setPage(page.getPage() + 1);
                result.addMessage(directMessages);
            } while (directMessages.size() > 0 && page.getPage() < 10);
            Log.d("size Sent"," size :" + directMessages.size());
        } catch (TwitterException te) {
            te.printStackTrace();
        }
    }
}
