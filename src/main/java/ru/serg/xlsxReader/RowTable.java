package ru.serg.xlsxReader;

import java.util.Objects;

public class RowTable {

    private int numRow;
    private String nameProduct;
    private String barCode;
    private int numberBatteries;
    private int numberPallet;

    public RowTable(int numRow, String nameProduct, String barCode, int numberBatteries, int numberPallet) {
        this.numRow = numRow;
        this.nameProduct = nameProduct;
        this.barCode = barCode;
        this.numberBatteries = numberBatteries;
        this.numberPallet = numberPallet;
    }

    public int getNumRow() {
        return numRow;
    }

    public void setNumRow(int numRow) {
        this.numRow = numRow;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public int getNumberBatteries() {
        return numberBatteries;
    }

    public void setNumberBatteries(int numberBatteries) {
        this.numberBatteries = numberBatteries;
    }

    public int getNumberPallet() {
        return numberPallet;
    }

    public void setNumberPallet(int numberPallet) {
        this.numberPallet = numberPallet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RowTable rowTable = (RowTable) o;
        return numRow == rowTable.numRow &&
                numberBatteries == rowTable.numberBatteries &&
                numberPallet == rowTable.numberPallet &&
                nameProduct.equals(rowTable.nameProduct) &&
                barCode.equals(rowTable.barCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numRow, nameProduct, barCode, numberBatteries, numberPallet);
    }

    @Override
    public String toString() {
        return nameProduct + " |" + barCode + " | " + numberBatteries + "| " + numberPallet + " |";
    }
}
