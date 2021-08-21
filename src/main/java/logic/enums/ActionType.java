package logic.enums;

public enum ActionType {
    ADD("Add"),
    UPDATE("Update"),
    REMOVE("Remove"),
    CANCEL("Cancel"),
    EXIT("Exit");

    public final String label;

    ActionType(String label) {
        this.label = label;
    }
}