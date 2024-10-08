import org.junit.jupiter.api.Test;
import pages.DemoqaFormPage;

public class FormTests extends BaseTest {
    private final DemoqaFormPage demoqaFormPage = new DemoqaFormPage();
    String firstName = "Kirill",
            lastName = "Goltser",
            userEmail = "kirillTest@yandex.ru",
            userGender = "Male",
            userNumber = "8999111111",
            negativeUserNumber = "8900$&*ASd",
            dayOfBirth = "8",
            monthOfBirth = "April",
            yearOfBirth = "1993",
            subjects = "History",
            hobbies = "Reading",
            pictureName = "testPic.jpeg",
            userAddress = "Krasnodar st. Karasunskaya 60",
            userState = "NCR",
            userCity = "Delhi";


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
