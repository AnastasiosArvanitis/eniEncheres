package fr.eni.eniEncheres.dal.jdbcTools;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Settings {

    private static Properties properties;
    private static FileInputStream input;

       /* FileInputStream input = new FileInputStream(filename);
        try{

            properties.load(input);
            return properties;
        }*/

    static {
        try {
            properties = new Properties();
            input = new FileInputStream("src/main/resources/settings.properties");
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getProperty(String key) {
        String parametre = properties.getProperty(key, null);
        return parametre;
    }

}
