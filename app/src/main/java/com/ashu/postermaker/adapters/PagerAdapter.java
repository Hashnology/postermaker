package com.ashu.postermaker.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Linez-001 on 3/22/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragment_list = new ArrayList<>();
    private ArrayList<String> title_list = new ArrayList<>();

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragment_list.get(position);
    }

    @Override
    public int getCount() {
        return fragment_list.size();
    }

    public void addFragment(Fragment fragment, String title){
        fragment_list.add(fragment);
        title_list.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title_list.get(position);
    }
}
