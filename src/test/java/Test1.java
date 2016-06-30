import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

public class Test1 {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();

    }

    @Test
    public void gotoRencreditPage() {
        driver.get("https://rencredit.ru");
        assertTrue(driver.getTitle().contains("Ренессанс Кредит"));
    }

    public void saveLogo()
    {
        //найти, как сохранить логитип сайта
    }

    @Test
    public void gotoInvestorsPage(){
        driver.get("https://rencredit.ru/investors/reporting/");
        //найти, как сохранить pdf

    }

}
