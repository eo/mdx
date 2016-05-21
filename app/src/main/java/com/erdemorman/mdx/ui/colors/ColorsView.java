package com.erdemorman.mdx.ui.colors;

import com.erdemorman.mdx.data.model.MaterialColor;
import com.erdemorman.mdx.ui.base.BaseView;

import java.util.List;

public interface ColorsView extends BaseView {
    void showColors(List<MaterialColor> colors);

}
