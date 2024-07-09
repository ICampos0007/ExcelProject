package org.IrvinCampos;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class DataDriven {
//    Identify TestCases column by scanning the entire 1st row
//    Once column is identified then scan entire testcase column to identify purchase testcase row
//    After you grab testcase row = pull all the data of that row and feed into test
    public static void main(String[] args) throws IOException, InvalidFormatException {
//        fileInputStream argument
        File file = new File("C://Users//Irvin//Downloads//Book.xlsx");
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        int sheets = xssfWorkbook.getNumberOfSheets();
        for (int i =0; i<sheets;i++) {
            if (xssfWorkbook.getSheetName(i).equalsIgnoreCase("Sheet1")) {
                XSSFSheet sheet = xssfWorkbook.getSheetAt(i);
                //    Identify TestCases column by scanning the entire 1st row
                Iterator<Row> rows= sheet.iterator(); // sheet is collection of rows
                Row firstRow = rows.next();
                Iterator<Cell> cell = firstRow.cellIterator(); // row is collection of cells
                int k = 0;
                int column = 0;
                while (cell.hasNext()) {
                    Cell value = cell.next();
                    if (value.getStringCellValue().equalsIgnoreCase("Data2")) {
//                        desired column
                        column=k;

                    }
                    k++;
                }
                System.out.println(column);

            }
        }
    }
}
