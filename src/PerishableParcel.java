import java.util.Scanner;

public class PerishableParcel extends Parcel{

    private static final int BASIC_COST = 3;
    Scanner scanner = new Scanner(System.in);
    int timeToLive;

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    @Override
    int calculateDeliveryCost() {
        return weight * BASIC_COST;
    }

    public boolean isExpired(int currentDay) {
        return (sendDay + timeToLive) < currentDay;
    }

    @Override
    public void deliver() {
        System.out.print("Введите номер текущего дня: ");
        int currentDay = Integer.parseInt(scanner.nextLine());
        if (isExpired(currentDay)) {
            System.out.println("Срок годности посылки истёк!");
        } else {
            super.deliver();
        }
    }
}
