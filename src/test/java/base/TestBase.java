package base;

import rencredit.pages.*;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Fiatlux on 14.07.2016.
 */
public class TestBase {

    public static final MainPage MAIN_PAGE = new MainPage("https://rencredit.ru");
    public static final AboutPage ABOUT_PAGE = new AboutPage("https://rencredit.ru/about");
    public static final InvestorsPage INVESTORS_PAGE = new InvestorsPage("https://rencredit.ru/investors");
    public static final ReportsPage REPORTS_PAGE = new ReportsPage("https://rencredit.ru/investors/reporting");
    public static final SavePDFPage SAVE_PDF_PAGE = new SavePDFPage("https://rencredit.ru/investors/reporting/otchetnost-banka-po-msfo/otchetnost-banka/");

    public static final GoToLoans GO_TO_LOANS = new GoToLoans("https://rencredit.ru/loans/");

    @Attachment(value = "{0}")
    public byte[] attachToAllure(String fileName, File file) {
        return getFileBytes(file);
    }

    private static byte[] getFileBytes(File file)
    {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException ex) {
            return String.format("IOException was occured in Attacher class! Throwable: %s", ex.getMessage()).getBytes();
        }
    }
}
