package com.streaming.aggregator.timwe.bean;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Hari on 1/29/2021.
 */
public class CgUrlRequest implements Serializable{
    @NotNull
    private String msisdn;
    private String packId;
    private String countryId = "";
    @NotNull
    private String locale;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(final String msisdn) {
        this.msisdn = msisdn;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(final String locale) {
        this.locale = locale;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(final String countryId) {
        this.countryId = countryId;
    }

    public String getPackId() {
        return packId;
    }

    public void setPackId(String packId) {
        this.packId = packId;
    }
}
