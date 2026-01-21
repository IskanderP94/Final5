public class StandardParcel extends Parcel {

    private static final int BASIC_COST = 2;

    public StandardParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    int calculateDeliveryCost() {
        return weight * BASIC_COST;
    }
}
