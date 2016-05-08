package com.erdemorman.mdx.injection.component;

import android.app.Application;
import android.content.Context;

import com.erdemorman.mdx.data.remote.MdxService;
import com.erdemorman.mdx.injection.ApplicationContext;
import com.erdemorman.mdx.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ApplicationContext Context context();
    Application application();
    MdxService mdxService();
}
