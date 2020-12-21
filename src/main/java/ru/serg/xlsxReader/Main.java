package ru.serg.xlsxReader;

import org.apache.poi.xssf.usermodel.XSSFSheet;

public class Main {
    public static void main(String[] args) {

        XSSFSheet workbook = new XlsxFileReader().openSheet("file.xlsx", 0);
        XlsxHandler xlsxReader = new XlsxHandler(workbook);
        xlsxReader.handle();

    }
}
