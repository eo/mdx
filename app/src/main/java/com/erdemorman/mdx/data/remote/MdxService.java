package com.erdemorman.mdx.data.remote;

import com.erdemorman.mdx.data.model.MaterialColor;
import com.erdemorman.mdx.data.model.MaterialIconGroup;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

public interface MdxService {
    String ENDPOINT = "https://erdemorman.github.io/mdx/api/v1/";

    @GET("material-icon-groups.json")
    Observable<List<MaterialIconGroup>> getMaterialIconGroups();

    @GET("material-colors.json")
    Observable<List<MaterialColor>> getMaterialColors();

    class Creator {
        public static MdxService newMdxService() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(MdxService.class);
        }
    }
}
