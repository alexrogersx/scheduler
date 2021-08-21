package logic.enums;


public enum ItemType {
    APPOINTMENT("Appointment"),
    CUSTOMER("Customer"),
    APPLICATION("Application");

    public final String label;

    ItemType(String label) {
        this.label = label;
    }
}