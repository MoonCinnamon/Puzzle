package com.cinnamon.moon.puzzle.DirectMessage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.content.Context;
import android.util.Log;
import twitter4j.Twitter;

/**
 * Created by moonp on 2017-01-13.
 */

public class setMessage extends Thread {

    private Twitter twitter;
    private MakeXml xml;

    public setMessage(Twitter twitter, Context context) {
        this.twitter = twitter;
        xml = new MakeXml(context);
    }

    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        ResultMessage message = new ResultMessage();
        Runnable rRecip = new getRecipientTask(twitter, message);
        Runnable rSent = new getSentTask(twitter, message);

        // 스레드풀의 작업 큐에 저장
        Future<ResultMessage> fRecip = executorService.submit(rRecip, message);
        Future<ResultMessage> fSent = executorService.submit(rSent, message);

        try {
            message = fRecip.get();
            message = fSent.get();
            xml.OutputXml(message.sortDirectMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
