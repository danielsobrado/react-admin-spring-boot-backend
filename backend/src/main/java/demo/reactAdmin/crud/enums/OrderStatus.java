package demo.reactAdmin.crud.enums;

public enum OrderStatus {
    ACCEPTED, DELIVERED, CANCELLED;

    public String orderStatus() {
        return this.name().toLowerCase();
    }
}
