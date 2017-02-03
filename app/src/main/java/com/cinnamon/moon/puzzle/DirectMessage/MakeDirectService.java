package com.cinnamon.moon.puzzle.DirectMessage;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.cinnamon.moon.puzzle.Login.LoginData;
import twitter4j.DirectMessage;

import java.util.ArrayList;

public class MakeDirectService extends IntentService {

    private ArrayList<DirectMessage> messages;

    public MakeDirectService() {
        super("directMessageService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Thread thread = new setMessage(LoginData.getTwitter(LoginData.getNum() - 1), getApplicationContext());
        thread.start();
    }
}
