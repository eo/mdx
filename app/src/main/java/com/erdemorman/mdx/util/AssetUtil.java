package com.erdemorman.mdx.util;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import rx.Observable;

public class AssetUtil {
    public static <T> T fromJsonToType(Context context, String fileName, Class<T> classOfT) {
        return fromJsonToType(context, fileName, (Type) classOfT);
    }

    public static <T> T fromJsonToType(Context context, String fileName, Type typeOfT) {
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

    public static <T> Observable<T> fromJsonToObservable(Context context, String fileName,
                                                         Type typeOfT) {
        T data = fromJsonToType(context, fileName, typeOfT);

        return Observable.just(data);
    }
}
