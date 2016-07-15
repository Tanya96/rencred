package rencredit.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import rencredit.ICondition;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.actions;

/**
 * Created by andrey on 15.07.2016.
 */
public class GoToDeposit extends BasePage {
    public GoToDeposit(String url) {
        super(url);
    }

    public String gotoDeposit(){
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

    @Step("Шаг. Ввод в текстовое поле")
    public void sumTextBox() throws InterruptedException {
        String beforeSum = gotoDeposit();

        $(By.name("amount")).val("125000");
        TimeUnit.SECONDS.sleep(2);

        String afterSum = checkSum();
        System.out.println(afterSum);

        change(beforeSum, afterSum);
        TimeUnit.SECONDS.sleep(1);

    }

    @Step("Шаг. Изменение comboBox")
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

    String cssBaloon = ".calculator__slide-section div[class*='ui-slider'] span";
    String cssAmountInput = ".calculator__slide-section input[name='amount']";

    @Step("Шаг. Передвинуть ползунок")
    public void setSliderToAmount(int amount) {
        // Лямбда выражении (Java 8)
        System.out.println("start");
        moveSlider( () -> amount > getCurrentAmount(),
                () -> getCurrentAmount() == amount);
        System.out.println("finish");
    }

    public void moveSlider(ICondition direction, ICondition finish) {
        System.out.println("moveslider");
        actions().clickAndHold($(cssBaloon))
                .perform();
        System.out.println("check");
        while (!finish.isTrue()) {
            actions().moveByOffset(direction.isTrue() ? 1 : -1, 0)
                    .perform();
        }
        actions().release()
                .perform();
    }

    public int getCurrentAmount() {
        System.out.println($(cssAmountInput).val().replace(" ",""));
        return Integer.parseInt($(cssAmountInput).val().replace(" ",""));
    }

}
