package org.IrvinCampos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import java.time.Duration;

public class UploadDownload {
    public static void main(String[] args) {
        String fruitName = "Apple";
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(7));
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/upload-download-test/index.html");

//        download
        driver.findElement(By.xpath("//button[@id = 'downloadButton']")).click();
//        edit excel
//        upload
        WebElement upload = driver.findElement(By.cssSelector("input[type='file']"));
        upload.sendKeys("C://Users//Irvin//Downloads//download.xlsx");
//        wait for success message  to show up and wait for disappear
        By toastLocator = By.cssSelector(".Toastify__toast-body div:nth-child(2)");
        wait.until(ExpectedConditions.visibilityOfElementLocated(toastLocator));
        String toastText = driver.findElement(toastLocator).getText();
        System.out.println(toastText);
        Assert.assertEquals("Updated Excel Data Successfully.",toastText);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(toastLocator));

//        verify updated excel data showing in the web table
//        //div[text()='Apple']/parent::div/parent::div/div[@id = 'cell-4-undefined']
//        more dynamic way to get the price
        String priceColumn =driver.findElement(By.xpath("//div[text()='Price']")).getAttribute("data-column-id");
        String actualPrice = driver.findElement(By.xpath("//div[text()='"+fruitName+"']/parent::div/parent::div/div[@id = 'cell-"+priceColumn+"-undefined']")).getText();
        System.out.println(actualPrice);
        Assert.assertEquals("345",actualPrice);

        driver.quit();


//        hardcoded way to get the price
//        WebElement applePrice = driver.findElement(By.xpath("//div[@id='row-1']/div[@id='cell-4-undefined']"));
//        String price = applePrice.getText();
//        System.out.println(price);
//        Assert.assertEquals("345",price);


    }
}
