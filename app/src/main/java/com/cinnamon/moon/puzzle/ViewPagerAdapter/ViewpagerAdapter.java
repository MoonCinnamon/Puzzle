package com.cinnamon.moon.puzzle.ViewPagerAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.cinnamon.moon.puzzle.Login.LoginData;
import com.cinnamon.moon.puzzle.R;

import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moon-Cinnamon on 2016. 3. 7..
 */
public class ViewpagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

    private ArrayList<Integer> Icon;
    private Fragment mCurrentFragment;
    private ArrayList<Status> statuslist;
    private SparseArray<TimeLineFragment> adapter;
    private SparseArray<Recycler_View_Adapter> list_adapter;
    private Context context;

    public ViewpagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        Icon = new ArrayList<>();
        this.context = context;
        adapter = new SparseArray<>();
        list_adapter = new SparseArray<>();
    }

    public void addPage(int position) {
        adapter.put(position, new TimeLineFragment());
        list_adapter.put(position, new Recycler_View_Adapter(context));
        notifyDataSetChanged();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (mCurrentFragment != object) {
            mCurrentFragment = (Fragment) object;
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public TimeLineFragment getItem(int position) {
        Log.d("itemnumber", String.valueOf(position));
        TimeLineFragment fragment = adapter.get(position);
        return fragment.newInstance(position, "home", list_adapter);
    }


    public void addTweet(int position, List<Status> status) {
        Log.d("tweetSize", String.valueOf(status.size()));
        list_adapter.get(position).addAll(status);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    //가져와라 뷰의 갯수를
    @Override
    public int getCount() {
        return adapter.size();
    }

    //뷰를 더하는 부분이다 이 부분도 딱히 의미는 없는거 같다
    /*
    public void addView(int value) {
        switch (value % 10) {
            case 0:
                Icon.add(R.drawable.ic_alarm);
                break;
            case 1:
                Icon.add(R.drawable.ic_home);
                break;
            case 2:
                Icon.add(R.drawable.ic_message);
                break;
            case 3:
                Icon.add(R.drawable.ic_activity);
                break;
        }
    }*/

    //업데이트 부분이다 건들지 말자
    public void update() {
        notifyDataSetChanged();
    }

    //icon 설정부분이므로 건들지 말
    @Override
    public int getPageIconResId(int position) {
        Log.d("Show icon", String.valueOf(Icon.size()));
        if (Icon.size() != 0) {
            Log.d("Show icon", String.valueOf(position));
            return Icon.get(position);
        } else
            return R.mipmap.ic_error;
    }
}
