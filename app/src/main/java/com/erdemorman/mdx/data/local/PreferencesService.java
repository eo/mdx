package com.erdemorman.mdx.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.erdemorman.mdx.injection.ApplicationContext;
import com.erdemorman.mdx.util.ColorFormatUtil.ColorFormat;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

@Singleton
public class PreferencesService {
    public static final String PREFS_FILE_NAME = "mdx.prefs";

    private final SharedPreferences mPrefs;
    private final Observable<String> mChangedKeys;

    private static final String KEY_COLOR_FORMAT = "KEY_COLOR_FORMAT";
    private static final String DEFAULT_COLOR_FORMAT = ColorFormat.HEX.name();

    @Inject
    public PreferencesService(@ApplicationContext Context context) {
        mPrefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE);

        mChangedKeys = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                final OnSharedPreferenceChangeListener listener = new OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                        subscriber.onNext(key);
                    }
                };

                Subscription subscription = Subscriptions.create(new Action0() {
                    @Override public void call() {
                        mPrefs.unregisterOnSharedPreferenceChangeListener(listener);
                    }
                });
                subscriber.add(subscription);

                mPrefs.registerOnSharedPreferenceChangeListener(listener);
            }
        }).share();
    }

    private Observable<String> observeKey(final String key) {
        return mChangedKeys
                .filter(new Func1<String, Boolean>() {
                    @Override public Boolean call(String value) {
                        return key.equals(value);
                    }
                });
    }

    public void clear() {
        mPrefs.edit().clear().apply();
    }

    public ColorFormat getColorFormat() {
        String formatName = mPrefs.getString(KEY_COLOR_FORMAT, DEFAULT_COLOR_FORMAT);
        return ColorFormat.valueOf(formatName);
    }

    public void setColorFormat(ColorFormat format) {
        mPrefs.edit().putString(KEY_COLOR_FORMAT, format.name()).apply();
    }

    public Observable<ColorFormat> observeColorFormat() {
        return observeKey(KEY_COLOR_FORMAT)
                .map(new Func1<String, ColorFormat>() {
                    @Override
                    public ColorFormat call(String s) {
                        return getColorFormat();
                    }
                });
    }
}
