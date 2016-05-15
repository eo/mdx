package com.erdemorman.mdx.injection.module;

import android.app.Application;
import android.content.Context;

import com.erdemorman.mdx.data.model.MaterialIcon;
import com.erdemorman.mdx.data.remote.MdxService;
import com.erdemorman.mdx.injection.ApplicationContext;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    private Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    MdxService provideMdxService() {
        return MdxService.Creator.newMdxService();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(MaterialIcon.class,
                        new MaterialIcon.GsonDeserializer(mApplication))
                .create();
    }
}
