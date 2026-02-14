package ru.yandex.practicum.delivery;

public class PerishableParcel extends Parcel {
    private static final int BASIC_COST = 3;
    private final int timeToLive;

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    @Override
    protected int getBasicCost() {
        return BASIC_COST;
    }

    public boolean isExpired(int currentDay) {
        return getSendDay() + getTimeToLive() < currentDay;
    }

    public int getTimeToLive() {
        return timeToLive;
    }
}
