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

    public static String getDdlType() {
        return getProperty("spring.jpa.hibernate.ddl-auto");
    }

    public static String getAuthenticationScheme() {
        return getProperty("server.auth.scheme");
    }



}
