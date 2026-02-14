package ru.yandex.practicum.delivery;

public abstract class Parcel {
    private final String description;
    private final int weight;
    private final String deliveryAddress;
    private final int sendDay;

    public Parcel(String description, int weight, String deliveryAddress, int sendDay) {
        this.description = description;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.sendDay = sendDay;
    }

    protected void packageItem() {
        System.out.println("Посылка <<" + getDescription() + ">> упакована");
    }

    protected void deliver() {
        System.out.println("Посылка <<" + getDescription() + ">> доставлена по адресу " + getDeliveryAddress());
    }

    protected abstract int getBasicCost();

    public int calculateDeliveryCost() {
        return getWeight() * getBasicCost();
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public int getSendDay() {
        return sendDay;
    }
}
