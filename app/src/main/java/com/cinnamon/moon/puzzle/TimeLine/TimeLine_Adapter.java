package com.cinnamon.moon.puzzle.TimeLine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.cinnamon.moon.puzzle.ImageActivity;
import com.cinnamon.moon.puzzle.R;
import twitter4j.MediaEntity;
import twitter4j.Status;

/**
 * Created by Moon-Cinnamon on 2016. 3. 12..
 */
public class TimeLine_Adapter extends ArrayAdapter<Status> implements Serializable {

    private Context context;

    private SparseArray<WeakReference<View>> viewArray;
    private LayoutInflater inflater;
    private FragmentManager manager;

    public TimeLine_Adapter(Context applicationContext, int view_timeline_item, FragmentManager supportFragmentManager) {
        super(applicationContext, view_timeline_item);
        this.context = applicationContext;
        this.viewArray = new SparseArray<>();
        this.manager = supportFragmentManager;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void update() {
        viewArray.clear();
        notifyDataSetChanged();
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (viewArray != null && viewArray.get(position) != null) {
            convertView = viewArray.get(position).get();
            if (convertView != null)
                return convertView;
        }

        try {
            convertView = inflater.inflate(R.layout.view_timeline_item, null);
            final ImageView profile = (ImageView) convertView.findViewById(R.id.profile);

            ImageView loveIcon = (ImageView) convertView.findViewById(R.id.love);
            ImageView retweetIcon = (ImageView) convertView.findViewById(R.id.retweet);
            ImageView loveTopIcon = (ImageView) convertView.findViewById(R.id.loveTop);

            ImageView lock = (ImageView) convertView.findViewById(R.id.lock);

            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView screenName = (TextView) convertView.findViewById(R.id.screenName);
            TextView content = (TextView) convertView.findViewById(R.id.contentText);
            TextView via = (TextView) convertView.findViewById(R.id.via);
            TextView time = (TextView) convertView.findViewById(R.id.textView);
            TextView retweet = (TextView) convertView.findViewById(R.id.retweetPanel);
            RelativeLayout ImagePanel = (RelativeLayout) convertView.findViewById(R.id.imagePanel);
            RelativeLayout ImageQoute = (RelativeLayout) convertView.findViewById(R.id.qouteImagePanel);
            final ImageView[] imageViews = new ImageView[]{(ImageView) convertView.findViewById(R.id.image1),
                    (ImageView) convertView.findViewById(R.id.image2),
                    (ImageView) convertView.findViewById(R.id.image3),
                    (ImageView) convertView.findViewById(R.id.image4)};

            final Status status = getItem(position);
            //리트윗이면 1번 아니면 2번 둘다 아니면 3번 최적화 방법 고민해볼것
            if (status.isRetweet()) {
                assert retweet != null;
                convertView.setBackgroundColor(context.getResources().getColor(R.color.TwitterWhiteGray));
                retweetIcon.setVisibility(View.VISIBLE);
                retweet.setText(status.getRetweetedStatus().getUser().getScreenName());
                retweet.setVisibility(View.VISIBLE);
                if (getImageStatus(status).length != 0) {
                    setImage(getImageStatus(status), imageViews);
                }
            } else if (status.getUserMentionEntities().length != 0) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.TwitterWhite));
            }

            if (status.getUser().isProtected()) {
                assert lock != null;
                lock.setVisibility(View.VISIBLE);
            }

            imageViews[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = ImageActivity.newInstance(getImageStatus(status), 0);
                    newFragment.show(manager, "dialog");
                }
            });

            imageViews[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = ImageActivity.newInstance(getImageStatus(status), 1);
                    newFragment.show(manager, "dialog");
                }
            });

            imageViews[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = ImageActivity.newInstance(getImageStatus(status), 2);
                    newFragment.show(manager, "dialog");
                }
            });

            imageViews[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment newFragment = ImageActivity.newInstance(getImageStatus(status), 3);
                    newFragment.show(manager, "dialog");
                }
            });

//            assert profile != null;
//            Glide.with(context).load(status.getUser().getBiggerProfileImageURL()).bitmapTransform(new RoundedCornersTransformation(context, 15, 0,
//                    RoundedCornersTransformation.CornerType.ALL))
//                    .into(profile);
            assert name != null;
            name.setText(status.getUser().getName());
            assert screenName != null;
            screenName.setText(status.getUser().getScreenName());
            assert content != null;
            content.append(status.getText());
            assert via != null;
            via.setText(status.getSource());
            assert time != null;
            time.setText(getDate(status.getCreatedAt()));


            if (getImageStatus(status).length != 0) {
                assert ImagePanel != null;
                ImagePanel.setVisibility(View.VISIBLE);

                setImage(getImageStatus(status), imageViews);
            }
        } finally {
            viewArray.put(position, new WeakReference<View>(convertView));
        }

        return convertView;
    }

    public void setImage(String[] imageStatus, ImageView[] imageViews) {
        for (int counter = 0; counter < imageStatus.length; counter++) {
//            Glide.with(context).load(imageStatus[counter]).into(imageViews[counter]);
        }
    }

    private String[] getImageStatus(Status status) {
        MediaEntity[] entities = status.getMediaEntities();
        String[] imageStatus = new String[entities.length];

        if (!entities[0].getType().endsWith("photo")) {
            for (int imageNumber = 0; imageNumber < entities.length; imageNumber++) {
                imageStatus[imageNumber] = entities[imageNumber].getMediaURL();
            }
        } else {
            Log.d("show-url", String.valueOf(entities[0]));
            imageStatus[0] = entities[0].getMediaURL();
        }

        return imageStatus;
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
