package com.erdemorman.mdx.ui.colors;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erdemorman.mdx.R;
import com.erdemorman.mdx.data.model.MaterialColor;
import com.erdemorman.mdx.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ColorsFragment extends Fragment implements ColorsView {
    @Inject ColorsPresenter mColorsPresenter;

    @Bind(R.id.tab_layout) TabLayout mTabLayout;
    @Bind(R.id.view_pager) ViewPager mViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mColorsPresenter.loadColors();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mColorsPresenter.detachView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_colors, container, false);
        ButterKnife.bind(this, fragmentView);
        mColorsPresenter.attachView(this);

        return fragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle(R.string.colors_title);
    }

    @Override
    public void showColors(final List<MaterialColor> colors) {
        ColorsFragmentPagerAdapter pagerAdapter = new ColorsFragmentPagerAdapter(
                getFragmentManager(), colors);
        mViewPager.setAdapter(pagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        setTabIcons(colors);

        updateTabIndicatorColor(colors, mTabLayout.getSelectedTabPosition());

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                updateTabIndicatorColor(colors, position);
            }
        });
    }

    private void updateTabIndicatorColor(List<MaterialColor> colors, int position) {
        MaterialColor materialColor = colors.get(position);
        mTabLayout.setSelectedTabIndicatorColor(Color.parseColor(
                materialColor.getPrimaryColor()));
    }

    private void setTabIcons(List<MaterialColor> colors) {
        for (int index = 0; index < mTabLayout.getTabCount(); index++) {
            MaterialColor materialColor = colors.get(index);
            Drawable icon = ContextCompat.getDrawable(getContext(), R.drawable.colors_tab_icon);

            icon.setColorFilter(Color.parseColor(materialColor.getPrimaryColor()),
                    PorterDuff.Mode.SRC_ATOP);

            TabLayout.Tab tab = mTabLayout.getTabAt(index);
            if (tab != null) {
                tab.setIcon(icon);
            }
        }
    }
}
