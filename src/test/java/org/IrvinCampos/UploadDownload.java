package org.IrvinCampos;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;

public class UploadDownload {
    public static void main(String[] args) throws IOException, InvalidFormatException, InterruptedException {
        String fruitName = "Apple";
        String updatedValue = "599";
        String fileName = "C://Users//Irvin//Downloads//download.xlsx";
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/upload-download-test/index.html");

        // Download
        driver.findElement(By.xpath("//button[@id = 'downloadButton']")).click();

        // Wait for the file to be downloaded
        waitForFile(fileName, 30);

        // Edit excel - getColumnNumber of price - getRowNumber of Apple -> update excel with row, col
        int col = getColumnNumber(fileName, "price");
        int row = getRowNumber(fileName, "Apple");
        Assert.assertTrue(updateCell(fileName, row, col, updatedValue));

        // Upload
        WebElement upload = driver.findElement(By.cssSelector("input[type='file']"));
        upload.sendKeys(fileName);

        // Wait for success message to show up and wait for it to disappear
        By toastLocator = By.cssSelector(".Toastify__toast-body div:nth-child(2)");
        wait.until(ExpectedConditions.visibilityOfElementLocated(toastLocator));
        String toastText = driver.findElement(toastLocator).getText();
        System.out.println(toastText);
        Assert.assertEquals("Updated Excel Data Successfully.", toastText);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(toastLocator));

        // Verify updated excel data showing in the web table
        String priceColumn = driver.findElement(By.xpath("//div[text()='Price']")).getAttribute("data-column-id");
        String actualPrice = driver.findElement(By.xpath("//div[text()='" + fruitName + "']/parent::div/parent::div/div[@id = 'cell-" + priceColumn + "-undefined']")).getText();
        System.out.println(actualPrice);
        Assert.assertEquals(updatedValue, actualPrice);

        driver.quit();
    }

    private static int getRowNumber(String fileName, String text) throws IOException, InvalidFormatException {
        File file = new File(fileName);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        XSSFSheet sheet = xssfWorkbook.getSheet("Sheet1");
        Iterator<Row> rows = sheet.iterator();
        int k = 1;
        int rowIndex = -1;
        while (rows.hasNext()) {
            Row row = rows.next();
            Iterator<Cell> cells = row.cellIterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                if (cell.getCellType() == CellType.STRING && cell.getStringCellValue().equalsIgnoreCase(text)) {
                    rowIndex = k;
                }
            }
            k++;
        }
        return rowIndex;
    }

    private static int getColumnNumber(String fileName, String columnName) throws IOException, InvalidFormatException {
        File file = new File(fileName);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        XSSFSheet sheet = xssfWorkbook.getSheet("Sheet1");
        Iterator<Row> rows = sheet.iterator();
        Row firstRow = rows.next();
        Iterator<Cell> cell = firstRow.cellIterator();
        int k = 1;
        int column = 0;
        while (cell.hasNext()) {
            Cell value = cell.next();
            if (value.getStringCellValue().equalsIgnoreCase(columnName)) {
                column = k;
            }
            k++;
        }
        System.out.println(column);
        return column;
    }

    private static boolean updateCell(String fileName, int row, int col, String updatedValue) throws IOException, InvalidFormatException {
        File file = new File(fileName);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        XSSFSheet sheet = xssfWorkbook.getSheet("Sheet1");
        Row rowField = sheet.getRow(row - 1);
        Cell cellField = rowField.getCell(col - 1);
        cellField.setCellValue(updatedValue);
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        xssfWorkbook.write(fileOutputStream);
        xssfWorkbook.close();
        fileOutputStream.close();
        return true;
    }

    private static void waitForFile(String filePath, int timeoutInSeconds) throws InterruptedException {
        File file = new File(filePath);
        int waited = 0;
        while (!file.exists() && waited < timeoutInSeconds) {
            Thread.sleep(1000);
            waited++;
        }
        if (!file.exists()) {
            throw new RuntimeException("File not downloaded within timeout period.");
        }
    }
}
