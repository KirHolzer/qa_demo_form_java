import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.By.xpath;

public class SideBarNavigateTests extends BaseTest {
    @BeforeEach
    void beforeEach() {
        open("/forms");
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");
    }
    @AfterEach
    void afterEach() {
        Selenide.closeWebDriver();
    }


    @ParameterizedTest(name = "При клике на {0} левого сайдбара вкладки Elements - у нас отображается страница {1}")
    @CsvSource({
            "Text Box, Text Box",
            "Check Box, Check Box",
            "Radio Button, Radio Button",
            "Web Tables, Web Tables",
            "Links, Links",
            "Broken Links - Images, Broken Links - Images",
            "Upload and Download, Upload and Download",
            "Dynamic Properties, Dynamic Properties"
    })
    @Tag("SMOKE")
    void checkNavigationFromLeftSideBarByElements(String menuItem, String expectedHeader) {
        $(xpath("//*[text()='Elements']")).click();
        $(xpath("//span[text()='" + menuItem + "']")).click();
        $("h1").shouldHave(text(expectedHeader));
    }
    @Tag("SMOKE")
    @ParameterizedTest(name = "При клике на {0} левого сайдбара вкладки Widgets - у нас отображается страница {0}")
    @ValueSource(strings = {
            "Accordian", "Auto Complete", "Date Picker", "Slider",
            "Progress Bar", "Tabs", "Tool Tips", "Menu", "Select Menu"
    })
    void checkNavigationFromLeftSideBarByWidgets(String menuItem) {
        $(xpath("//*[text()='Widgets']")).click();
        $(xpath("//span[text()='" + menuItem + "']")).click();
        $("h1").shouldHave(text(menuItem));
    }
    @Tag("SMOKE")
    @ParameterizedTest(name = "При клике на {0} левого сайдбара вкладки Interactions - у нас отображается страница {1}")
    @MethodSource("provideInteractionsData")
    void checkNavigationFromLeftSideBarByInteractions(String menuItem,String expectedHeader) {
        $(xpath("//*[text()='Interactions']")).click();
        $(xpath("//span[text()='" + menuItem + "']")).click();
        $("h1").shouldHave(text(expectedHeader));
    }
    static Stream<Arguments> provideInteractionsData(){
        return Stream.of(
                Arguments.of("Sortable", "Sortable"),
                Arguments.of("Selectable", "Selectable"),
                Arguments.of("Resizable", "Resizable"),
                Arguments.of("Droppable", "Droppable"),
                Arguments.of("Dragabble", "Dragabble")
        );
    }
}
