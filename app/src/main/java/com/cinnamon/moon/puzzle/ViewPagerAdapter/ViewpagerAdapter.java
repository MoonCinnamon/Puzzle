package com.cinnamon.moon.puzzle.ViewPagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.cinnamon.moon.puzzle.R;

import java.util.ArrayList;

/**
 * Created by Moon-Cinnamon on 2016. 3. 7..
 */
public class ViewpagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

    private ArrayList<Integer> Icon;
    private Fragment mCurrentFragment;
    //    private ArrayList<TimeLineFragment> adapter;
    private SparseArray<Fragment> adapter;

    public ViewpagerAdapter(FragmentManager fm) {
        super(fm);
        Icon = new ArrayList<>();
//        adapter = new ArrayList<>();
        adapter = new SparseArray<>();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (mCurrentFragment != object) {
            mCurrentFragment = (Fragment) object;
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        return adapter.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        adapter.put(position, fragment);
        return fragment;
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
        adapter.add(new TimeLineFragment(value));
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
