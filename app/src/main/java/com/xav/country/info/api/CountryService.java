package com.xav.country.info.api;

import com.xav.country.info.model.CountryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Skhan4 on 6/13/2017.
 */

public interface CountryService {

    @GET("v2/all?fields=name;numericCode;currencies;flag")
    Call<List<CountryModel>> getCountries();
}
