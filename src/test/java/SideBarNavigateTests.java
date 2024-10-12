import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
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

public class SideBarNavigateTests {
    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        open("https://demoqa.com/forms");
        Configuration.pageLoadStrategy = "eager";
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");
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
        sleep(2000);
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
        sleep(2000);
    }
    @Tag("SMOKE")
    @ParameterizedTest(name = "При клике на {0} левого сайдбара вкладки Interactions - у нас отображается страница {1}")
    @MethodSource()
    void checkNavigationFromLeftSideBarByInteractions(String menuItem,String expectedHeader) {
        $(xpath("//*[text()='Interactions']")).click();
        $(xpath("//span[text()='" + menuItem + "']")).click();
        $("h1").shouldHave(text(expectedHeader));
        sleep(2000);
    }
    static Stream<Arguments> checkNavigationFromLeftSideBarByInteractions() {
        return Stream.of(
                Arguments.of("Sortable", "Sortable"),
                Arguments.of("Selectable", "Selectable"),
                Arguments.of("Resizable", "Resizable"),
                Arguments.of("Droppable", "Droppable"),
                Arguments.of("Dragabble", "Dragabble")
        );
    }
}
