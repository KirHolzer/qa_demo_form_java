import com.fasterxml.jackson.databind.ObjectMapper;
import model.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonFileTest {

    @Test
    @Tag("JSON")
    @DisplayName("Проверка чтения и содержания JSON файла с информацией о студенте")
    void testStudentJson() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("StudentExample.json")) {
            assertNotNull(inputStream, "Файл не найден в ресурсах");
            ObjectMapper objectMapper = new ObjectMapper();
            Student student = objectMapper.readValue(inputStream, Student.class);

            Assertions.assertEquals("Kirill Samusenko", student.getStudentName());
            Assertions.assertEquals(21, student.getAge());
            assertTrue(student.isStudentIsActive());
            assertNotNull(student.getSubjects());
            Assertions.assertEquals(3, student.getSubjects().size());
            assertTrue(student.getSubjects().contains("Mathematics"));
            assertTrue(student.getSubjects().contains("Physics"));
            assertTrue(student.getSubjects().contains("Computer Science"));
        }

    }
}
