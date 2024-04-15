package kstu.fit.sib.batyrkhanov.payment.utilities;

import java.util.LinkedList;
import java.util.List;

import kstu.fit.sib.batyrkhanov.payment.entities.Account;
import kstu.fit.sib.batyrkhanov.payment.entities.Card;

public final class Reporter {

////////////////////////////////
    // Единичный вывод
////////////////////////////////

    public static void print(Account account) {
        System.out.println(account);
    }
    public static void print(Card card) {
        System.out.println(card);
    }
    public static void print(Card card, Card clone) {
        System.out.println("Выполнено глубокое клонирование: " + boolToString(!card.equals(clone)));
    }
    public static String boolToString(boolean value) {
        return value ? "Да" : "Нет";
    }

////////////////////////////////
    // Массовый вывод
////////////////////////////////

    public static void print(LinkedList <?> list) {
        System.out.println();
        for (Object obj : list)
            System.out.println(obj);
    }
}
