package infrastructure.service;

import application.service.LogService;
import domain.value.LogLevel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Implementasi LogService untuk logging ke console/CLI.
 * 
 * <p>Class ini mengimplementasikan LogService dengan output ke console
 * dalam format yang terstruktur dengan timestamp dan level logging.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public class CliLogService implements LogService {
    
    /**
     * Menampilkan pesan log ke console dengan format yang terstruktur.
     * 
     * <p>Method ini akan memecah pesan multi-baris dan menampilkan setiap baris
     * dengan format: &lt;BANK-APP&gt;[timestamp][level]: message</p>
     * 
     * @param logLevel level logging untuk pesan
     * @param message pesan yang akan di-log
     */
    public void log(LogLevel logLevel, String message) {
        String[] messages = splitMessage(message);
        for (String chunk : messages) {
            System.out.println("<BANK-APP>[" + getTimestamp() + "][" + logLevel.getDisplayName() + "]: " + chunk);
        }
    }

    /**
     * Memecah pesan multi-baris menjadi array string.
     * 
     * @param message pesan yang akan dipecah
     * @return array string berisi setiap baris pesan
     */
    protected String[] splitMessage(String message) {
        return message.split("\n");
    }

    /**
     * Mendapatkan timestamp saat ini dalam format yang disederhanakan.
     * Format: yyyy-MM-ddTHH:mm:ss (tanpa milidetik dan timezone)
     * 
     * @return timestamp saat ini sebagai string dalam format 2025-09-20T11:53:24
     */
    protected String getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return LocalDateTime.now(ZoneId.systemDefault()).format(formatter);
    }
}
