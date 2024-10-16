import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import stepsForIssue.WebTestSteps;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;


@Feature("Issue в репозитории")
@Story("Проверка наличия Issue")
@Owner("Kirill Goltser")
public class IssueTest extends BaseTest {
    @BeforeEach
    public void setupAllureListener() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }


    private static final String REPOSITORY = "KirHolzer/qa_demo_form_java";
    private static final String LINK_GITHUB= "https://github.com";



    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Тест без описания шагов в Allure")
    public void issueSearchSelenideTest() {

        open(LINK_GITHUB);

        $(".header-search-button").click();
        $("#query-builder-test").setValue(REPOSITORY).pressEnter();
        $(linkText(REPOSITORY)).click();
        $("#issues-tab").shouldHave(text("Issues"));
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "Test page", url = "https://github.com/KirHolzer/qa_demo_form_java")
    @DisplayName("Проверка наличия Issue в репозитории с помощью lambda")
    public void lambdaStepTestForIssue() {

        step("Открываем страницу GitHub-a по адресу {LINK_GITHUB}}", () ->
                open(LINK_GITHUB));

        step("Кликаем на верхний инпут", () ->
                $(".header-search-button").click());

        step("Вводим значение {REPOSITORY} и жмем клавишу enter", () ->
                $("#query-builder-test").setValue(REPOSITORY).pressEnter());

        step("Кликаем мышкой по ссылке {REPOSITORY}", () ->
                $(linkText(REPOSITORY)).click());
        step("Проверяем что у нас есть вкладка Issues ", () ->
                $("#issues-tab").shouldHave(text("Issues")));

    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Link(value = "Test page", url = "https://github.com/KirHolzer/qa_demo_form_java")
    @DisplayName("Проверка наличия Issue в репозитории с помощью шагов анатаций")
    public void annotatedStepTestForIssue() {
        WebTestSteps step = new WebTestSteps();

        step.openMainPage(LINK_GITHUB);
        step.setVelueInHeaderInput(REPOSITORY);
        step.searchRepository(REPOSITORY);
        step.clickOnLinkRepo(REPOSITORY);
        step.checkIssueOnPanel();
    }
}
