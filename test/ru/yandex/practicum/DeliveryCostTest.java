package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.*;

public class DeliveryCostTest {

    @Test
    void standardParcelCost_isCorrect() {
        // BASIC_COST = 2
        StandardParcel parcel = new StandardParcel("books", 5, "Moscow", 1);
        Assertions.assertEquals(10, parcel.calculateDeliveryCost(), "Стоимость стандартной посылки некорректна");
    }

    @Test
    void fragileParcelCost_isCorrect() {
        // BASIC_COST = 4
        FragileParcel parcel = new FragileParcel("glass", 3, "Moscow", 1);
        Assertions.assertEquals(12, parcel.calculateDeliveryCost(), "Стоимость хрупкой посылки некорректна");
    }

    @Test
    void perishableParcelCost_boundaryWeightZero_isZero() {
        // граничный случай: вес 0
        PerishableParcel parcel = new PerishableParcel("food", 0, "Moscow", 1, 5);
        Assertions.assertEquals(0, parcel.calculateDeliveryCost(), "При нулевом весе стоимость должна быть 0");
    }

    // --- 2) PerishableParcel.isExpired ---

    @Test
    void isExpired_returnsFalse_whenNotExpired() {
        // sendDay=10, ttl=5 => испортится после дня 15, на 14 ещё не испорчена
        PerishableParcel parcel = new PerishableParcel("milk", 1, "Moscow", 10, 5);
        Assertions.assertFalse(parcel.isExpired(14), "Посылка не должна считаться испорченной");
    }

    @Test
    void isExpired_returnsTrue_whenExpired() {
        // sendDay=10, ttl=5 => если currentDay=16, то 10+5 < 16 => true
        PerishableParcel parcel = new PerishableParcel("milk", 1, "Moscow", 10, 5);
        Assertions.assertTrue(parcel.isExpired(16), "Посылка должна считаться испорченной");
    }

    @Test
    void isExpired_boundary_onLastValidDay_isFalse() {
        // граничный: currentDay = sendDay + ttl (15) => 15 < 15 false
        PerishableParcel parcel = new PerishableParcel("milk", 1, "Moscow", 10, 5);
        Assertions.assertFalse(parcel.isExpired(15), "В последний допустимый день посылка не должна считаться испорченной");
    }

    // --- 3) ParcelBox.addParcel (по весу) ---

    @Test
    void addParcel_addsWhenWeightWithinLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);

        StandardParcel p1 = new StandardParcel("A", 4, "Addr", 1);
        StandardParcel p2 = new StandardParcel("B", 6, "Addr", 1);

        box.addParcel(p1);
        box.addParcel(p2);

        Assertions.assertEquals(2, box.getAllParcels().size(), "Обе посылки должны быть добавлены");
    }

    @Test
    void addParcel_doesNotAddWhenLimitExceeded() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(9);

        StandardParcel p1 = new StandardParcel("A", 4, "Addr", 1);
        StandardParcel p2 = new StandardParcel("B", 6, "Addr", 1); // суммарно 10 > 9

        box.addParcel(p1);
        box.addParcel(p2);

        Assertions.assertEquals(1, box.getAllParcels().size(), "Посылка, превышающая лимит, не должна добавляться");
        Assertions.assertSame(p1, box.getAllParcels().get(0), "В коробке должна остаться первая посылка");
    }

    @Test
    void addParcel_boundary_exactlyAtLimit_adds() {
        // граничный: ровно по лимиту
        ParcelBox<FragileParcel> box = new ParcelBox<>(10);

        FragileParcel p1 = new FragileParcel("A", 7, "Addr", 1);
        FragileParcel p2 = new FragileParcel("B", 3, "Addr", 1); // 7+3 == 10

        box.addParcel(p1);
        box.addParcel(p2);

        Assertions.assertEquals(2, box.getAllParcels().size(), "Если вес ровно по лимиту, посылка должна добавляться");
    }

}
