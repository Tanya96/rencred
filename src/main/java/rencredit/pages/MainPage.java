package rencredit.pages;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.title;
import static org.testng.Assert.fail;

/**
 * Created by Fiatlux on 14.07.2016.
 */
public class MainPage extends BasePage {

    private static final By LOGO = By.cssSelector("img.logo__image");
    public String title;
    public MainPage(String url){
        super(url);
        title = title();
    }

    @Step("Шаг. Проверка подлинности")
    public void authentication(){
        if(!title.equals("Банк «Ренессанс Кредит»"))
        {
            fail("Ошибка проверки");
        }
    }

    @Step("Шаг. Сохранение лого")
    public File saveLogo() {
        File savedLogo;

        try {
            java.net.URL url = null;
            url = new URL($(LOGO).getAttribute("src"));
            BufferedImage bufferedImage = ImageIO.read(url);
            savedLogo = new File("target/logo.png");
            ImageIO.write(bufferedImage, "png", savedLogo);

        } catch (IOException e) {
            fail("Ошибка сохранения Logo: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return savedLogo;
    }
}
