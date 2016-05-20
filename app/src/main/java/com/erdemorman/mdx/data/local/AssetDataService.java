package com.erdemorman.mdx.data.local;

import android.content.Context;

import com.erdemorman.mdx.data.model.MaterialColor;
import com.erdemorman.mdx.data.model.MaterialIconGroup;
import com.erdemorman.mdx.injection.ApplicationContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

@Singleton
public class AssetDataService {
    private Context mContext;
    private Gson mGson;

    @Inject
    public AssetDataService(@ApplicationContext Context context, Gson gson) {
        mContext = context;
        mGson = gson;
    }

    public <T> T fromJsonToType(String fileName, Class<T> classOfT) {
        return fromJsonToType(fileName, (Type) classOfT);
    }

    public <T> T fromJsonToType(String fileName, Type typeOfT) {
        InputStreamReader reader = null;

        try {
            reader = new InputStreamReader(mContext.getAssets().open(fileName));

            return mGson.fromJson(reader, typeOfT);
        } catch (IOException e) {
            // TODO: log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO: log the exception
                }
            }
        }

        return null;
    }

    public <T> Observable<T> fromJsonToObservable(final String fileName, final Type typeOfT) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                T data = fromJsonToType(fileName, typeOfT);
                subscriber.onNext(data);
                subscriber.onCompleted();
            }
        });
    }
}
