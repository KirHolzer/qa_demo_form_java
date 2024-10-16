import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.DemoqaFormPage;
import utils.DataGenerator;
import static io.qameta.allure.Allure.step;

@Feature("Заполнение формы")
@Story("Проверка модального окна на корректность введенных данных")
@Owner("Kirill Goltser")
//@Tag("Form tests")
public class FormTests extends BaseTest {
    private final DemoqaFormPage demoqaFormPage = new DemoqaFormPage();
    private final DataGenerator randomData = new DataGenerator();
    String firstName = randomData.generateFirstName(),
            lastName = randomData.generateLastName(),
            userEmail =randomData.generateUserEmail(),
            userGender = randomData.selectRandomGender(),
            userNumber = randomData.generateRandomPhoneNumber(),
            negativeUserNumber = "8900$&*ASd",
            dayOfBirth = randomData.dayOfBirth(),
            monthOfBirth = randomData.monthOfBirth(),
            yearOfBirth = randomData.generateYearOfBirth(),
            subjects = randomData.subjects(),
            hobbies = randomData.hobbies(),
            pictureName = "testPic.jpeg",
            userAddress = randomData.address(),
            userState = randomData.state(),
            userCity = randomData.city(userState);


    @Test
    @Tag("SMOKE")
    @DisplayName("Проверка полного заполнения формы")
    void checkFullFormTest() {
        step("Открыть форму", () -> {
            demoqaFormPage.openPage()
                    .removeBanner();
        });
        step("Дополнение firstName в форму", () -> {
            demoqaFormPage
                    .setFirstName(firstName);
        });
        step("Дополнение lastName в форму", () -> {
            demoqaFormPage
                    .setLastName(lastName);
        });
        step("Дополнение userEmail в форму", () -> {
            demoqaFormPage
                    .setEmail(userEmail);
        });
        step("Дополнение userGender в форму", () -> {
            demoqaFormPage
                    .setGender(userGender);
        });
        step("Дополнение userNumber в форму", () -> {
            demoqaFormPage
                    .setUserNumber(userNumber);
        });
        step("Дополнение dayOfBirth, monthOfBirth, yearOfBirth в форму", () -> {
            demoqaFormPage
                    .setBirthDay(dayOfBirth, monthOfBirth, yearOfBirth);
        });
        step("Дополнение subjects в форму", () -> {
            demoqaFormPage
                    .setSubject(subjects);
        });
        step("Дополнение hobbies в форму", () -> {
            demoqaFormPage
                    .setHobby(hobbies);
        });
        step("Дополнение pictureName в форму", () -> {
            demoqaFormPage
                    .uploadPicture(pictureName);
        });
        step("Дополнение userAddress в форму", () -> {
            demoqaFormPage
                    .setUserCurrentAddress(userAddress);
        });
        step("Дополнение userState в форму", () -> {
            demoqaFormPage
                    .setUserState(userState);
        });
        step("Дополнение userCity в форму", () -> {
            demoqaFormPage
                    .setUserCity(userCity);
        });
        step("Нажать кнопку Submit", () -> {
            demoqaFormPage
                    .clickSubmit();
        });


        step("Проверка заполненной формы всех полей", () ->
                demoqaFormPage.checkSuccessResult("Student Name", firstName + " " + lastName)
                        .checkSuccessResult ("Student Email", userEmail)
                        .checkSuccessResult ("Gender", userGender)
                        .checkSuccessResult ("Mobile", userNumber)
                        .checkSuccessResult ("Date of Birth", dayOfBirth + " " + monthOfBirth + "," + yearOfBirth)
                        .checkSuccessResult ("Subjects", subjects)
                        .checkSuccessResult ("Hobbies", hobbies)
                        .checkSuccessResult ("Picture", pictureName)
                        .checkSuccessResult ("Address", userAddress)
                        .checkSuccessResult ("State and City", userState + " " + userCity));
    }

    @Test
    @Tag("SMOKE")
    @DisplayName("Проверка заполнения формы обязательных полей")
    void checkRequiredFormTest(){
        step("Открыть форму", () -> {
            demoqaFormPage.openPage()
                    .removeBanner();
        });
        step("Дополнение firstName в форму", () -> {
            demoqaFormPage
                    .setFirstName(firstName);
        });
        step("Дополнение lastName в форму", () -> {
            demoqaFormPage
                    .setLastName(lastName);
        });
        step("Дополнение userEmail в форму", () -> {
            demoqaFormPage
                    .setEmail(userEmail);
        });
        step("Дополнение userGender в форму", () -> {
            demoqaFormPage
                    .setGender(userGender);
        });
        step("Дополнение userNumber в форму", () -> {
            demoqaFormPage
                    .setUserNumber(userNumber);
        });
        step("Дополнение dayOfBirth, monthOfBirth, yearOfBirth в форму", () -> {
            demoqaFormPage
                    .setBirthDay(dayOfBirth, monthOfBirth, yearOfBirth);
        });
        step("Нажать кнопку Submit", () -> {
            demoqaFormPage
                    .clickSubmit();
        });


        step("Проверка заполненной формы всех обязательных полей", () -> {
            demoqaFormPage.checkSuccessResult("Student Name", firstName + " " + lastName)
                    .checkSuccessResult ("Gender", userGender)
                    .checkSuccessResult ("Mobile", userNumber)
                    .checkSuccessResult ("Date of Birth", dayOfBirth + " " + monthOfBirth + "," + yearOfBirth);
        });

    }

    @Test
    @Tag("SMOKE")
    @Disabled("Пока не нужно проверять негативные тесты на номер телефона")
    @DisplayName("Негативная проверка ввода данных в поле номера телефона")
    void NegativeFormTest() {
        demoqaFormPage.openPage()
                .removeBanner()
                .setUserNumber(negativeUserNumber)
                .clickSubmit();

        demoqaFormPage.checkNegativeResult();
    }
}
