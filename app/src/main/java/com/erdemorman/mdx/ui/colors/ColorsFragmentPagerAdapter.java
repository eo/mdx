package com.erdemorman.mdx.ui.colors;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.erdemorman.mdx.data.model.MaterialColor;

import java.util.List;

public class ColorsFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<MaterialColor> mColors;

    public ColorsFragmentPagerAdapter(FragmentManager fm, List<MaterialColor> materialColors) {
        super(fm);
        mColors = materialColors;
    }

    public List<MaterialColor> getColors() {
        return mColors;
    }

    @Override
    public Fragment getItem(int position) {
        return ColorTonesFragment.newInstance(mColors.get(position));
    }

    @Override
    public int getCount() {
        return mColors.size();
    }
}
