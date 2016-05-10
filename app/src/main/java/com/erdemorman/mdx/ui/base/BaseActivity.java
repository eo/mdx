package com.erdemorman.mdx.ui.base;

import android.support.v7.app.AppCompatActivity;

import com.erdemorman.mdx.MdxApplication;
import com.erdemorman.mdx.injection.component.ActivityComponent;
import com.erdemorman.mdx.injection.component.DaggerActivityComponent;
import com.erdemorman.mdx.injection.module.ActivityModule;

public class BaseActivity extends AppCompatActivity {
    private ActivityComponent mActivityComponent;

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(MdxApplication.get(this).getApplicationComponent())
                    .build();
        }

        return mActivityComponent;
    }
}
