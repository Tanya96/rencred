import org.openqa.selenium.By;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;


public class Test1 {
    private void openStatement() throws InterruptedException {
        open("https://rencredit.ru");
        AssertJUnit.assertTrue(title().contains("Ренессанс Кредит"));
    }

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

    private void savePDF() throws IOException, InterruptedException{
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

    @Test
    public void test1() throws Exception {
        openStatement();
        saveLogo();
        gotoInvestorsPage();
        savePDF();
    }


}
