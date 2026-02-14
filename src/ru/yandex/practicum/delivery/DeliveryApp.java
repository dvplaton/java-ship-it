package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();
    private static List<Trackable> trackableParcels = new ArrayList<>();

    private static final ParcelBox<StandardParcel> standardBox = new ParcelBox<>(50);
    private static final ParcelBox<FragileParcel> fragileBox = new ParcelBox<>(30);
    private static final ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>(40);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    reportTrackingStatuses();
                    break;
                case 5:
                    showBoxContents();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Обновить статус (трекинг)");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        System.out.println("Выберите тип посылки: 1 — стандартная, 2 — хрупкая, 3 — скоропортящаяся");
        int type = Integer.parseInt(scanner.nextLine());

        System.out.println("Введите описание:");
        String description = scanner.nextLine();

        System.out.println("Введите вес:");
        int weight = Integer.parseInt(scanner.nextLine());

        System.out.println("Введите адрес доставки:");
        String address = scanner.nextLine();

        System.out.println("Введите день отправки (число):");
        int sendDay = Integer.parseInt(scanner.nextLine());

        if (type == 1) {
            StandardParcel parcel = new StandardParcel(description, weight, address, sendDay);
            allParcels.add(parcel);
            standardBox.addParcel(parcel);
        } else if (type == 2) {
            FragileParcel parcel = new FragileParcel(description, weight, address, sendDay);
            allParcels.add(parcel);
            fragileBox.addParcel(parcel);
            trackableParcels.add(parcel);
        } else if (type == 3) {
            System.out.println("Введите срок годности (в днях):");
            int ttl = Integer.parseInt(scanner.nextLine());

            PerishableParcel parcel = new PerishableParcel(description, weight, address, sendDay, ttl);
            allParcels.add(parcel);
            perishableBox.addParcel(parcel);
        } else {
            System.out.println("Неверный тип посылки.");
            return;
        }

        System.out.println("Посылка добавлена.");
    }

    private static void sendParcels() {
        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            parcel.deliver();
        }
    }

    private static void calculateCosts() {
        int total = 0;
        for (Parcel parcel : allParcels) {
            total += parcel.calculateDeliveryCost();
        }
        System.out.println("Общая стоимость доставки: " + total);
    }

    private static void reportTrackingStatuses() {
        if (trackableParcels.isEmpty()) {
            System.out.println("Нет отправлений, поддерживающих трекинг.");
            return;
        }

        System.out.println("Введите новое местоположение:");
        String newLocation = scanner.nextLine();

        for (Trackable trackable : trackableParcels) {
            trackable.reportStatus(newLocation);
        }
    }

    private static void showBoxContents() {
        System.out.println("Выберите коробку: 1 — стандартная, 2 — хрупкая, 3 — скоропортящаяся");
        int boxType = Integer.parseInt(scanner.nextLine());

        if (boxType == 1) {
            printParcels(standardBox.getAllParcels());
        } else if (boxType == 2) {
            printParcels(fragileBox.getAllParcels());
        } else if (boxType == 3) {
            printParcels(perishableBox.getAllParcels());
        } else {
            System.out.println("Неверный выбор коробки.");
        }
    }

    private static void printParcels(List<? extends Parcel> parcels) {
        if (parcels.isEmpty()) {
            System.out.println("Коробка пуста.");
            return;
        }
        for (Parcel p : parcels) {
            System.out.println("Посылка <<" + p.getDescription() + ">>, вес: " + p.getWeight());
        }
    }

}

