package kstu.fit.sib.batyrkhanov.payment.utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import kstu.fit.sib.batyrkhanov.payment.entities.Account;
import kstu.fit.sib.batyrkhanov.payment.entities.Card;
import kstu.fit.sib.batyrkhanov.payment.entities.Client;
import kstu.fit.sib.batyrkhanov.payment.entities.Payment;

@SuppressWarnings("unchecked")

public final class FileManager {

    public static void saveStateToFile(LinkedList<Client> clients, LinkedList<Account> allAccounts, LinkedList<Card> allCards, LinkedList<Payment> payments, String filename) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            outputStream.writeObject(clients);
            outputStream.writeObject(allAccounts);
            outputStream.writeObject(allCards);
            outputStream.writeObject(payments);
            System.out.println("Состояние сохранено в файл " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении состояния: " + e.getMessage());
        }
    }

    public static void loadStateFromFile(LinkedList<Client> clients, LinkedList<Account> allAccounts, LinkedList<Card> allCards, LinkedList<Payment> payments, String filename) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            clients.clear();
            allAccounts.clear();
            allCards.clear();
            clients.addAll((LinkedList<Client>) inputStream.readObject());
            allAccounts.addAll((LinkedList<Account>) inputStream.readObject());
            allCards.addAll((LinkedList<Card>) inputStream.readObject());
            payments.addAll((LinkedList<Payment>) inputStream.readObject());
            System.out.println("Состояние загружено из файла " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при загрузке состояния: " + e.getMessage());
        }
    }

    public static void saveClientsToStringFile(List<Client> clients, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Client client : clients)
                writer.println(client.toString());
            System.out.println("Клиенты сохранены в файл " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении клиентов: " + e.getMessage());
        }
    }
    public static void savePaymentsToStringFile(List<Payment> payments, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Payment payment : payments)
                writer.println(payment.toString());
            System.out.println("Платежи сохранены в файл " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении платежей: " + e.getMessage());
        }
    }
    public static void printClientsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            System.out.println("Информация о клиентах из файла " + filename + ":");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении информации о клиентах из файла: " + e.getMessage());
        }
    }
    public static void printPaymentsFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            System.out.println("Информация о платежах из файла " + filename + ":");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении информации о платежах из файла: " + e.getMessage());
        }
    }
}
