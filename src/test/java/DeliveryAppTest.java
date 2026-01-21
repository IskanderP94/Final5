import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryAppTest {

    @Test
    void testCalculateCosts() {
        // Стандартная: вес 5кг * коэф 2 = 10
        StandardParcel standard = new StandardParcel("Книги", 5, "Москва", 1);
        assertEquals(10, standard.calculateDeliveryCost());

        // Хрупкая: вес 2кг * коэф 4 = 8
        FragileParcel fragile = new FragileParcel("Зеркало", 2, "СПб", 1);
        assertEquals(8, fragile.calculateDeliveryCost());

        // Скоропортящаяся: вес 10кг * коэф 3 = 30
        PerishableParcel perishable = new PerishableParcel("Сыр", 10, "Склад", 1, 5);
        assertEquals(30, perishable.calculateDeliveryCost());

        // Граничный случай: минимальный вес 1кг для хрупкой (1 * 4 = 4)
        FragileParcel smallFragile = new FragileParcel("Чип", 1, "Офис", 1);
        assertEquals(4, smallFragile.calculateDeliveryCost());

    }

    @Test
    void testIsExpired() {
        // Отправлена в день 5, срок годности 2 дня (годна до конца дня 7)
        PerishableParcel p = new PerishableParcel("Молоко", 1, "Адрес", 5, 2);

        // Стандартный сценарий: день 6 (еще годна)
        assertFalse(p.isExpired(6));

        // Стандартный сценарий: день 10 (точно испорчена)
        assertTrue(p.isExpired(10));

        // ГРАНИЦА: день 7 (последний день срока)
        // По логике большинства систем, в день истечения срока продукт еще можно использовать
        assertFalse(p.isExpired(7), "В последний день срока годности посылка еще не просрочена");
    }

    @Test
    void testParcelBoxAddLogic() {
        int maxWeight = 10;
        ParcelBox<StandardParcel> box = new ParcelBox<>(maxWeight);

        // Стандартный сценарий 1: добавление (3кг < 10кг)
        assertTrue(box.addParcel(new StandardParcel("Посылка 1", 3, "А", 1)));

        // Стандартный сценарий 2: отказ (вес 15кг > 10кг)
        assertFalse(box.addParcel(new StandardParcel("Слишком тяжелая", 15, "Б", 1)));

        // ГРАНИЦА: добавляем ровно столько, чтобы стало 10кг (сейчас 3кг, добавляем 7кг)
        assertTrue(box.addParcel(new StandardParcel("Граничная", 7, "В", 1)),
                "Посылка весом ровно в остаток лимита должна быть принята");

        // Проверка после достижения границы
        assertFalse(box.addParcel(new StandardParcel("Лишний вес", 1, "Г", 1)),
                "В полную коробку нельзя добавить даже 1кг");
    }
}
