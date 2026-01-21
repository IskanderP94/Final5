import javax.sound.midi.Track;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;


public class DeliveryApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();
    private static List<Trackable> fragileParcels = new ArrayList<>();

    private static ParcelBox<StandardParcel> standardParcel;
    private static ParcelBox<FragileParcel> fragileParcel;
    private static ParcelBox<PerishableParcel> perishableParcel;

    public static void main(String[] args) {
        paramBox();

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
                    updateTracking();
                    break;
                case 5:
                    printBox();
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
        System.out.println("4 — Показать трекинг отправления");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }


    private static void addParcel() {
        // Подсказка: спросите тип посылки и необходимые поля,
        // создайте объект и добавьте в allParcels
        System.out.print("Укажите тип посылки (1. Стандартная, 2. Хрупкая, 3. Скоропортящаяся, срок годности): ");
        int typeOfParcel = Integer.parseInt(scanner.nextLine());

        System.out.print("Укажите описание посылки: ");
        String description = scanner.nextLine();

        System.out.print("Укажите вес посылки: ");
        int weight = Integer.parseInt(scanner.nextLine());

        System.out.print("Укажите адрес доставки посылки: ");
        String deliveryAddress = scanner.nextLine();

        System.out.print("Укажите дату отправления посылки: ");
        int sendDay = Integer.parseInt(scanner.nextLine());

        Parcel parcel;
        if (typeOfParcel == 1) {
            parcel = new StandardParcel(description, weight, deliveryAddress, sendDay);
            if (standardParcel.addParcel((StandardParcel) parcel)) {
                allParcels.add(parcel);
            }
        } else if (typeOfParcel == 2) {
            parcel = new FragileParcel(description, weight, deliveryAddress, sendDay);
            if (fragileParcel.addParcel((FragileParcel) parcel)) {
                allParcels.add(parcel);
                fragileParcels.add((Trackable) parcel);
            }
        } else if (typeOfParcel == 3) {
            System.out.println("Укажите срок годности продукта");
            int timeToLive = Integer.parseInt(scanner.nextLine());
            parcel = new PerishableParcel(description, weight, deliveryAddress, sendDay,
                    timeToLive);
            if (perishableParcel.addParcel((PerishableParcel) parcel)) {
                allParcels.add(parcel);
            }
        }
    }

    private static void sendParcels() {
        // Пройти по allParcels, вызвать packageItem() и deliver()
        for (Parcel p : allParcels) {
            p.packageItem();
            p.deliver();
        }
    }

    private static void calculateCosts() {
        // Посчитать общую стоимость всех доставок и вывести на экран
        int result = 0;
        for (Parcel p : allParcels) {
            result += p.calculateDeliveryCost();
        }
        System.out.println("Стоимость всех доставок равна " + result);
    }

    private static void updateTracking() {
        System.out.println("Введите новую локацию: ");
        String newLocation = scanner.nextLine();
        for (Trackable trackable : fragileParcels) {
            trackable.reportStatus(newLocation);
        }
    }

    private static void printBox() {
        System.out.println("Выберите коробку (1. Стандартные, 2. Хрупкие, 3. Скоропортящиеся");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 1) {
            standardParcel.boxPrint();
        } else if (choice == 2) {
            fragileParcel.boxPrint();
        } else if (choice == 3) {
            perishableParcel.boxPrint();
        } else {
            System.out.println("Неверный выбор!");
        }
    }

    private static void paramBox() {
        System.out.print("Коробка для посылок 'Стандартные', введите макс. вес: ");
        int maxWeightStandard = Integer.parseInt(scanner.nextLine());
        standardParcel = new ParcelBox<>(maxWeightStandard);
        System.out.println("Максимальный вес коробки 'Стандартные' = " + maxWeightStandard + "\n");

        System.out.print("Коробка для посылок 'Хрупкие', введите макс. вес: ");
        int maxWeightFragile = Integer.parseInt(scanner.nextLine());
        fragileParcel = new ParcelBox<>(maxWeightFragile);
        System.out.println("Максимальный вес коробки 'Хрупкие' = " + maxWeightFragile + "\n");

        System.out.print("Коробка для посылок 'Скоропортящиеся', введите макс. вес: ");
        int maxWeightPerishable = Integer.parseInt(scanner.nextLine());
        perishableParcel = new ParcelBox<>(maxWeightPerishable);
        System.out.println("Максимальный вес коробки 'Скоропортящиеся' = " + maxWeightPerishable + "\n");
    }



}


