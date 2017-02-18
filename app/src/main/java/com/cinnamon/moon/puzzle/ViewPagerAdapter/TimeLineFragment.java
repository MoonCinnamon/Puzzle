package com.cinnamon.moon.puzzle.ViewPagerAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import android.widget.FrameLayout;

import com.cinnamon.moon.puzzle.R;

import twitter4j.Status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moon-Cinnamon on 2016. 3. 7..
 */
@SuppressLint("ValidFragment")
public class TimeLineFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    private String name;
    private String url;
    private boolean Open = false;
    private FloatingActionButton photo;
    private FloatingActionButton gallery;
    private FloatingActionButton tweet;
    private Animation showPhoto;
    private Animation hidePhoto;
    private Animation showGallery;
    private Animation hideGallery;
    private Animation showTweet;
    private Animation hideTweet;
    private Recycler_View_Adapter adapter;
    private RecyclerView timeline;

    // newInstance constructor for creating fragment with arguments
    public static TimeLineFragment newInstance(int page, String title, SparseArray list_adapter) {
        TimeLineFragment fragmentFirst = new TimeLineFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        args.putString("name", title);
        args.putSparseParcelableArray("adapter", list_adapter);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    public void setListadapter(Recycler_View_Adapter adapter) {
        this.adapter = adapter;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments() != null ? getArguments().getInt("num") : 1;
        name = getArguments() != null ? getArguments().getString("name") : "";
//        url = getArguments() != null ? getArguments().getString("url") : "";
        Log.d("pagenumber", String.valueOf(page));
        adapter = getArguments() != null ? (Recycler_View_Adapter) getArguments().getSparseParcelableArray("adapter").get(page) : null;
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_timeline, container, false);
        //  final TweetSend tweetSend = new TweetSend(Login_Data.getTwitter(mNum / 10));
        //이 부분에서 animation을 설정해준다
        showPhoto = AnimationUtils.loadAnimation(getContext(), R.anim.photo_show);
        hidePhoto = AnimationUtils.loadAnimation(getContext(), R.anim.photo_hide);
        showGallery = AnimationUtils.loadAnimation(getContext(), R.anim.gallery_show);
        hideGallery = AnimationUtils.loadAnimation(getContext(), R.anim.gallery_hide);
        showTweet = AnimationUtils.loadAnimation(getContext(), R.anim.tweet_show);
        hideTweet = AnimationUtils.loadAnimation(getContext(), R.anim.tweet_hide);

        //여기서부터 id값 등록
        final FloatingActionButton open = (FloatingActionButton) view.findViewById(R.id.open);
        photo = (FloatingActionButton) view.findViewById(R.id.photo);
        gallery = (FloatingActionButton) view.findViewById(R.id.gallery);
        tweet = (FloatingActionButton) view.findViewById(R.id.tweet);
        timeline = (RecyclerView) view.findViewById(R.id.timeline);
        View profile = view.findViewById(R.id.profile);
        final View content = view.findViewById(R.id.content);
        View send = view.findViewById(R.id.send);

        open.setOnClickListener(openFab);

        final InputMethodManager inputManager = (InputMethodManager)
                getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        timeline.setAdapter(adapter);
        timeline.setLayoutManager(new LinearLayoutManager(getContext()));
        timeline.setOnTouchListener(hideFap);

        return view;
    }


    public void addItem(int num, Status status) {
        this.adapter.add(num, status);
    }

    public void addItem(List<Status> statuses) {
        if (this.adapter != null)
            adapter.addAll(statuses);
        else
            Log.d("adapter", " : null");
    }


    private View.OnTouchListener hideFap = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (Open) {
                hideFAB();
                Open = false;
            }
            return false;
        }
    };


    private View.OnClickListener openFab = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (Open == false) {
                expandFAB();
                Open = true;
            } else {
                hideFAB();
                Open = false;
            }
        }
    };

    private void expandFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) photo.getLayoutParams();
        layoutParams.rightMargin += (int) (photo.getWidth() * 1.7);
        layoutParams.bottomMargin += (int) (photo.getHeight() * 0.25);
        photo.setLayoutParams(layoutParams);
        photo.startAnimation(showPhoto);
        photo.setClickable(true);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) gallery.getLayoutParams();
        layoutParams2.rightMargin += (int) (gallery.getWidth() * 1.5);
        layoutParams2.bottomMargin += (int) (gallery.getHeight() * 1.5);
        gallery.setLayoutParams(layoutParams2);
        gallery.startAnimation(showGallery);
        gallery.setClickable(true);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) tweet.getLayoutParams();
        layoutParams3.rightMargin += (int) (tweet.getWidth() * 0.25);
        layoutParams3.bottomMargin += (int) (tweet.getHeight() * 1.7);
        tweet.setLayoutParams(layoutParams3);
        tweet.startAnimation(showTweet);
        tweet.setClickable(true);
    }


    private void hideFAB() {

        //Floating Action Button 1
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) photo.getLayoutParams();
        layoutParams.rightMargin -= (int) (photo.getWidth() * 1.7);
        layoutParams.bottomMargin -= (int) (photo.getHeight() * 0.25);
        photo.setLayoutParams(layoutParams);
        photo.startAnimation(hidePhoto);
        photo.setClickable(false);

        //Floating Action Button 2
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) gallery.getLayoutParams();
        layoutParams2.rightMargin -= (int) (gallery.getWidth() * 1.5);
        layoutParams2.bottomMargin -= (int) (gallery.getHeight() * 1.5);
        gallery.setLayoutParams(layoutParams2);
        gallery.startAnimation(hideGallery);
        gallery.setClickable(false);

        //Floating Action Button 3
        FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) tweet.getLayoutParams();
        layoutParams3.rightMargin -= (int) (tweet.getWidth() * 0.25);
        layoutParams3.bottomMargin -= (int) (tweet.getHeight() * 1.7);
        tweet.setLayoutParams(layoutParams3);
        tweet.startAnimation(hideTweet);
        tweet.setClickable(false);
    }

}
