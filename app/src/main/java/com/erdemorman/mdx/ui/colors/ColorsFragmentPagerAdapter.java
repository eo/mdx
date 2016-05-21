package com.erdemorman.mdx.ui.colors;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.erdemorman.mdx.data.model.MaterialColor;

import java.util.ArrayList;
import java.util.List;

public class ColorsFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<MaterialColor> mMaterialColors;

    public ColorsFragmentPagerAdapter(FragmentManager fm) {
        this(fm, new ArrayList<MaterialColor>());
    }

    public ColorsFragmentPagerAdapter(FragmentManager fm, List<MaterialColor> materialColors) {
        super(fm);
        mMaterialColors = materialColors;
    }

    @Override
    public Fragment getItem(int position) {
        return ColorTonesFragment.newInstance(mMaterialColors.get(position));
    }

    @Override
    public int getCount() {
        return mMaterialColors.size();
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mMaterialColors.get(position).getName();
//    }
}
