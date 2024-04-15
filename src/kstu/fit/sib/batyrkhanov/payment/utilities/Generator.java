package kstu.fit.sib.batyrkhanov.payment.utilities;

import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import kstu.fit.sib.batyrkhanov.payment.entities.Account;
import kstu.fit.sib.batyrkhanov.payment.entities.Card;
import kstu.fit.sib.batyrkhanov.payment.entities.Client;
import kstu.fit.sib.batyrkhanov.payment.entities.Payment;
import kstu.fit.sib.batyrkhanov.payment.enums.CardTypes;
import kstu.fit.sib.batyrkhanov.payment.enums.Services;

public final class Generator {
    
    public static double generateBalance() {
        return (int) (Math.random() * 89999+10000);
    }
    public static CardTypes generateCardType() {
        Random random = new Random();
        CardTypes[] cardTypes = CardTypes.values();
        return cardTypes[random.nextInt(cardTypes.length)];
    }
    public static boolean generateBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }
    public static long generateCardNumber() {
        return (long) (Math.random() * 9_000_000_000_000_000L) + 1_000_000_000_000_000L;
    }
    public static short generatePIN() {
        return (short) (Math.random() * 9000 + 1000);
    }
    public static String generateClientName() {
        int index = new Random().nextInt(NAMES.length);
        return NAMES[index];
    }
    public static boolean generatePayment(LinkedList<Client> clients, LinkedList<Payment> allPayments) {
        Random random = new Random();
        Date date = new Date();

        Client randomClient = clients.get(random.nextInt(clients.size()));
        Account randomAccount = randomClient.getAccounts().get(random.nextInt(randomClient.getAccounts().size()));
        Card randomCard = randomAccount.getCards().get(random.nextInt(randomAccount.getCards().size()));
        String serviceName = Services.values()[random.nextInt(Services.values().length)].toString();
        double amount = Math.random() * 9999 + 1000;

        if (randomCard.withdraw(amount)) {
            allPayments.add(new Payment(randomCard, serviceName, amount, date));
        return true;
        }
        return false;
    }
    public static void generatePayment(LinkedList<Client> clients, LinkedList<Payment> allPayments, int amount) {
        int successCount = 0, errorCount = 0;
        for (int i = 0; i < amount; i++) {
            if (generatePayment(clients, allPayments)) successCount++;
            else errorCount++;
        }
        System.out.println("Платежей проведено успешно: " + successCount);
        System.out.println("Платежей не удалось: " + errorCount);
    }
    private static final String[] NAMES = {
        "Алексей", "Борис", "Владимир", "Григорий", "Дмитрий", "Евгений", "Жанна", "Зоя", "Иван", "Юлия",
        "Кирилл", "Леонид", "Михаил", "Наталья", "Олег", "Павел", "Роман", "Сергей", "Татьяна", "Ульяна",
        "Федор", "Алия", "Арина", "Эдуард", "Юрий", "Яна", "Анна", "Белла", "Вера",
        "Галина", "Дарья", "Екатерина", "Яков", "Зинаида", "Ирина", "Юлиан", "Ксения", "Людмила", "Мария",
        "Надежда", "Оксана", "Полина", "Раиса", "Светлана", "Тамара", "Устин", "Фаина", "Кристина", "Антон",
        "Чарльз", "Эльвира", "Карина", "Ярослав", "Артем", "Ян", "Валентин", "Георгий", "Даниил", "Елена"        
    };
}
