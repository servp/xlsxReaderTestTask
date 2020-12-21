package ru.serg.xlsxReader;


import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class XlsxFileReader {


    public XSSFSheet openSheet(String fileName, int numberListOfWorkbook) {
        XSSFSheet sheet = null;
        try {
            InputStream file = new FileInputStream(fileName);
            XSSFWorkbook workbook = new XSSFWorkbook(file);//XSSFWorkBook т.к. office 2007+ в котором формировался файл
            sheet = workbook.getSheetAt(numberListOfWorkbook);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheet;
    }


}
