package ru.serg.xlsxReader;

public class DocumentFooter {
    private int totalBattery;
    private int totalPallets;

    public DocumentFooter(int totalBattery, int totalPallets) {
        this.totalBattery = totalBattery;
        this.totalPallets = totalPallets;
    }

    public int getTotalBattery() {
        return totalBattery;
    }

    public void setTotalBattery(int totalBattery) {
        this.totalBattery = totalBattery;
    }

    public int getTotalPallets() {
        return totalPallets;
    }

    public void setTotalPallets(int totalPallets) {
        this.totalPallets = totalPallets;
    }

    @Override
    public String toString() {
        return "Total battery = " + totalBattery +
                "\n" + "Total pallets = " + totalPallets;
    }
}
