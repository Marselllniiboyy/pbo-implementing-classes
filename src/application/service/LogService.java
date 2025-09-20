package application.service;

import domain.value.LogLevel;

/**
 * Interface service untuk logging dalam sistem perbankan.
 * 
 * <p>Interface ini mendefinisikan operasi logging dengan berbagai level
 * (INFO, WARNING, ERROR, SYSTEM) untuk pelacakan dan debugging aplikasi.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public interface LogService {
    
    /**
     * Menampilkan pesan log dengan level tertentu.
     * 
     * @param logLevel level logging (INFO, WARNING, ERROR, SYSTEM)
     * @param message pesan yang akan di-log
     */
    void log(LogLevel logLevel, String message);

    /**
     * Menampilkan pesan log dengan level INFO.
     * 
     * @param message pesan informasi yang akan di-log
     */
    default void info(String message) {
        log(LogLevel.INFO, message);
    }

    /**
     * Menampilkan pesan log dengan level WARNING.
     * 
     * @param message pesan peringatan yang akan di-log
     */
    default void warning(String message) {
        log(LogLevel.WARNING, message);
    }

    /**
     * Menampilkan pesan log dengan level ERROR.
     * 
     * @param message pesan error yang akan di-log
     */
    default void error(String message) {
        log(LogLevel.ERROR, message);
    }

    /**
     * Menampilkan pesan log dengan level SYSTEM.
     * 
     * @param message pesan sistem yang akan di-log
     */
    default void system(String message) {
        log(LogLevel.SYSTEM, message);
    }
}
