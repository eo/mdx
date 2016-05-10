package com.erdemorman.mdx.util;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import rx.Observable;
import rx.Subscriber;

public class AssetUtil {
    public static <T> T fromJson(Context context, String fileName, Class<T> classOfT) {
        return fromJson(context, fileName, (Type) classOfT);
    }

    public static <T> T fromJson(Context context, String fileName, Type typeOfT) {
        InputStreamReader reader = null;

        try {
            Gson gson = new Gson();
            reader = new InputStreamReader(context.getAssets().open(fileName));

            return gson.fromJson(reader, typeOfT);
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

    public static <T> Observable<T> fromJsonToObservable(final Context context,
                                                         final String fileName,
                                                         final Type typeOfT) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        T data = fromJson(context, fileName, typeOfT);

                        subscriber.onNext(data);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
