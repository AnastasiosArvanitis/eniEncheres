package fr.eni.eniEncheres.dal.jdbcTools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings {

    private static Properties properties;
    private static FileInputStream input;

    static {
        try {
            properties = new Properties();

            InputStream resourceAsStream = Settings.class.getClassLoader().getResourceAsStream("settings.properties");
            if (resourceAsStream != null) {
                properties.load(resourceAsStream);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public static String getProperty(String key) {
        String parametre = properties.getProperty(key, null);
        return parametre;
    }

}
