package com.streaming.properties;

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

    public static String getOTPPort() {
        return getProperty("config.otp.port");
    }

    public static String getOTPGenerateUrl() {
        return getProperty("config.otp.generate");
    }

    public static String getOTPVerifyUrl() {
        return getProperty("config.otp.verify");
    }

    public static String getOTPPublisherId() {
        return getProperty("config.otp.publisher-id");
    }

    public static String getOTPTenantId() {
        return getProperty("config.otp.tenant-id");
    }
}
