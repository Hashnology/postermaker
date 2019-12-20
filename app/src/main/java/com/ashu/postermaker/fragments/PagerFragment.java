package com.ashu.postermaker.fragments;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ashu.postermaker.R;

public class PagerFragment extends Fragment {

    private View fragment_view;
    private TabLayout sliding_tab;
    private ViewPager view_pager;

    public PagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragment_view = inflater.inflate(R.layout.pager_fragment, container, false);

        linkViews();

        sliding_tab.setupWithViewPager(view_pager);

        setViewPager(view_pager);

        return fragment_view;
    }

    private void setViewPager(ViewPager view_pager) {
        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        /*here we will call those fragment to whom we required to show in pager */
        adapter.addFragment(new FeaturedFragment(), "Featured");
        adapter.addFragment(new CategoryFragment(), "Category");
        adapter.addFragment(new CreateLogoFragment(), "Create Logo");
        adapter.addFragment(new MyDesign(), "My Design");
        view_pager.setAdapter(adapter);
    }

    private void linkViews() {
        sliding_tab = (TabLayout) fragment_view.findViewById(R.id.slide_tab);
        view_pager = (ViewPager) fragment_view.findViewById(R.id.view_pager);
    }

}
