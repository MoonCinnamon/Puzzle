package com.cinnamon.moon.puzzle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.cinnamon.moon.puzzle.ViewPagerAdapter.ImageViewpager;
import uk.co.senab.photoview.PhotoView;

import java.util.ArrayList;

/**
 * Created by Moon-Cinnamon on 2016. 3. 16..
 */
public class ImageActivity extends DialogFragment {

    private ViewPager viewPager;

    public static ImageActivity newInstance(String[] url, int number) {
        ImageActivity frag = new ImageActivity();
        Bundle args = new Bundle();
        args.putStringArray("image_array", url);
        args.putInt("number", number);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.timline_image_viewer, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ImageViewpager) view.findViewById(R.id.imageViewer);
        String[] image_url = getArguments().getStringArray("image_array");

        ImageViewPagerAdapter adapter = new ImageViewPagerAdapter();

        assert image_url != null;
        for (String url : image_url) {
            adapter.addView(url);
        }
        //ViewPager에 Adapter 설정
        viewPager.setAdapter(adapter);

        viewPager.setCurrentItem(getArguments().getInt("number"));


        if (savedInstanceState != null) {
            boolean isLocked = savedInstanceState.getBoolean("isLock", false);
            ((ImageViewpager) viewPager).setLocked(isLocked);
        }
    }

    static class ImageViewPagerAdapter extends PagerAdapter {

        private ArrayList<String> view_array = new ArrayList<String>();

        @Override
        public int getCount() {
            return view_array.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            Glide.with(container.getContext()).load(view_array.get(position)).into(photoView);
            container.addView(photoView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        public void addView(String url) {
            view_array.add(url);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    private boolean isViewPagerActive() {
        return (viewPager != null && viewPager instanceof ImageViewpager);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (isViewPagerActive()) {
            outState.putBoolean("isLock", ((ImageViewpager) viewPager).isLocked());
        }
        super.onSaveInstanceState(outState);
    }
}
