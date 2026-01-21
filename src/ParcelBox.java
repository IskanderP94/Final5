import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParcelBox<T extends Parcel> {

    Scanner scanner = new Scanner(System.in);
    private final List<T> box = new ArrayList<>();
    int maxWeight;
    int currentWeight = 0;

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public boolean addParcel(T parcel) {
        if (currentWeight + parcel.weight <= maxWeight) {
            box.add(parcel);
            currentWeight += parcel.weight;
            System.out.println("Посылка добавлена!");
            return true;
        } else {
            System.out.println("Достигнут максимальный вес коробки!");
            return false;
        }
    }

    public List<T> getAllParcels() {
        return box;
    }

    public void boxPrint() {
        System.out.println("Содержимое коробки: ");
        if (box.isEmpty()) {
            System.out.println("Коробка пуста");
            return;
        }
        for (T item : getAllParcels()) {
            System.out.println(item.description);
        }
        System.out.println("Общий вес коробки = " + currentWeight + ", макс. вес коробки = " + maxWeight);
    }

}
