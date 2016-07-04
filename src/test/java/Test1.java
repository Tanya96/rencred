import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;



public class Test1 {

    private void gotoInvestorsPage()throws InterruptedException {
        $(By.linkText("О банке")).click();
        $(By.linkText("Инвесторам")).click();
        $(By.linkText("Отчетность")).click();
        $(By.linkText("Отчетность банка")).click();
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
        $(By.linkText("Вклады")).click();
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

    private void sumSlider() throws InterruptedException {

        String beforeSum = gotoDeposit();

        WebElement slider = $(By.xpath("//div[@data-property='amount']/span[contains(@class, 'ui-slider-handle ui-state-default ui-corner-all')]"));
//        int width = slider.getSize().getWidth();
//        Actions actions = new Actions(getWebDriver());
//        actions.dragAndDropBy(slider, 2, 0).perform();
       // System.out.println(width);

        Actions move = new Actions(getWebDriver());
        move.moveToElement(slider, (100), 0).click();
        move.build().perform();
        System.out.println("работает");

       // move.clickAndHold(slider).moveByOffset(20, 0).release().perform();
       // actions.dragAndDropBy(slider, 2, 0).perform();

        TimeUnit.SECONDS.sleep(3);
        String afterSum = checkSum();
        System.out.println(afterSum);

        change(beforeSum, afterSum);
        TimeUnit.SECONDS.sleep(1);
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
        Assert.assertTrue(title().contains("Ренессанс Кредит"));
    }

    @Test
    public void test1() throws Exception {
        System.out.println("Тест 1");
        saveLogo();
        gotoInvestorsPage();
        savePDF();
    }

    @Test
    public void textBox() throws Exception {
        System.out.println("Изменение суммы вклада через поле ввода");
        sumTextBox();
    }

    @Test
    public void comboBox() throws Exception {
        System.out.println("Изменение срока вклада через выпадающий список");
        sumComboBox();
    }

}
