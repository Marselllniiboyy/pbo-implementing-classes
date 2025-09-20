package domain.value;

/**
 * Enum yang merepresentasikan berbagai level logging dalam sistem perbankan.
 * 
 * <p>Setiap level memiliki prioritas dan tujuan yang berbeda dalam
 * pelacakan dan debugging aplikasi.</p>
 * 
 * @author Gede Dhanu Purnayasa
 * @author Made Marsel Biliana Wijaya
 * @since 1.0
 */
public enum LogLevel {
    /**
     * Level informasi - untuk pesan informatif tentang operasi normal
     */
    INFO("INFO"),
    
    /**
     * Level peringatan - untuk situasi yang tidak normal tetapi tidak kritis
     */
    WARNING("WARN"),
    
    /**
     * Level error - untuk kesalahan yang memerlukan perhatian
     */
    ERROR("ERROR"),
    
    /**
     * Level sistem - untuk pesan internal sistem dan debugging
     */
    SYSTEM("SYST");

    private final String displayName;

    /**
     * Konstruktor untuk LogLevel.
     * 
     * @param displayName nama yang ditampilkan untuk level logging ini
     */
    LogLevel(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Mengembalikan nama yang ditampilkan untuk level logging ini.
     * 
     * @return nama yang ditampilkan untuk level logging
     */
    public String getDisplayName() {
        return displayName;
    }
}