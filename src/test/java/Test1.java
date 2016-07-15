import com.codeborne.selenide.Selenide;
import listeners.MyTestListener;
import org.testng.annotations.*;

import static base.TestBase.GO_TO_DEPOSIT;
import static base.TestBase.MAIN_PAGE;
import static base.TestBase.SAVE_PDF_PAGE;

@Listeners(MyTestListener.class)
public class Test1 {


    @Test
    public void test1_check(){
        MAIN_PAGE.authentication();
    }

    @Test
    public void test1_logo() throws Exception{
        MAIN_PAGE.saveLogo();
    }

    @Test
    public void test1_PDF() throws Exception {
        SAVE_PDF_PAGE.savePDF();
    }

    @Test
    public void test2_textBox() throws Exception {
        GO_TO_DEPOSIT.sumTextBox();
    }
     @Test
    public void test2_slider() {
        GO_TO_DEPOSIT.setSliderToAmount(1000000);
        Selenide.sleep(2);
    }

    @Test
    public void test2_comboBox() throws Exception {
        GO_TO_DEPOSIT.sumComboBox();
    }
}