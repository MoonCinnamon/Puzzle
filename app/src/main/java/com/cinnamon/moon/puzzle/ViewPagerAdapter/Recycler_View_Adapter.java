package com.cinnamon.moon.puzzle.ViewPagerAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cinnamon.moon.puzzle.R;
import com.cinnamon.moon.puzzle.TimeLine.HomeTimeLine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import twitter4j.JSONObjectType;
import twitter4j.MediaEntity;
import twitter4j.Status;

/**
 * Created by moonp on 2017-02-04.
 */
public class Recycler_View_Adapter extends RecyclerView.Adapter<Recycler_View_Adapter.View_Holder> {

    List<Status> list = new ArrayList<>();
    private Context context;

    public Recycler_View_Adapter(Context context) {
        this.context = context;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_timeline_item, parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(View_Holder holder, int position) {
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        Glide.with(context).load(list.get(position).getUser().getBiggerProfileImageURL()).into(holder.profile);
        if (list.get(position).getUser().isProtected())
            holder.lock.setVisibility(View.VISIBLE);
        if (list.get(position).getMediaEntities().length != 0) {
            switch (list.get(position).getMediaEntities()[0].getType()) {
                case "photo":
                    int num = 0;
                    for (MediaEntity entity : list.get(position).getMediaEntities()) {
                        holder.image[num].setVisibility(View.VISIBLE);
                        Glide.with(context).load(entity.getMediaURL()).into(holder.image[num]);
                    }
                    break;
            }
        }
        holder.name.setText(list.get(position).getUser().getName());
        holder.screenName.setText(list.get(position).getUser().getScreenName());
        holder.contentText.setText(list.get(position).getText());
        holder.via.setText(list.get(position).getSource());
        holder.time.setText(getDate(list.get(position).getCreatedAt()));
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public class View_Holder extends RecyclerView.ViewHolder {
        private ImageView profile, love, retweet, lovetop, lock, qouteImage1, qouteImage2, qouteImage3, qouteImage4;
        private TextView name, screenName, contentText, qouteName, qouteScreenName, qouteContent, retweetPanel, via, time;
        private ImageView[] image = new ImageView[4];

        View_Holder(View itemView) {
            super(itemView);
            profile = (ImageView) itemView.findViewById(R.id.profile);
            love = (ImageView) itemView.findViewById(R.id.love);
            retweet = (ImageView) itemView.findViewById(R.id.retweet);
            lovetop = (ImageView) itemView.findViewById(R.id.loveTop);
            lock = (ImageView) itemView.findViewById(R.id.lock);
            qouteImage1 = (ImageView) itemView.findViewById(R.id.qouteImage1);
            qouteImage2 = (ImageView) itemView.findViewById(R.id.qouteImage2);
            qouteImage3 = (ImageView) itemView.findViewById(R.id.qouteImage3);
            qouteImage4 = (ImageView) itemView.findViewById(R.id.qouteImage4);
            image[0] = (ImageView) itemView.findViewById(R.id.image1);
            image[1] = (ImageView) itemView.findViewById(R.id.image2);
            image[2] = (ImageView) itemView.findViewById(R.id.image3);
            image[3] = (ImageView) itemView.findViewById(R.id.image4);

            name = (TextView) itemView.findViewById(R.id.name);
            screenName = (TextView) itemView.findViewById(R.id.screenName);
            contentText = (TextView) itemView.findViewById(R.id.contentText);
            qouteName = (TextView) itemView.findViewById(R.id.qouteName);
            qouteScreenName = (TextView) itemView.findViewById(R.id.qouteScreenName);
            qouteContent = (TextView) itemView.findViewById(R.id.qouteContent);
            retweetPanel = (TextView) itemView.findViewById(R.id.retweetPanel);
            via = (TextView) itemView.findViewById(R.id.via);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }


    public void add(int num, Status status) {
        list.add(num, status);
        notifyDataSetChanged();
    }

    public void addAll(List<Status> statuses) {
        list.addAll(statuses);
        Log.d("recycle " , String.valueOf(list.size()));
        notifyDataSetChanged();
    }


    public String getDate(Date time) {
        String rTime = "";

        long nTime = System.currentTimeMillis();

        long show = (nTime - time.getTime()) / 1000;

        if (show < 30)
            rTime = "- " + "NowTweet";
        else if (show < 60)
            rTime = "- " + "30초 경과";
        else if (show < 3600)
            rTime = "- " + String.valueOf(show / 60) + "분 경과";
        else if (show < 86400)
            rTime = "- " + String.valueOf(show / 3600) + "시간 경과";
        else {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일");
            Date date = new Date();
            rTime = "- " + df.format(date);
        }

        return rTime;
    }
}