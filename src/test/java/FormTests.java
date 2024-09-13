import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class FormTests {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        // Configuration.holdBrowserOpen = true;
    }

    @Test
    void checkTestForm() {
        //Действия
        open("/automation-practice-form");
        // заполнение формы
        $("#firstName").setValue("Kirill");  // имя
        $("#lastName").setValue("Goltser"); // фамилия
        $("#userEmail").setValue("kirillTest@yandex.ru"); // email
        $("#userNumber").setValue("8999111111"); // номер телефона
        $(byText("Male")).click(); // пол мужской
        $("#dateOfBirthInput").click(); // открыть календарь
        $(".react-datepicker__month-select").click();
        $(byText("April")).click(); // месяц
        $(".react-datepicker__year-select").click();
        $(byText("1993")).click(); // год
        $(".react-datepicker__month").click();
        $(byText("8")).click(); // дата
        $("#dateOfBirthInput").pressEnter();
        $("#subjectsInput").setValue("History").pressEnter(); // предмет
        $(byText("Reading")).click(); //хобби -чтение
        $("#uploadPicture").uploadFromClasspath("testPic.jpeg"); // загрузка картинки
        $("#currentAddress").setValue("Krasnodar st. Karasunskaya 60");
        $("#react-select-3-input").setValue("NCR").pressEnter(); // стейт
        $("#react-select-4-input").setValue("Del").pressEnter(); // город
        $("#submit").click();

        // Проверки
        $(".table-responsive").shouldHave(text("Kirill Goltser"));
        $(".table-responsive").shouldHave(text("kirillTest@yandex.ru"));
        $(".table-responsive").shouldHave(text("Male"));
        $(".table-responsive").shouldHave(text("8999111111"));
        $(".table-responsive").shouldHave(text("08 April,1993"));
        $(".table-responsive").shouldHave(text("History"));
        $(".table-responsive").shouldHave(text("Reading"));
        $(".table-responsive").shouldHave(text("testPic.jpeg"));
        $(".table-responsive").shouldHave(text("Krasnodar st. Karasunskaya 60"));
        $(".table-responsive").shouldHave(text("NCR Delhi"));
    }
}
