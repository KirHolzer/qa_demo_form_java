import org.junit.jupiter.api.Test;
import pages.DemoqaFormPage;
import utils.DataGenerator;

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
    void checkFullFormTest() {
        demoqaFormPage.openPage()
                .removeBanner()
                .setFirstName(firstName)  // имя
                .setLastName(lastName) // фамилия
                .setEmail(userEmail) // email
                .setGender(userGender) // пол мужской
                .setUserNumber(userNumber) // номер телефона
                .setBirthDay(dayOfBirth, monthOfBirth, yearOfBirth) // дата рождения в календаре
                .setSubject(subjects) // предмет
                .setHobby(hobbies) // хобби
                .uploadPicture(pictureName) // загрузка картинки
                .setUserCurrentAddress(userAddress) // адресс юзера
                .setUserState(userState) // Штат юзера
                .setUserCity(userCity) // город
                .clickSubmit(); // загрузка картинки
        //Selenide.sleep(10000);
        // Проверки
        demoqaFormPage.checkSuccessResult("Student Name", firstName + " " + lastName)
                .checkSuccessResult ("Student Email", userEmail)
                .checkSuccessResult ("Gender", userGender)
                .checkSuccessResult ("Mobile", userNumber)
                .checkSuccessResult ("Date of Birth", dayOfBirth + " " + monthOfBirth + "," + yearOfBirth)
                .checkSuccessResult ("Subjects", subjects)
                .checkSuccessResult ("Hobbies", hobbies)
                .checkSuccessResult ("Picture", pictureName)
                .checkSuccessResult ("Address", userAddress)
                .checkSuccessResult ("State and City", userState + " " + userCity);
    }

    @Test
    void checkRequiredFormTest(){
        demoqaFormPage.openPage()
                .removeBanner()
                .setFirstName(firstName)
                .setLastName(lastName)
                .setUserNumber(userNumber)
                .setGender(userGender)
                .setBirthDay(dayOfBirth, monthOfBirth, yearOfBirth)
                .clickSubmit();

        demoqaFormPage.checkSuccessResult("Student Name", firstName + " " + lastName)
                .checkSuccessResult ("Gender", userGender)
                .checkSuccessResult ("Mobile", userNumber)
                .checkSuccessResult ("Date of Birth", dayOfBirth + " " + monthOfBirth + "," + yearOfBirth);

    }

    @Test
    void NegativeFormTest() {
        demoqaFormPage.openPage()
                .removeBanner()
                .setUserNumber(negativeUserNumber)
                .clickSubmit();

        demoqaFormPage.checkNegativeResult();
    }
}
