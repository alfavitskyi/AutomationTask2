package HWTask2;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.xpath;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class TestsAvicHw1 {
    private WebDriver driver;


    @BeforeTest
    public void profileSetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

    @BeforeMethod
    public void testSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://avic.ua/ua");
    }

    @Test(priority = 1)
    public void checkThatUrlContainsSearchWord() {
        driver.findElement(xpath("//input[@id='input_search']")).sendKeys("Xiaomi Mi Robot");
        driver.findElement(xpath("//button[@class='button-reset search-btn']")).click();
        assertTrue(driver.getCurrentUrl().contains("query=Xiaomi"));
    }


    @Test(priority = 2)
    public void checkNumberOfElementsOnPage() {
        driver.findElement(xpath("//input[@id='input_search']")).sendKeys("Xiaomi Mi Robot");
        driver.findElement(xpath("//button[@class='button-reset search-btn']")).click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        //scroll to button
        js.executeScript("window.scrollBy(0,300)");

        WebElement filterOption = driver.findElement(xpath("//label[contains(text(),'Пилососи')]"));
        filterOption.click();

        WebElement element = driver.findElement(xpath("//a[@class='btn-see-more js_show_more']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();

        driver.findElement(xpath("//a[@class='btn-see-more js_show_more']")).click();
        //wait for page load with new results
        waitInSeconds(3);

        List<WebElement> elementList = driver.findElements(xpath("//a[@class='prod-cart__buy']"));
        int numberOfGoods = elementList.size();
        int expectedSize = 24;

        assertEquals(numberOfGoods, expectedSize);
    }

    @Test(priority = 3)
    public void checkAddGoodsToCart (){
        driver.findElement(xpath("//span[@class='sidebar-item']")).click();
        driver.findElement(xpath("//ul[contains(@class,'sidebar-list')]//a[contains(@href, 'gadzhetyi1')]")).click();
        driver.findElement(xpath("//div[@class='brand-box__title']/a[contains(@href,'smart-chasyi')]")).click();
        new WebDriverWait(driver, 30).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        driver.findElement(xpath("//a[@class='prod-cart__buy'][contains(@data-ecomm-cart,'SM-R890NZKA')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("js_cart")));
        driver.findElement(xpath("//div[@class='btns-cart-holder']//a[contains(@class,'btn--orange')]")).click();
        driver.findElement(xpath("//div[@class='header-bottom__cart active-cart flex-wrap middle-xs js-btn-open']//div[contains(@class,'active-cart-item js_cart_count')]")).click();
        driver.findElement(xpath("//span[@class='js_plus btn-count btn-count--plus ']")).click();
        waitInSeconds(3);
            String numberOfGoods = driver.findElement(xpath("//div[contains(@class,'header-bottom__cart')]//div[contains(@class,'cart_count')]")).getText();
        assertEquals(numberOfGoods,"2");
    }
    private void waitInSeconds(int seconds) {
    try {
        Thread.sleep(seconds * 1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}


    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}