package com.erdemorman.mdx.ui.icons;

import com.erdemorman.mdx.data.model.MaterialIconGroup;
import com.erdemorman.mdx.ui.base.BaseView;

import java.util.List;

public interface IconsView extends BaseView {
    void showIconGroups(List<MaterialIconGroup> iconGroups);
}
