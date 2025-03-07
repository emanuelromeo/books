package com.crud.books.enumerate;

public enum RecordStatus {
    A("Active"),
    D("Deleted");

    private final String description;

    RecordStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
