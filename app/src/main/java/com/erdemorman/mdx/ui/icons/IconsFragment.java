package com.erdemorman.mdx.ui.icons;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.erdemorman.mdx.R;
import com.erdemorman.mdx.data.model.MaterialIconGroup;
import com.erdemorman.mdx.ui.base.BaseActivity;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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
        mIconsRecycler.addItemDecoration(new IconGroupDividerItemDecoration(
                getActivity(), R.drawable.divider_icons_group_header));

        mIconsRecycler.setAdapter(mIconsAdapter);

        final GridLayoutManager layoutManager = (GridLayoutManager)(mIconsRecycler.getLayoutManager());
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (mIconsAdapter.isGroupHeader(position)) ? layoutManager.getSpanCount() : 1;
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
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().setTitle(R.string.icons_title);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_icons, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.icon_search_hint));
        // reset max width to device width to make search view full width
        searchView.setMaxWidth(getResources().getDisplayMetrics().widthPixels);

        RxSearchView.queryTextChanges(searchView)
                .debounce(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence query) {
                        mIconsPresenter.loadIcons(query);
                    }
                });
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
