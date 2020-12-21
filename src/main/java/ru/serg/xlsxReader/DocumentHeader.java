package ru.serg.xlsxReader;

import java.util.Objects;


//POJO for header document

public class DocumentHeader {

    private String documentNumber;
    private String carNumber;

    public DocumentHeader(String documentNumber, String carNumber) {
        this.documentNumber = documentNumber;
        this.carNumber = carNumber;
    }

    public DocumentHeader() {
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentHeader that = (DocumentHeader) o;
        return documentNumber.equals(that.documentNumber) &&
                carNumber.equals(that.carNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentNumber, carNumber);
    }

    @Override
    public String toString() {
        return "Сar number = " + carNumber + "\n" +
                "Document number = " + documentNumber;
    }
}
