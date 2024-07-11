package org.IrvinCampos;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;

public class ProvideDataTest {
    @Test(dataProvider = "driveTest")
    public void testCaseData(String greeting, String communication, String id) {
        System.out.println(greeting+" "+communication+" "+id);
    }

    @DataProvider(name="driveTest")
    public Object[][] getData() throws IOException {
//        Object[][] data = {{"hello","text",1},{"bye","message",2},{"solo","call",3}};
//        every row of excel should be sent to 1 array
//        return data;
        DataFormatter formatter = new DataFormatter();
        FileInputStream file = new FileInputStream("C://Users//Irvin//Downloads//ExcelDriven.xlsx");
        XSSFWorkbook workBook = new XSSFWorkbook(file);
        XSSFSheet sheet = workBook.getSheetAt(0);
        int rowCount = sheet.getPhysicalNumberOfRows();
        XSSFRow row = sheet.getRow(0);
        int columnCount = row.getLastCellNum();
        Object[][] data = new Object[rowCount-1][columnCount];
        for (int i=0; i<rowCount-1; i++) {
            row = sheet.getRow(i+1);
            for (int j=0; j<columnCount; j++) {
                XSSFCell cell = row.getCell(j);

                data[i][j]=formatter.formatCellValue(cell);
            }
        }
        return data;
    }
}
