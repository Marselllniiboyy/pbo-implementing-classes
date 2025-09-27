// PBO[package]: Menentukan paket domain.exception agar semua exception spesifik sistem perbankan tersentralisasi.
package domain.exception;

// PBO[import]: Memanfaatkan BigDecimal untuk validasi nilai numerik limit/harga
// dan Map untuk menyimpan context informasi tambahan pada error.
import java.math.BigDecimal;
import java.util.Map;

/**
 * PBO[class]: Exception untuk masalah terkait jenis kartu.
 *
 * <p>Kelas induk ini menjadi dasar untuk berbagai jenis error yang
 * berhubungan dengan tipe kartu (CardTypeEntity) seperti tidak ditemukan,
 * data tidak valid, atau nilai limit negatif. Mewarisi {@link BankingException}
 * sehingga membawa errorCode, pesan untuk user, pesan teknis, timestamp, dan context map.</p>
 *
 * <p>Gunakan subclass statis di dalamnya sesuai kebutuhan:</p>
 * <ul>
 *   <li>{@code CardTypeNotFound} – jika jenis kartu tidak ditemukan.</li>
 *   <li>{@code InvalidCardTypeData} – jika data field tertentu tidak valid.</li>
 *   <li>{@code NegativeLimit} – jika limit transaksi bernilai negatif.</li>
 *   <li>{@code InvalidMonthlyPrice} – jika harga bulanan tidak valid.</li>
 * </ul>
 *
 * @see domain.entity.CardTypeEntity
 * @see BankingException
 *
 * @since 1.0
 * @autor Gede Dhanu Purnayasa
 * @autor Made Marsel Biliana Wijaya
 */
public class CardTypeException extends BankingException {
    /**
     * PBO[subclass]: Dilempar ketika jenis kartu tidak ditemukan berdasarkan ID atau nama.
     */
    public static class CardTypeNotFound extends CardTypeException {
        public CardTypeNotFound(int cardTypeId) {
            super("CARD_TYPE_NOT_FOUND", 
                "Jenis kartu tidak ditemukan", 
                String.format("Card type with ID %d not found", cardTypeId),
                Map.of("cardTypeId", cardTypeId));
        }
        
        public CardTypeNotFound(String cardTypeName) {
            super("CARD_TYPE_NOT_FOUND", 
                "Jenis kartu tidak ditemukan", 
                String.format("Card type with name %s not found", cardTypeName),
                Map.of("cardTypeName", cardTypeName));
        }
    }
    /**
     * PBO[subclass]: Dilempar ketika data field tipe kartu tidak valid.
     */
    public static class InvalidCardTypeData extends CardTypeException {
        public InvalidCardTypeData(String field, Object value, String reason) {
            super("INVALID_CARD_TYPE_DATA", 
                String.format("Data %s tidak valid", field), 
                String.format("Invalid card type data: %s = %s, reason: %s", field, value, reason),
                Map.of(
                    "field", field,
                    "value", value,
                    "reason", reason
                ));
        }
    }
    /**
     * PBO[subclass]: Dilempar ketika limit transaksi bernilai negatif.
     */
    public static class NegativeLimit extends CardTypeException {
        public NegativeLimit(String limitType, BigDecimal value) {
            super("NEGATIVE_LIMIT", 
                String.format("Limit %s tidak boleh negatif", limitType), 
                String.format("Negative limit not allowed: %s = %s", limitType, value),
                Map.of(
                    "limitType", limitType,
                    "value", value
                ));
        }
    }
    /**
     * PBO[subclass]: Dilempar ketika harga bulanan tidak valid.
     */
    public static class InvalidMonthlyPrice extends CardTypeException {
        public InvalidMonthlyPrice(BigDecimal monthlyPrice) {
            super("INVALID_MONTHLY_PRICE", 
                "Harga bulanan tidak valid", 
                String.format("Invalid monthly price: %s", monthlyPrice),
                Map.of("monthlyPrice", monthlyPrice));
        }
    }
    /**
     * Konstruktor privat hanya untuk dipanggil oleh subclass.
     */
    private CardTypeException(String errorCode, String userMessage, String technicalMessage, Map<String, Object> context) {
        super(errorCode, userMessage, technicalMessage, context);
    }
}
