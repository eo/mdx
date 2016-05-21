package com.erdemorman.mdx.ui.colors;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erdemorman.mdx.R;
import com.erdemorman.mdx.data.model.MaterialColorTone;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ColorTonesAdapter extends RecyclerView.Adapter<ColorTonesAdapter.ColorToneViewHolder> {
    private final List<MaterialColorTone> mColorTones;

    public ColorTonesAdapter(List<MaterialColorTone> colorTones) {
        mColorTones = colorTones;
    }

    @Override
    public ColorToneViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_color_tones_item_color_tone, parent, false);

        return new ColorToneViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ColorToneViewHolder holder, int position) {
        MaterialColorTone colorTone = mColorTones.get(position);

        holder.toneName.setText(colorTone.getName());
        holder.toneColor.setText(colorTone.getColor());
        holder.toneContainer.setBackgroundColor(
                Color.parseColor(colorTone.getColor())
        );
    }

    @Override
    public int getItemCount() {
        return mColorTones.size();
    }

    class ColorToneViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tone_container) View toneContainer;
        @Bind(R.id.tone_name) TextView toneName;
        @Bind(R.id.tone_color) TextView toneColor;

        public ColorToneViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
