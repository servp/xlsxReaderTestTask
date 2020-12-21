package ru.serg.xlsxReader;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.*;

public class XlsxHandler {

    private final XSSFSheet sheet;

    public XlsxHandler(XSSFSheet sheet) {
        this.sheet = sheet;
    }

    public boolean handle() {
        DocumentHeader documentHeader = readDocumentHeader();
        printDocumentHeader(documentHeader);
        int endRowInTable = findEndRow();
        List<RowTable> rowTableList = readTable(endRowInTable);
        printTableOnConsole(rowTableList);
        DocumentFooter total = getTotal();
        return checkQuantity(rowTableList, total);
    }

    // чтение шапки таблицы
    protected DocumentHeader readDocumentHeader() {

        CellReference E11 = new CellReference("E11");
        Row row11 = sheet.getRow(E11.getRow());
        Cell cell11 = row11.getCell(E11.getCol());

        CellReference E13 = new CellReference("E13");
        Row row13 = sheet.getRow(E13.getRow());
        Cell cell13 = row13.getCell(E13.getCol());

        double tempNumberDocument = cell13.getNumericCellValue();
        String numberDocument = String.format("%.0f", tempNumberDocument);
        String numberCar = cell11.getStringCellValue();
        DocumentHeader documentHeader = new DocumentHeader(numberDocument, numberCar);
        return documentHeader;
    }

    protected void printDocumentHeader(DocumentHeader documentHeader) {
        System.out.println(documentHeader.toString());
        System.out.println();
    }

    //поиск строки с TOTAL или пустых строк, определение конечной строки табличной части
    protected int findEndRow() {
        final int MAX_ROW = sheet.getLastRowNum();
        int endRow = 0;
        //find the final row to select
        for (int i = 17; i <= MAX_ROW; i++) {
            Row currentRow = sheet.getRow(i);
            Cell cell = currentRow.getCell(0);
            if (cell.getStringCellValue().isEmpty()) {
                endRow = i;
                System.out.println("endRow=" + i);
                break;
            }
            if (cell.getCellTypeEnum().equals(CellType.STRING) && cell.getStringCellValue().contains("TOTAL")) {
                endRow = i;
                break;
            }
        }
        return endRow;
    }

    //find  TOTAL row
    protected int findTotal() {
        final int MAX_ROW = sheet.getLastRowNum();
        int totalRow = 0;
        //find the final row to select
        for (int i = 17; i <= MAX_ROW; i++) {
            Row currentRow = sheet.getRow(i);
            Cell cell = currentRow.getCell(0);
            if (cell.getCellTypeEnum().equals(CellType.STRING) && cell.getStringCellValue().contains("TOTAL")) {
                totalRow = i;
                break;
            }
        }
        return totalRow;
    }

    // вычитывание табличной части
    protected List<RowTable> readTable(int endRow) {
        List<RowTable> rowTableList = new ArrayList<>();
        for (int row = 17; row < endRow; row++) {
            String productName = "";
            String barCode = "";
            int numberBatteries = 0;
            int numberPallet = 0;

            for (int col = 0; col < 8; col++) {
                Row currentRow = sheet.getRow(row);
                Cell currentCell = currentRow.getCell(col);
                switch (col) {
                    case 0: //A
                        productName = currentCell.getStringCellValue();
                        break;
                    case 1: //B
                        double tempBarCode = currentCell.getNumericCellValue();
                        barCode = String.format("%.0f", tempBarCode);
                        break;
                    case 2: //C
                        numberBatteries = (int) currentCell.getNumericCellValue();
                        break;
                    case 6: //G
                        //проверка на "Находятся на поддонах с котлами", если внесен любой текс.
                        if (currentCell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                            numberPallet = (int) currentCell.getNumericCellValue();
                        } else {
                            numberPallet = 0;
                        }
                        break;
                }
            }
            rowTableList.add(new RowTable(row, productName, barCode, numberBatteries, numberPallet));
        }
        return rowTableList;
    }

    // печать табличной части
    protected void printTableOnConsole(List<RowTable> rowTableList) {
        for (RowTable r : rowTableList) {
            System.out.println(r.toString());
            System.out.println("---------------------------------------------------");
        }
    }

    //получение TOTAL из итоговой строки таблицы
    protected DocumentFooter getTotal() {
        DocumentFooter documentFooter = null;
        int totalRow = findTotal();
        Row row = sheet.getRow(totalRow);
        Cell cell = row.getCell(2);//C
        int assertTotalBattery = (int) cell.getNumericCellValue();
        cell = row.getCell(6);//G
        int assetTotalPallet = (int) cell.getNumericCellValue();
        documentFooter = new DocumentFooter(assertTotalBattery, assetTotalPallet);
        System.out.println(documentFooter.toString());
        return documentFooter;

    }

    //проверка по количеству
    protected boolean checkQuantity(List<RowTable> rowTableList, DocumentFooter total) {
        int assertTotalBattery = total.getTotalBattery();
        int assetTotalPallet = total.getTotalPallets();
        int actualTotalBattery = 0;
        int actualTotalPallet = 0;
        for (RowTable r : rowTableList) {
            actualTotalBattery += r.getNumberBatteries();
            actualTotalPallet += r.getNumberPallet();
        }

        if (assertTotalBattery != actualTotalBattery) {
            System.out.println("Фактическое количество батарей не совпадает с ожидаемым количеством. ");
            return false;
        }
        if (assetTotalPallet != actualTotalPallet) {
            System.out.println("Фактическое количество паллет не совпадает с ожидаемым количеством.");
            return false;
        }
        if (actualTotalBattery == assertTotalBattery && assetTotalPallet == assetTotalPallet) {
            System.out.println("Фактическое колличество совпадает с ожидаемым.");
            return true;
        }
        return false;
    }
}
