package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class ConfigFileManager {

    private static final String CONFIG_FILE = System.getProperty("user.home") +
            File.separator +
            ".robot_game_config.properties";


    public Properties load() {
        Properties properties = new Properties();
        File configFile = new File(CONFIG_FILE);

        if (!configFile.exists()) {
            return properties;
        }

        try (FileInputStream in = new FileInputStream(configFile)) {
            properties.load(in);
        } catch (IOException e) {
            System.err.println("Ошибка загрузки конфигурации: " + e.getMessage());
        }

        return properties;
    }


    public void save(Properties properties) {
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            properties.store(out, "Robot Game Configuration");
        } catch (IOException e) {
            System.err.println("Ошибка сохранения конфигурации: " + e.getMessage());
        }
    }
}