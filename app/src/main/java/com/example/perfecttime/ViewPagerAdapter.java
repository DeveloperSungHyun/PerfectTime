package com.example.perfecttime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.perfecttime.Fragment.FragAll;
import com.example.perfecttime.Fragment.FragDate;
import com.example.perfecttime.Fragment.FragHome;
import com.example.perfecttime.Fragment.FragWeek;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: return FragHome.newInstance();
            case 1: return FragAll.newInstance();
            case 2: return FragWeek.newInstance();
            case 3: return FragDate.newInstance();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "홈";
            case 1: return "매일";
            case 2: return "요일";
            case 3: return "날짜";
            default: return null;
        }
    }
}
