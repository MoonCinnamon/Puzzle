package com.cinnamon.moon.puzzle.DirectMessage;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import twitter4j.DirectMessage;

/**
 * Created by moonp on 2017-01-13.
 */

public class ResultMessage {
    ArrayList<DirectMessage> messages = new ArrayList<>();
    synchronized void addMessage(List<DirectMessage> messageList){
        messages.addAll(messageList);
    }

    public ArrayList<DirectMessage> sortDirectMessage() {
        //int half_size = messages.size() / 2;
        DirectMessageComparator comp = new DirectMessageComparator();
        Collections.sort(messages, comp);
        return messages;
    }
}
