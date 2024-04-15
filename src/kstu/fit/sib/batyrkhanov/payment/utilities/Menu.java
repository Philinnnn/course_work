package kstu.fit.sib.batyrkhanov.payment.utilities;

import kstu.fit.sib.batyrkhanov.payment.entities.Account;
import kstu.fit.sib.batyrkhanov.payment.entities.Card;
import kstu.fit.sib.batyrkhanov.payment.entities.Client;
import kstu.fit.sib.batyrkhanov.payment.entities.Payment;
import kstu.fit.sib.batyrkhanov.payment.utilities.actions.CardCloneCreator;
import kstu.fit.sib.batyrkhanov.payment.utilities.actions.CardSorter;

import java.io.File;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

public class Menu {
    public static void start() {

        Scanner scanner = new Scanner(System.in);
        Administrator admin = new Administrator();

        LinkedList<Client> clients = new LinkedList<>();
        LinkedList<Account> allAccounts = new LinkedList<>();
        LinkedList<Card> allCards = new LinkedList<>();
        LinkedList<Payment> allPayments = new LinkedList<>();

        String dataFolderPath = "src" + File.separator + "kstu" + File.separator + "fit" + File.separator + "sib" + File.separator + "batyrkhanov" + File.separator + "payment" + File.separator + "data" + File.separator;
        String stateFilePath = dataFolderPath + "state.dat";
        String clientTextFilePath = dataFolderPath + "clients.txt";
        String paymentTextFilePath = dataFolderPath + "payments.txt";

        boolean exit = false;
        while (!exit) {
            clearConsole();
            System.out.println("Меню:");
            System.out.println("1. Генерировать объекты");
            System.out.println("2. Вывести информацию");
            System.out.println("3. Осуществить перевод");
            System.out.println("4. Клонировать карту");
            System.out.println("5. Сортировать карты");
            System.out.println("6. Заблокировать счет");
            System.out.println("7. Разблокировать счет");
            System.out.println("8. Работа с бинарным файлом");
            System.out.println("9. Работа с текстовым файлом");
            System.out.println("10. Выйти из программы");
            System.out.print("Выберите действие: ");
            int choice;
            try {
                choice = scanner.nextInt();
            }
            catch (InputMismatchException e) {
                choice = 0;
            }
            switch (choice) {
                case 1: // Gen
                    boolean localExit = false;
                    int createChoice;
                    while (!localExit) {
                    clearConsole();
                    System.out.println("Выберите объекты:");
                    System.out.println("1. Клиенты");
                    System.out.println("2. Счета");
                    System.out.println("3. Карты");
                    System.out.println("4. Платежи");
                    System.out.println("5. Вернуться в главное меню");
                    try {
                        createChoice = scanner.nextInt();
                    }
                    catch (InputMismatchException e){
                        createChoice = 0;
                    }
                    switch (createChoice) {
                        case 1:
                            try {
                                System.out.print("Введите количество создаваемых клиентов: ");
                                int amountClients = scanner.nextInt();
                                admin.createClient(clients, amountClients);
                            } catch (Exception e) {
                                System.out.println("Некорректное значение");
                            }
                            break;
                        case 2:
                            admin.createAccount(clients, allAccounts);
                            break;
                        case 3:
                            admin.createCards(allAccounts, allCards);
                            break;
                        case 4:
                            try {
                                Generator.generatePayment(clients, allPayments, (int) (Math.random() * 50) + 1);
                            }
                            catch (IllegalArgumentException e)
                                {System.out.println("Карты не созданы. Генерация невозможна");}
                            break;
                        case 5:
                            localExit = true;
                            break;
                        default:
                            System.out.println("Некорректное значение. Выберите цифру из меню");
                            break;
                        }
                        if (!localExit) {
                            System.out.print("Нажмите Enter для продолжения...");
                            scanner.nextLine();
                            scanner.nextLine();
                        }
                    }
                    break;
                case 2: // Print
                    clearConsole();
                    System.out.println("Выберите объекты:");
                    System.out.println("1. Клиенты");
                    System.out.println("2. Счета");
                    System.out.println("3. Карты");
                    System.out.println("4. Платежи");
                    int reportChoice;
                    try {
                        reportChoice = scanner.nextInt();
                    }
                    catch (InputMismatchException e) {
                        reportChoice = 0;
                    }
                    switch (reportChoice) {
                        case 1 -> Reporter.print(clients);
                        case 2 -> Reporter.print(allAccounts);
                        case 3 -> Reporter.print(allCards);
                        case 4 -> Reporter.print(allPayments);
                        default -> System.out.println("Некорректное значение. Выберите цифру из меню");
                    }
                    break;
                case 3: // Clone
                    try {
                        System.out.println("Введите номер карты отправителя:");
                        long senderCardNumber = scanner.nextLong();
                        System.out.println("Введите номер карты получателя:");
                        long recipientCardNumber = scanner.nextLong();
                        System.out.println("Введите сумму для перевода:");
                        double amount = scanner.nextDouble();

                        Card senderCard = Card.findCardByNumber(senderCardNumber, allCards);
                        Card recipientCard = Card.findCardByNumber(recipientCardNumber, allCards);

                        if (senderCard != null && recipientCard != null)
                            senderCard.payTo(recipientCard, amount);
                        else
                            System.out.println("Карта отправителя или получателя не найдена.");

                    } catch (Exception e) {
                        System.err.println("Ошибка при выполнении перевода: " + e.getMessage());
                    }
                    break;
                case 4: // Pay
                    try {
                        CardCloneCreator cloner = new CardCloneCreator();
                        System.out.println("Введите номер карты:");
                        long testNumber = scanner.nextLong();
                        Card temp = Card.findCardByNumber(testNumber, allCards);
                        assert temp != null;
                        Card clone = cloner.createClone(temp);

                        Reporter.print(temp);
                        Reporter.print(clone);
                        Reporter.print(temp, clone);
                    } catch (Exception e) {
                        System.err.println("Карта не найдена");
                    }
                    break;
                case 5: // Sort
                    CardSorter sorter = new CardSorter();
                    try {
                        if (allCards.isEmpty()) {
                            throw new Exception("Список карт пуст.");
                        }
                        clearConsole();
                        System.out.println("Выберите действие:");
                        System.out.println("1. Сортировка по балансу");
                        System.out.println("2. Сортировка по типу карты");
                        System.out.println("3. Сортировка по номеру карты");
                        int sorterChoice = scanner.nextInt();
                        switch (sorterChoice) {
                            case 1 -> {
                                sorter.sortByBalance(allCards);
                                Reporter.print(allCards);
                            }
                            case 2 -> {
                                sorter.sortByType(allCards);
                                Reporter.print(allCards);
                            }
                            case 3 -> {
                                sorter.sortByNumber(allCards);
                                Reporter.print(allCards);
                            }
                            default -> throw new Exception("Некорректный ввод. Пожалуйста, введите 1 или 2.");
                        }
                    } catch (Exception e) {
                        System.err.println("Ошибка: " + e.getMessage());
                    }
                    break;
                case 6: // Block
                    try {
                        System.out.println("Введите UID счёта: ");
                        int blockUID = scanner.nextInt();
                        admin.blockAccount(Objects.requireNonNull(Account.getAccountByUID(blockUID, allAccounts)));
                        Reporter.print(Account.getAccountByUID(blockUID, allAccounts));
                    }
                    catch (Exception e) {
                        System.out.println("Счет не найден");
                    }
                    break;
                case 7: // Unblock
                    try {
                    System.out.println("Введите UID счёта: ");
                    int unblockUID = scanner.nextInt();
                    admin.unblockAccount(Objects.requireNonNull(Account.getAccountByUID(unblockUID, allAccounts)));
                    Reporter.print(Account.getAccountByUID(unblockUID, allAccounts));
                    }
                    catch (Exception e) {
                        System.out.println("Счет не найден");
                    }
                    break;
                case 8: // Bin
                    clearConsole();
                    System.out.println("Выберите действие:");
                    System.out.println("1. Сохранить файл");
                    System.out.println("2. Загрузить файл");
                    int binFileChoice = scanner.nextInt();
                    switch (binFileChoice) {
                        case 1 -> FileManager.saveStateToFile(clients, allAccounts, allCards, allPayments, "state.dat");
                        case 2 -> {
                            File loadFile = new File(stateFilePath);
                            if (loadFile.exists()) {
                                FileManager.loadStateFromFile(clients, allAccounts, allCards, allPayments, "state.dat");
                            } else {
                                System.out.println("Файл не существует.");
                            }
                        }
                    }
                    break;
                case 9: // Text
                    clearConsole();
                    System.out.println("Выберите действие:");
                    System.out.println("1. Сохранить файлы");
                    System.out.println("2. Прочитать файл клиентов");
                    System.out.println("3. Прочитать файл платежей");
                    int textFileChoice = scanner.nextInt();
                    switch (textFileChoice) {
                        case 1 -> {
                            FileManager.saveClientsToStringFile(clients, clientTextFilePath);
                            FileManager.savePaymentsToStringFile(allPayments, paymentTextFilePath);
                        }
                        case 2 -> {
                            File loadClientTextFile = new File(clientTextFilePath);
                            if (loadClientTextFile.exists()) {
                                FileManager.printClientsFromFile(clientTextFilePath);
                            } else {
                                System.out.println("Файл не существует.");
                            }
                        }
                        case 3 -> {
                            File loadPaymentTextFile = new File(paymentTextFilePath);
                            if (loadPaymentTextFile.exists()) {
                                FileManager.printPaymentsFromFile(paymentTextFilePath);
                            } else {
                                System.out.println("Файл не существует.");
                            }
                        }
                    }
                    break;
                case 10:
                    System.out.println("Программа завершена.");
                    exit = true;
                    break;
                default:
                    System.out.println("Некорректный выбор. Пожалуйста, выберите действие из меню.");
                    break;
                }
            if (!exit) {
                System.out.print("Нажмите Enter для продолжения...");
                scanner.nextLine();
                scanner.nextLine();
            }
        }
        scanner.close();
    }
    static void clearConsole() {
        for (int i = 0; i < 100; i++)
            System.out.println();
    }
}