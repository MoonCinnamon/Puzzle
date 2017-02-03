package com.cinnamon.moon.puzzle.DirectMessage;

import java.util.List;

import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Created by moonp on 2017-01-13.
 */

public class getRecipientTask implements Runnable {

    private ResultMessage result;
    private Twitter twitter;

    public getRecipientTask(Twitter twitter, ResultMessage result) {
        this.result = result;
        this.twitter = twitter;
    }

    @Override
    public void run() {
        try {
            Paging paging = new Paging(1, 50);
            List<DirectMessage> directMessages;
            do {
                directMessages = twitter.getDirectMessages(paging);
                paging.setPage(paging.getPage() + 1);
                result.addMessage(directMessages);
            } while (directMessages.size() > 0 && paging.getPage() < 10);
        } catch (TwitterException te) {
            te.printStackTrace();
        }
    }
}
