package com.cinnamon.moon.puzzle.DirectMessage;

import twitter4j.DirectMessage;

import java.util.Comparator;

/**
 * Created by moonp on 2016-12-13.
 */
public class DirectMessageComparator implements Comparator<DirectMessage> {
    @Override
    public int compare(DirectMessage o1, DirectMessage o2) {
        long firstValue = o1.getCreatedAt().getTime();
        long secondValue = o2.getCreatedAt().getTime();

        if (firstValue > secondValue)
            return -1;
        else if (firstValue < secondValue)
            return 1;
        else
            return 0;
    }
}
