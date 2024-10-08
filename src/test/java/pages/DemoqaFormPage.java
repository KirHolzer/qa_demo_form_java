package pages;

import com.codeborne.selenide.SelenideElement;
import pages.components.CalendarComponent;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DemoqaFormPage {

    private final SelenideElement
            firstNameInput = $("#firstName"),
            lastNameInput = $("#lastName"),
            emailInput = $("#userEmail"),
            genderWrapper = $("#genterWrapper"),
            userNumberInput = $("#userNumber"),
            calendarInput = $("#dateOfBirthInput"),
            userSubjectInput = $("#subjectsInput"),
            hobbiesWrapper = $("#hobbiesWrapper"),
            uploadPicture = $("#uploadPicture"),
            currentAddressInput = $("#currentAddress"),
            userStateInput = $("#react-select-3-input"),
            userCityInput = $("#react-select-4-input"),
            submitInput = $("#submit");

    public DemoqaFormPage openPage() {
        open("/automation-practice-form");
        return this;
    }

    public DemoqaFormPage removeBanner() {
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");
        return this;
    }

    public DemoqaFormPage setFirstName(String value) {
        firstNameInput.setValue(value);
        return this;
    }

    public DemoqaFormPage setLastName(String value) {
        lastNameInput.setValue(value);
        return this;
    }

    public DemoqaFormPage setEmail(String value) {
        emailInput.setValue(value);
        return this;
    }

    public DemoqaFormPage setGender(String value) {
        genderWrapper.$(byText(value)).click();
        return this;
    }

    public DemoqaFormPage setUserNumber(String value) {
        userNumberInput.setValue(value);
        return this;
    }

    public DemoqaFormPage setBirthDay(String day, String month, String year) {
        calendarInput.click();
        new CalendarComponent().setDate(day, month, year);
        return this;
    }

    public DemoqaFormPage setSubject(String value) {
        userSubjectInput.setValue(value).pressEnter();
        return this;
    }

    public DemoqaFormPage setHobby(String value) {
        hobbiesWrapper.$(byText(value)).click();
        return this;
    }

    public DemoqaFormPage uploadPicture(String value) {
        uploadPicture.uploadFromClasspath(value);
        return this;
    }

    public DemoqaFormPage setCurrentAddress(String value) {
        currentAddressInput.setValue(value);
        return this;
    }

    public DemoqaFormPage setUserState(String value) {
        userStateInput.setValue(value).pressEnter();
        return this;
    }

    public DemoqaFormPage setUserCity(String value) {
        userCityInput.setValue(value).pressEnter();
        return this;
    }

    public void clickSubmit () {
        submitInput.click();
    }


}
