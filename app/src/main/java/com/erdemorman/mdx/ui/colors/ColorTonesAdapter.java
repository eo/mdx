package com.erdemorman.mdx.ui.colors;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erdemorman.mdx.R;
import com.erdemorman.mdx.data.model.MaterialColor;
import com.erdemorman.mdx.data.model.MaterialColorTone;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ColorTonesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private final String mColorName;
    private final List<MaterialColorTone> mColorTones;

    public ColorTonesAdapter(Context context, MaterialColor materialColor) {
        mContext = context;
        mColorName = materialColor.getName();
        mColorTones = materialColor.getTones();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);

        return (viewType == ColorNameViewHolder.TYPE ?
                new ColorNameViewHolder(itemView) : new ColorToneViewHolder(itemView));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ColorNameViewHolder) {
            ColorNameViewHolder viewHolder = (ColorNameViewHolder) holder;
            viewHolder.colorName.setText(mColorName);
        } else {
            ColorToneViewHolder viewHolder = (ColorToneViewHolder) holder;
            MaterialColorTone colorTone = getColorToneAtAdapterPosition(position);
            int textColor = ContextCompat.getColor(mContext,
                    colorTone.isWhiteText() ? R.color.tone_white_text : R.color.tone_black_text);

            viewHolder.toneName.setText(colorTone.getName());
            viewHolder.toneColor.setText(colorTone.getColor());

            viewHolder.toneName.setTextColor(textColor);
            viewHolder.toneColor.setTextColor(textColor);

            viewHolder.toneContainer.setBackgroundColor(
                    Color.parseColor(colorTone.getColor())
            );
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ColorNameViewHolder.TYPE : ColorToneViewHolder.TYPE;
    }

    @Override
    public int getItemCount() {
        return mColorTones.size() + 1;
    }

    private MaterialColorTone getColorToneAtAdapterPosition(int adapterPosition) {
        return mColorTones.get(adapterPosition - 1);
    }

    class ColorNameViewHolder extends RecyclerView.ViewHolder {
        static final int TYPE = R.layout.fragment_color_tones_item_color_name;

        @Bind(R.id.color_name) TextView colorName;

        public ColorNameViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ColorToneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        static final int TYPE = R.layout.fragment_color_tones_item_color_tone;

        @Bind(R.id.tone_container) View toneContainer;
        @Bind(R.id.tone_name) TextView toneName;
        @Bind(R.id.tone_color) TextView toneColor;

        public ColorToneViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            MaterialColorTone colorTone = getColorToneAtAdapterPosition(getAdapterPosition());

            copyColorToClipboard(colorTone);
            showColorCopiedMessage(view, colorTone);
        }

        private void copyColorToClipboard(MaterialColorTone colorTone) {
            ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(
                    Context.CLIPBOARD_SERVICE);
            String clipDataLabel = mContext.getString(R.string.tone_copied_clipboard_label);

            ClipData clip = ClipData.newPlainText(clipDataLabel, colorTone.getColor());
            clipboard.setPrimaryClip(clip);
        }

        private void showColorCopiedMessage(View view, MaterialColorTone colorTone) {
            String messageText = mContext.getString(R.string.tone_copied_to_clipboard,
                    colorTone.getColor());
            Snackbar.make(view, messageText, Snackbar.LENGTH_SHORT).show();
        }
    }
}
