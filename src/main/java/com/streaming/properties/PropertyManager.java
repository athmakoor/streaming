package com.streaming.properties;

import com.streaming.constant.Provider;

import java.util.Properties;

/**
 * @author customfurnish.com
 */
public final class PropertyManager {
    private static Properties properties = null;

    /**
     * Its a private constructor.
     */
    private PropertyManager() {

    }

    private static String getProperty(final String name) {
        try {
            if (properties == null) {
                properties = new PropertyLoader().getProperties();
            }
            if (properties.containsKey(name)) {
                return properties.getProperty(name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getOTPIP() {
        return getProperty("config.otp.ip");
    }

    public static String getMode() {
        return getProperty("application.mode");
    }

    public static String getOTPGenerateUrl(String provider) {
        if (provider.equals(Provider.ZAIN_KUWAIT)) {
            return getProperty("config.otp.generate.zain");
        }
        return getProperty("config.otp.generate.ooredoo");
    }

    public static String getOTPVerifyUrl(String provider) {
        if (provider.equals(Provider.ZAIN_KUWAIT)) {
            return getProperty("config.otp.verify.zain");
        }
        return getProperty("config.otp.verify.ooredoo");
    }

    public static String getPropValue(final String key) {
        return getProperty(key);
    }
}
