package com.erdemorman.mdx.ui.icons;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erdemorman.mdx.R;
import com.erdemorman.mdx.data.model.MaterialIcon;
import com.erdemorman.mdx.data.model.MaterialIconGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class IconsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_GROUP_HEADER = 0;
    private static final int VIEW_TYPE_ICON = 1;

    private List<Object> mFlattennedList;

    @Inject
    public IconsAdapter() {
        mFlattennedList = new ArrayList<>();
    }

    public void setIconGroups(List<MaterialIconGroup> iconGroups) {
        flattenIconGroupList(iconGroups);
        notifyDataSetChanged();
    }

    private void flattenIconGroupList(List<MaterialIconGroup> iconGroups) {
        int flattenedListSize = calculateFlattenedListSize(iconGroups);
        mFlattennedList = new ArrayList<>(flattenedListSize);

        for (MaterialIconGroup iconGroup: iconGroups) {
            mFlattennedList.add(iconGroup);
            mFlattennedList.addAll(iconGroup.getIcons());
        }
    }

    private int calculateFlattenedListSize(List<MaterialIconGroup> iconGroups) {
        int flattenedListSize = iconGroups.size();
        for (MaterialIconGroup iconGroup: iconGroups) {
            flattenedListSize += iconGroup.getIconCount();
        }

        return flattenedListSize;
    }

    public boolean isGroupHeader(int position) {
        return (mFlattennedList.get(position) instanceof MaterialIconGroup);
    }

    @Override
    public int getItemViewType(int position) {
        return isGroupHeader(position) ? VIEW_TYPE_GROUP_HEADER : VIEW_TYPE_ICON;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        @LayoutRes int layoutRes = (viewType == VIEW_TYPE_ICON ?
                R.layout.fragment_icons_item_icon : R.layout.fragment_icons_item_group_header);
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutRes, parent, false);

        return (viewType == VIEW_TYPE_ICON ?
                new IconViewHolder(itemView) : new GroupHeaderViewHolder(itemView));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof IconViewHolder) {
            MaterialIcon icon = (MaterialIcon) mFlattennedList.get(position);

            IconViewHolder viewHolder = (IconViewHolder) holder;
            viewHolder.iconImage.setImageResource(icon.getDrawableId());
            viewHolder.iconName.setText(icon.getName());
        } else {
            MaterialIconGroup group = (MaterialIconGroup) mFlattennedList.get(position);

            GroupHeaderViewHolder viewHolder = (GroupHeaderViewHolder) holder;
            viewHolder.groupName.setText(group.getName());
            viewHolder.iconCount.setText(String.valueOf(group.getIconCount()));
        }
    }

    @Override
    public int getItemCount() {
        return mFlattennedList.size();
    }

    class IconViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icon_image) ImageView iconImage;
        @Bind(R.id.icon_name) TextView iconName;

        public IconViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class GroupHeaderViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.group_name) TextView groupName;
        @Bind(R.id.icon_count) TextView iconCount;

        public GroupHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
