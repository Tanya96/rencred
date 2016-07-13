import com.codeborne.selenide.Selenide;
import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.actions;

@Listeners(TestListener.class)
public class Test1 {

    public static void main(String[] args) throws SQLException {
        try{
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        }
        catch (SQLException e){
            System.out.println("ошибка регистрации драйвера");
        }
    }

    String cssBaloon = ".calculator__slide-section div[class*='ui-slider'] span";
    String cssAmountInput = ".calculator__slide-section input[name='amount']";

    private void gotoInvestorsPage()throws InterruptedException {
        $(By.xpath("//a[@href='/about/']")).click();
        $(By.xpath("//a[@href='/investors/']")).click();
        $(By.xpath("//a[@href='/investors/reporting/']")).click();
        $(By.xpath("//a[@href='/investors/reporting/otchetnost-banka-po-msfo/otchetnost-banka/']")).click();
        TimeUnit.SECONDS.sleep(1);
    }

    private void saveLogo() throws IOException, InterruptedException {
        URL url = new URL($(By.xpath("//img[@class='logo__image']")).getAttribute("src"));
        BufferedImage bufferedImage = ImageIO.read(url);
        ImageIO.write(bufferedImage, "png", new File("logo.png"));
        System.out.println("Image downloaded");
        TimeUnit.SECONDS.sleep(1);
    }

    private void savePDF() throws IOException, InterruptedException {
        System.out.println("Opening connection");
        URL url1 = new URL("https://rencredit.ru/upload/iblock/5dc/msfo_otchetnost_renkredit_2015_1a.pdf");
        InputStream in = url1.openStream();
        FileOutputStream fos = new FileOutputStream(new File("pdfFile.pdf"));

        System.out.println("Reading from resource and writing to file");
        int length;
        byte[] buffer = new byte[1024];
        while ((length = in.read(buffer)) > -1)
            fos.write(buffer, 0, length);
        fos.close();
        in.close();
        System.out.println("File downloaded");

        TimeUnit.SECONDS.sleep(1);
    }

    private String gotoDeposit(){
        $(By.xpath("//div/div/div/a[@href='/contributions/'][@class='home-nav__title-link']")).click();
        String beforeSum = checkSum();
        System.out.println(beforeSum);
        return beforeSum;
    }

    private String checkSum(){
        return $(By.xpath("//span[@class='js-calc-result js-calc-result-noanim']")).getText().replaceAll(" ", "");
    }

    private void change(String before, String after){
        if(!before.equals(after))
            System.out.println("Изменено");
        else
            System.out.println("Не изменено");
    }

    private void sumTextBox() throws InterruptedException {
        String beforeSum = gotoDeposit();

        $(By.name("amount")).val("125000");
        TimeUnit.SECONDS.sleep(2);

        String afterSum = checkSum();
        System.out.println(afterSum);

        change(beforeSum, afterSum);
        TimeUnit.SECONDS.sleep(1);

    }

    private void moveSlider(ICondition direction, ICondition finish) {
        actions().clickAndHold($(cssBaloon))
                .perform();

        while (!finish.isTrue()) {
            actions().moveByOffset(direction.isTrue() ? 1 : -1, 0)
                    .perform();
        }
        actions().release()
                .perform();
    }

    private void setSliderToAmount(int amount) {
        // Лямбда выражении (Java 8)
        moveSlider( () -> amount > getCurrentAmount(),
                () -> getCurrentAmount() == amount);
    }

    private int getCurrentAmount() {
        return Integer.parseInt($(cssAmountInput).val().replace(" ",""));
    }

    private void sumComboBox() throws InterruptedException {
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

    @BeforeTest
    private void openStatement() throws InterruptedException {
        open("https://rencredit.ru");
       // Assert.assertTrue(title().contains("Ренессанс Кредит"));
    }

   /* @Test
    public void test2_slider() {
        Selenide.open("https://rencredit.ru/contributions/");
        setSliderToAmount(5000000);
        TimeUnit.SECONDS.sleep(2);
    }
*/

    @Test
    public void test1() throws Exception {
        saveLogo();
        gotoInvestorsPage();
        savePDF();
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void test2_textBox() throws Exception {
        sumTextBox();
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void test2_comboBox() throws Exception {
        sumComboBox();
        TimeUnit.SECONDS.sleep(2);
    }
}