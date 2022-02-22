package demo.reactAdmin.crud.enums;

public enum ReviewStatus {
    ACCEPTED, PENDING, REJECTED;

    public String reviewStatus() {
        return this.name().toLowerCase();
    }
}
