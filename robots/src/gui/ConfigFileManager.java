package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Отвечает ТОЛЬКО за чтение и запись файла конфигурации.
 * Ничего не знает об окнах, только работа с Properties и файлом.
 */
public class ConfigFileManager {

    private static final String CONFIG_FILE = System.getProperty("user.home") +
            File.separator +
            ".robot_game_config.properties";

    /**
     * Загружает настройки из файла
     * @return Properties с загруженными настройками, или пустые Properties если файла нет
     */
    public Properties loadFromFile() {
        Properties properties = new Properties();
        File configFile = new File(CONFIG_FILE);

        if (!configFile.exists()) {
            return properties;  // Файла нет - возвращаем пустые настройки
        }

        try (FileInputStream in = new FileInputStream(configFile)) {
            properties.load(in);
        } catch (IOException e) {
            System.err.println("Ошибка загрузки конфигурации: " + e.getMessage());
        }

        return properties;
    }

    /**
     * Сохраняет настройки в файл
     * @param properties настройки для сохранения
     */
    public void saveToFile(Properties properties) {
        try (FileOutputStream out = new FileOutputStream(CONFIG_FILE)) {
            properties.store(out, "Robot Game Configuration");
        } catch (IOException e) {
            System.err.println("Ошибка сохранения конфигурации: " + e.getMessage());
        }
    }
}