import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import rencredit.Solution;

import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.*;

@Listeners(MyTestListener.class)
public class Test1 {

    private Solution sol = new Solution();

    @BeforeMethod
    private void openStatement() throws InterruptedException {
        sol.driver();
        open("https://rencredit.ru");

    }

    @Test
    public void test1_check(){
        Assert.assertTrue(title().contains("Ренессанс Кредит"));
    }
    @Test
    public void test1_logo() throws Exception{
        sol.saveLogo();
    }

    @Test
    public void test1_PDF() throws Exception {
        sol.gotoInvestorsPage();
        sol.savePDF();
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void test2_textBox() throws Exception {
        sol.sumTextBox();
        TimeUnit.SECONDS.sleep(2);
    }

    /* @Test
       public void test2_slider() {
           Selenide.open("https://rencredit.ru/contributions/");
           sol.setSliderToAmount(5000000);
           TimeUnit.SECONDS.sleep(2);
       }*/

    @Test
    public void test2_comboBox() throws Exception {
        sol.sumComboBox();
        TimeUnit.SECONDS.sleep(2);
    }
}