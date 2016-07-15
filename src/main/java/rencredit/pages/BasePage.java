package rencredit.pages;

import static com.codeborne.selenide.Selenide.open;

/**
 * Created by andrey on 15.07.2016.
 */
public class BasePage {

    public BasePage(String url) {
       open(url);
    }
}
