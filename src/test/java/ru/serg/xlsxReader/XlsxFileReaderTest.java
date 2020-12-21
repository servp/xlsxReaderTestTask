package ru.serg.xlsxReader;

import junit.framework.TestCase;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.Assert;
import org.junit.Test;


public class XlsxFileReaderTest extends TestCase {

    private String fileName = "testingFile.xlsx";
    private int indexSheet = 0;

    @Test
    public void testOpenSheet() {
        String expectedNameSheet = "Richiesta di deposito";
        XSSFSheet actualSheet = new XlsxFileReader().openSheet(fileName, indexSheet);
        Assert.assertNotNull(actualSheet);
        Assert.assertEquals(expectedNameSheet, actualSheet.getSheetName());
    }
}