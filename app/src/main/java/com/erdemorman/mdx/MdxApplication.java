package com.erdemorman.mdx;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.erdemorman.mdx.injection.component.ApplicationComponent;
import com.erdemorman.mdx.injection.component.DaggerApplicationComponent;
import com.erdemorman.mdx.injection.module.ApplicationModule;


public class MdxApplication extends Application {
    ApplicationComponent mApplicationComponent;

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public static MdxApplication get(Context context) {
        return (MdxApplication) context.getApplicationContext();
    }

    public ApplicationComponent getApplicationComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }

        return mApplicationComponent;
    }
}
