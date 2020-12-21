package ru.serg.xlsxReader;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class XlsxHandlerTest {

    XSSFSheet sheet;
    XlsxHandler xlsxHandler;
    List<RowTable> expectedRowTableList;

    @Before
    public void initXSSFWorkbook() {
        sheet = new XlsxFileReader().openSheet("testingFile.xlsx", 0);
        xlsxHandler = new XlsxHandler(sheet);

        expectedRowTableList = new ArrayList<>();
        expectedRowTableList.add(new RowTable(17, "ALETERNUM B4 500/100 BIANCO", "8015040017859", 48, 1));
        expectedRowTableList.add(new RowTable(18, "EXCLUSIVO B3 800/100 BIANCO", "8015040480165", 24, 1));
    }

    @Test
    public void testHandle() {
        //тест не нужен. Логика вызываемых методов тестируется самостоятельными тестами

        boolean actualResult = xlsxHandler.handle();
        Assert.assertEquals(true,actualResult);
    }

    @Test
    public void testReadDocumentHeader() {
        DocumentHeader expectedDocumentHeader =
                new DocumentHeader("2200000483", "KHS 142 //  JM 988");
        DocumentHeader actualDocumentHeader = xlsxHandler.readDocumentHeader();
        Assert.assertEquals(expectedDocumentHeader, actualDocumentHeader);
    }

    @Test
    public void testFindEndRow() {
        int expectedEndRow = 42;
        int actualEndRow = xlsxHandler.findEndRow();
        Assert.assertEquals(expectedEndRow, actualEndRow);
    }

    @Test
    public void testReadTable() {
        List<RowTable> actualRowTableList = xlsxHandler.readTable(19);
        System.out.println(expectedRowTableList.equals(actualRowTableList));
        Assert.assertEquals(expectedRowTableList, actualRowTableList);
    }

    @Test
    public void testSizeExpectedRowTableList() {
        List<RowTable> actualRowTableList = xlsxHandler.readTable(19);
        int expectedSize = 2;
        Assert.assertEquals(expectedSize, actualRowTableList.size());
    }

    @Test
    public void testFindTotal() {
        int expectedIndexRow = 44;
        int actualIndexRow = xlsxHandler.findTotal();
        Assert.assertEquals(expectedIndexRow, actualIndexRow);
    }

    @Test
    public void testGetTotal() {
        int expectedPallet = 49;
        int expectedBattery = 216;
        DocumentFooter actualTotal = xlsxHandler.getTotal();
        Assert.assertNotNull(actualTotal);
        int actualPallet = actualTotal.getTotalPallets();
        Assert.assertEquals(expectedPallet, actualPallet);
        int actualBattery = actualTotal.getTotalBattery();
        Assert.assertEquals(expectedBattery, actualBattery);
    }

    @Test
    public void testCheckQuantity() {
        DocumentFooter actualTotal1 = new DocumentFooter(144, 47);
        boolean if1 = xlsxHandler.checkQuantity(expectedRowTableList, actualTotal1);
        Assert.assertFalse(if1);

        DocumentFooter actualTotal2 = new DocumentFooter(144, 47);
        boolean if2 = xlsxHandler.checkQuantity(expectedRowTableList, actualTotal2);
        Assert.assertFalse(if2);

        DocumentFooter actualTotal3 = new DocumentFooter(72, 2);
        boolean if3 = xlsxHandler.checkQuantity(expectedRowTableList, actualTotal3);
        Assert.assertTrue(if3);
    }
}