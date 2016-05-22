package com.erdemorman.mdx.ui.colors;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erdemorman.mdx.R;
import com.erdemorman.mdx.data.model.MaterialColor;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ColorTonesFragment extends Fragment {
    public final static String ARG_MATERIAL_ICON = "ARG_MATERIAL_ICON";

    private MaterialColor mMaterialColor;

    @Bind(R.id.recycler_view) RecyclerView mTonesRecycler;

    public static ColorTonesFragment newInstance(MaterialColor materialColor) {
        ColorTonesFragment fragment = new ColorTonesFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_MATERIAL_ICON, materialColor);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMaterialColor = getArguments().getParcelable(ARG_MATERIAL_ICON);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_color_tones, container, false);
        ButterKnife.bind(this, fragmentView);

        mTonesRecycler.setHasFixedSize(true);
        mTonesRecycler.setAdapter(new ColorTonesAdapter(getContext(), mMaterialColor));

        return fragmentView;
    }
}
