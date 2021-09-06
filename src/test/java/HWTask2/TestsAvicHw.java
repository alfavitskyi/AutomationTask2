package HWTask2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class TestsAvicHw {
    private WebDriver driver;

    @BeforeTest
    public void profileSetUp (){
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void testSetUp (){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://avic.ua/ua");
    }

    @Test(priority = 1)
    public void checkThatUrlContainsSearchWord (){
        driver.findElement(xpath("//input[@id='input_search']")).sendKeys("Xiaomi Mi Robot");
        driver.findElement(xpath("//button[@class='button-reset search-btn']")).click();
        assertTrue(driver.getCurrentUrl().contains("query=Xiaomi"));
    }


    @Test(priority = 2)
    public void checkNumberOfElementsOnPage (){
        driver.findElement(xpath("//input[@id='input_search']")).sendKeys("Xiaomi Mi Robot");
        driver.findElement(xpath("//button[@class='button-reset search-btn']")).click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebElement filterOption = driver.findElement(xpath("//input[@id='fltr-category-830']"));
        filterOption.click();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(xpath("//a[@class='btn-see-more js_show_more']")).click();
        List<WebElement> elementList = driver.findElements(xpath("//a[@class='prod-cart__buy']"));
        int numberOfGoods = elementList.size();
        assertEquals(numberOfGoods, 24);


    }
    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}
