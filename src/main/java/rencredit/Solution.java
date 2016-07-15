package rencredit;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.actions;
public class Solution {

    String cssBaloon = ".calculator__slide-section div[class*='ui-slider'] span";
    String cssAmountInput = ".calculator__slide-section input[name='amount']";

    public void driver(){
        try{
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        }
        catch (SQLException e){
            System.out.println("ошибка регистрации драйвера");
        }
    }

    public void saveLogo() throws IOException, InterruptedException {
        URL url = new URL($(By.xpath("//img[@class='logo__image']")).getAttribute("src"));
        BufferedImage bufferedImage = ImageIO.read(url);
        ImageIO.write(bufferedImage, "png", new File("logo.png"));
        System.out.println("Image downloaded");
        TimeUnit.SECONDS.sleep(1);
    }

    public void gotoInvestorsPage()throws InterruptedException {
        $(By.xpath("//a[@href='/about/']")).click();
        $(By.xpath("//a[@href='/investors/']")).click();
        $(By.xpath("//a[@href='/investors/reporting/']")).click();
        $(By.xpath("//a[@href='/investors/reporting/otchetnost-banka-po-msfo/otchetnost-banka/']")).click();
        TimeUnit.SECONDS.sleep(1);
    }



    public String gotoDeposit(){
        $(By.xpath("//div/div/div/a[@href='/contributions/'][@class='home-nav__title-link']")).click();
        String beforeSum = checkSum();
        System.out.println(beforeSum);
        return beforeSum;
    }

    public String checkSum(){
        return $(By.xpath("//span[@class='js-calc-result js-calc-result-noanim']")).getText().replaceAll(" ", "");
    }

    public void change(String before, String after){
        if(!before.equals(after))
            System.out.println("Изменено");
        else
            System.out.println("Не изменено");
    }

    public void sumTextBox() throws InterruptedException {
        String beforeSum = gotoDeposit();

        $(By.name("amount")).val("125000");
        TimeUnit.SECONDS.sleep(2);

        String afterSum = checkSum();
        System.out.println(afterSum);

        change(beforeSum, afterSum);
        TimeUnit.SECONDS.sleep(1);

    }

    public void moveSlider(ICondition direction, ICondition finish) {
        actions().clickAndHold($(cssBaloon))
                .perform();

        while (!finish.isTrue()) {
            actions().moveByOffset(direction.isTrue() ? 1 : -1, 0)
                    .perform();
        }
        actions().release()
                .perform();
    }

    public void setSliderToAmount(int amount) {
        // Лямбда выражении (Java 8)
        moveSlider( () -> amount > getCurrentAmount(),
                () -> getCurrentAmount() == amount);
    }

    public int getCurrentAmount() {
        return Integer.parseInt($(cssAmountInput).val().replace(" ",""));
    }

    public void sumComboBox() throws InterruptedException {
        String beforeSum = gotoDeposit();

        $(By.className("jq-selectbox__trigger")).click();
        Select dropdown=new Select($(By.id("period")));
        dropdown.selectByVisibleText("3 месяца");

        TimeUnit.SECONDS.sleep(3);
        String afterSum = checkSum();
        System.out.println(afterSum);

        change(beforeSum, afterSum);
        TimeUnit.SECONDS.sleep(1);

    }

}
