package com.erdemorman.mdx.ui.icons;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.erdemorman.mdx.R;
import com.erdemorman.mdx.data.model.MaterialIcon;
import com.erdemorman.mdx.data.model.MaterialIconGroup;
import com.erdemorman.mdx.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class IconsFragment extends Fragment implements IconsView {
    @Inject IconsPresenter mIconsPresenter;
    @Inject IconsAdapter mIconsAdapter;

    @Bind(R.id.recycler_view) RecyclerView mIconsRecycler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_icons, container, false);
        ButterKnife.bind(this, fragmentView);
        mIconsPresenter.attachView(this);

        mIconsRecycler.setHasFixedSize(true);
        mIconsRecycler.setAdapter(mIconsAdapter);

        final GridLayoutManager layoutManager = (GridLayoutManager)(mIconsRecycler.getLayoutManager());
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (mIconsAdapter.isGroupHeader(position))? layoutManager.getSpanCount() : 1 ;
            }
        });

        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mIconsPresenter.loadIcons();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIconsPresenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_icons, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.icon_search_hint));
        // reset max width to device width to make search view full width
        searchView.setMaxWidth(getResources().getDisplayMetrics().widthPixels);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
           default:
               return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showIconGroups(List<MaterialIconGroup> iconGroups) {
        mIconsAdapter.setIconGroups(iconGroups);
    }
}
