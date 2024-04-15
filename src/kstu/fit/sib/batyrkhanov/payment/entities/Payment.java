package kstu.fit.sib.batyrkhanov.payment.entities;

import java.io.Serial;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @param card        Карта платежа
 * @param serviceName Название услуги
 * @param sum         Сумма платежа
 * @param date        Дата платежа
 */
public record Payment(Card card, String serviceName, double sum, Date date) implements Serializable {
    @Serial
    private static final long serialVersionUID = 512428L;

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return String.format("Платеж {\n\tкарта= %s\n\tуслуга= '%s'\n\tсумма= %.2f\n\tдата= %s\n}", card.getCardNumber(), serviceName, sum, dateFormat.format(date));
    }
}