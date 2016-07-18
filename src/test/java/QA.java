import com.codeborne.selenide.Selenide;
import listeners.MyTestListener;
import org.testng.annotations.*;

import static base.TestBase.GO_TO_LOANS;
import static base.TestBase.MAIN_PAGE;
import static base.TestBase.SAVE_PDF_PAGE;
import static listeners.MyClassListener.*;

public class QA {

    @Test
    @Listeners(MyTestListener.class)
    public class testik1{
        @BeforeClass
        public void beforeClass() {

            onBeforeClass(this.getClass().getCanonicalName());
        }
        @AfterClass
        public void afterClass() {
            onAfterClass();
        }

        @Test
        public void test1_check() {
            MAIN_PAGE.authentication();
        }

        @Test
        public void test1_logo() throws Exception {
            MAIN_PAGE.saveLogo();
            SAVE_PDF_PAGE.savePDF();
        }

        @Test
        public void test1_PDF() throws Exception {
            SAVE_PDF_PAGE.savePDF();
        }
    }

    @Test
    @Listeners(MyTestListener.class)
    public class testik2 {
        @BeforeClass
        public void beforeClass() {

            onBeforeClass(this.getClass().getCanonicalName());
        }
        @AfterClass
        public void afterClass() {
            onAfterClass();
        }
        @Test
        public void test2_textBox() throws Exception {
            GO_TO_LOANS.sumTextBox();
        }

        @Test
        public void test2_slider() {
            GO_TO_LOANS.setSliderToAmount(70000);
            Selenide.sleep(2);
        }

        @Test
        public void test2_comboBox() throws Exception {
            GO_TO_LOANS.sumComboBox();
        }
    }
}