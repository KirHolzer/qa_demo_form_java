import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class FormTests {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        // Configuration.holdBrowserOpen = true;
    }

    @Test
    void checkFormTest() {
        //Действия
        open("/automation-practice-form");
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");
        // заполнение формы
        $("#firstName").setValue("Kirill");  // имя
        $("#lastName").setValue("Goltser"); // фамилия
        $("#userEmail").setValue("kirillTest@yandex.ru"); // email
        $("#userNumber").setValue("8999111111"); // номер телефона
        $("label[for='gender-radio-1']").click(); // пол мужской
        $("#dateOfBirthInput").click(); // открыть календарь
        $(".react-datepicker__month-select").click();
        $(".react-datepicker__month-select").find("option[value='3']").click(); // месяц
        $(".react-datepicker__year-select").find("option[value='1993']").click();
        $(".react-datepicker__month").click();
        $(".react-datepicker__day--008").click();  // дата
        $("#dateOfBirthInput").pressEnter();
        $("#subjectsInput").setValue("History").pressEnter(); // предмет
        $("[for=hobbies-checkbox-2]").click(); //хобби -чтение
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
