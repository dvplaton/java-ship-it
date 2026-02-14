package ru.yandex.practicum.delivery;

import java.util.ArrayList;

public class ParcelBox<T extends Parcel> {

    private final int maxWeight;
    private final ArrayList<T> parcels = new ArrayList<>();

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void addParcel(T parcel) {
        int currentWeight = 0;
        for (int i = 0; i < parcels.size(); i++) {
            currentWeight += parcels.get(i).getWeight();
        }

        if (currentWeight + parcel.getWeight() > maxWeight) {
            System.out.println("Нельзя добавить посылку <<" + parcel.getDescription() + ">>: превышен максимальный вес коробки.");
            return;
        }

        parcels.add(parcel);
    }

    public ArrayList<T> getAllParcels() {
        return parcels;
    }
}