package com.lizw.customviewdemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lizw.colortracktextview.ColorTrackTextView;
import com.lizw.customviewdemo.ItemFragment;
import com.lizw.customviewdemo.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
    private static final String TAG = "ViewPagerActivity";

    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华"};
    private LinearLayout mIndicatorContainer;
    private List<ColorTrackTextView> mIndicators;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        mIndicatorContainer = findViewById(R.id.ll_indicator);
        mViewPager = findViewById(R.id.view_pager);

        initIndicatorContainer();
        initViewPager();
    }

    private void initIndicatorContainer() {
        mIndicators = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            ColorTrackTextView colorTrackTextView = new ColorTrackTextView(this);
            colorTrackTextView.setText(items[i]);
            colorTrackTextView.setTextSize(30);
            colorTrackTextView.setChangeColor(Color.RED);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
            colorTrackTextView.setLayoutParams(params);
            mIndicatorContainer.addView(colorTrackTextView);

            mIndicators.add(colorTrackTextView);
        }
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "position:" + position + "    positionOffset:" + positionOffset);

                // view_pager手指左划 ColorTrackTextView->LEFT_TO_RIGHT positionOffset ：0 -> 1
                // view_pager手指右划 ColorTrackTextView->RIGHT_TO_LEFT positionOffset ：1 -> 0

                ColorTrackTextView left = mIndicators.get(position);
                left.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
                left.setCurrentProgress(1 - positionOffset);

                try {
                    ColorTrackTextView right = mIndicators.get(position + 1);
                    right.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
                    right.setCurrentProgress(positionOffset);
                } catch (Exception e) {

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}