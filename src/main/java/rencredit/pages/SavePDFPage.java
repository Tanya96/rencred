package rencredit.pages;

import ru.yandex.qatools.allure.annotations.Step;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import static org.testng.Assert.fail;

/**
 * Created by andrey on 15.07.2016.
 */
public class SavePDFPage extends BasePage {
    private static final String PDF = "https://rencredit.ru/upload/iblock/5dc/msfo_otchetnost_renkredit_2015_1a.pdf";

    public SavePDFPage(String url) {
        super(url);
    }

    @Step("Шаг. Сохранение лого")
    public File savePDF() {
        File savedPDF;

        try {
            java.net.URL url = null;
            url = new URL(PDF);
            InputStream in = url.openStream();
            savedPDF = new File("target/pdfFile.pdf");
            FileOutputStream fos = new FileOutputStream(savedPDF);

            System.out.println("Reading from resource and writing to file");
            int length;
            byte[] buffer = new byte[1024];
            while ((length = in.read(buffer)) > -1)
                fos.write(buffer, 0, length);
            fos.close();
            in.close();
            System.out.println("File downloaded");

        } catch (IOException e) {
            fail("Ошибка сохранения PDF: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        return savedPDF;
    }
}
