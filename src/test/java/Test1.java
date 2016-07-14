import rencredit.Solution;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.*;

@Listeners(MyTestListener.class)
public class Test1 {
    private Solution sol = new Solution();
    public static void main(String[] args) throws SQLException {
        try{
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        }
        catch (SQLException e){
            System.out.println("ошибка регистрации драйвера");
        }
    }

    @BeforeTest
    private void openStatement() throws InterruptedException {
        open("https://rencredit.ru");
        Assert.assertTrue(title().contains("Ренессанс Кредит"));
    }

    @Test
    public void test1() throws Exception {
        sol.saveLogo();
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