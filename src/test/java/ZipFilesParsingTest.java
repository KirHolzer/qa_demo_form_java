import com.codeborne.pdftest.PDF;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import model.Student;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipFile;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

public class ZipFilesParsingTest {
    private ClassLoader classLoader = ZipFilesParsingTest.class.getClassLoader();
    private final String zipFileName = "sampleForTest.zip";
    @Test
    @Tag("JSON")
    @DisplayName("Проверка чтения и содержания JSON файла с информацией о студенте - проверка значений у ключей")
    void testStudentJson() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("StudentExample.json")) {
            assertNotNull(inputStream, "Файл не найден в ресурсах");
            ObjectMapper objectMapper = new ObjectMapper();
            Student student = objectMapper.readValue(inputStream, Student.class);

            Assertions.assertEquals("Kirill Samusenko", student.getStudentName());
            Assertions.assertEquals(21, student.getAge());
            assertTrue(student.isStudentIsActive());
            // проверки на массив предметов
            assertNotNull(student.getSubjects());
            Assertions.assertEquals(3, student.getSubjects().size()); // проверяем что три предмета
            assertTrue(student.getSubjects().contains("Mathematics"));
            assertTrue(student.getSubjects().contains("Physics"));
            assertTrue(student.getSubjects().contains("Computer Science"));
        }

    }
    @Test
    @DisplayName("проверка zip архива на наличие в нем example.csv table.xlsx sample.pdf файлов")
    @Tag("SMOKE")
    void checkContentInZip() throws Exception {
        Set<String> expectedFiles = new HashSet<>(Arrays.asList("example.csv", "table.xlsx", "sample.pdf"));
        Set<String> actualFiles = new HashSet<>();

        try (ZipInputStream zis = new ZipInputStream(classLoader.getResourceAsStream(zipFileName))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                actualFiles.add(entry.getName());
            }
        }
        assertTrue(actualFiles.containsAll(expectedFiles),
                "Не все ожидаемые файлы присутствуют в ZIP-архиве: " + expectedFiles);
    }

    @Test
    @DisplayName("проверяем что внутри pdf есть искомая строка")
    @Tag("SMOKE")
    void checkContentInPdf() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(classLoader.getResourceAsStream(zipFileName))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".pdf")) {
                    PDF pdf = new PDF(zis);
                    assertTrue(pdf.text.contains("PDF Form Example"),
                            "Строка 'PDF Form Example' не найдена в PDF файле");
                }
            }
        }
    }

    @Test
    @DisplayName("проверяем что внутри Csv есть корректные название столбцов")
    @Tag("SMOKE")
    void checkContentInCsv() throws Exception {
        ZipFile zipFile = new ZipFile(new File(Objects.requireNonNull(classLoader
                .getResource("sampleForTest.zip")).toURI()));
        ZipEntry csvEntry = zipFile.getEntry("example.csv");

        assertThat(csvEntry).isNotNull();

        try (InputStream stream = zipFile.getInputStream(csvEntry);
             CSVReader reader = new CSVReader(new InputStreamReader(stream))) {

            String[] headers = reader.readNext();
            assertThat(headers)
                    .containsExactly("Name", "Team", "Position", "Height(inches)", "Weight(lbs)", "Age");
        }
    }

    @Test
    @DisplayName("проверяем что внутри Xlsx есть корректные название столбцов")
    @Tag("SMOKE")
    void checkContentInXlsx() throws Exception {
        ZipFile zipFile = new ZipFile(new File(Objects.requireNonNull(classLoader
                .getResource("sampleForTest.zip")).toURI()));
        ZipEntry xlsxEntry = zipFile.getEntry("table.xlsx");

        String[] expectedHeaders = new String[]{
                "Title", "Identifier", "Date", "Subject",
                "Description", "Notes", "Creator",
                "Accession", "Accession No", "Reproduction"
        };
        String[] actualHeaders = new String[expectedHeaders.length];

        try (InputStream stream = zipFile.getInputStream(xlsxEntry)) {
            XSSFWorkbook workbook = new XSSFWorkbook(stream);
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);

            for (int i = 0; i < expectedHeaders.length; i++) {
                Cell cell = headerRow.getCell(i);
                actualHeaders[i] = (cell != null) ? cell.getStringCellValue() : "";
            }
            assertThat(actualHeaders).containsExactly(expectedHeaders);
        }
    }

}
