package com.xav.country.info.model;

import java.util.List;

/**
 * Created by Skhan4 on 6/13/2017.
 */

public class CountryModel {

    private String name;
    private String numericCode;
    private String flag;
    private List<CurrencyModel> currencies;

    public String getName() {
        return name;
    }

    public String getNumericCode() {
        return numericCode;
    }

    public String getFlag() {
        return flag;
    }

    public List<CurrencyModel> getCurrencies() {
        return currencies;
    }

}
