package com.xav.country.info.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Skhan4 on 6/13/2017.
 */

public class RetrofitUtil {

    public static <T> T createRetrofitService(final Class<T> clazz) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUtil.URL_API_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        T service = retrofit.create(clazz);
        return service;
    }
}
