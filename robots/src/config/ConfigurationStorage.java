package config;

import java.io.*;
import java.util.Properties;

public class ConfigurationStorage {
    private static final String CONFIG_FILE = System.getProperty("user.home") +
            File.separator +
            ".robot_game_config.properties";

    private Properties properties;

    public ConfigurationStorage() {
        this.properties = new Properties();
    }

    public boolean load() {
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            return false;
        }

        try (FileInputStream in = new FileInputStream(configFile)) {
            properties.load(in);
            return true;
        } catch (IOException e) {
            System.err.println("Ошибка загрузки конфигурации: " + e.getMessage());
            return false;
        }
    }

    public void save() {
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            properties.store(out, "Robot Game Configuration");
        } catch (IOException e) {
            System.err.println("Ошибка сохранения конфигурации: " + e.getMessage());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public void setProperty(String key, String value) {
        if (value != null) {
            properties.setProperty(key, value);
        } else {
            properties.remove(key);
        }
    }

    public boolean hasProperties() {
        return !properties.isEmpty();
    }
}