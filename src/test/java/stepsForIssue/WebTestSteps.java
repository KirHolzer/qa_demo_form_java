package stepsForIssue;

import io.qameta.allure.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.linkText;


public class WebTestSteps {
    @Step("Открываем главную страницу {link}")
    public void openMainPage(String link) {
        open(link);
    }

    @Step("Вводим значение {repo} и жмем клавишу enter")
    public void setVelueInHeaderInput(String repo){
        $(".header-search-button").click();
        $("#query-builder-test").setValue(repo).pressEnter();
    }
    @Step("Вводим значение {repo} и жмем клавишу enter")
    public void searchRepository(String repo){
        $("#query-builder-test").setValue(repo).pressEnter();
    }

    @Step("Кликаем мышкой по ссылке {repo}")
    public void clickOnLinkRepo(String repo){
        $(linkText(repo)).click();
    }
    @Step("Проверяем что у нас есть вкладка Issues ")
    public void checkIssueOnPanel() {
        $("#issues-tab").shouldHave(text("Issues"));
    }
}
