import com.codeborne.pdftest.PDF;
import com.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipFile;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

public class ZipFilesParsingTest {
    private ClassLoader classLoader = ZipFilesParsingTest.class.getClassLoader();
    private final String zipFileName = "sampleForTest.zip";

    @Test
    @Tag("SMOKE")
    void checkContentInZip() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(classLoader.getResourceAsStream(zipFileName))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                System.out.println(entry.getName());
            }
        }
    }

    @Test
    @Tag("SOKE")
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
                System.out.println(cell);
                actualHeaders[i] = (cell != null) ? cell.getStringCellValue() : "";
            }
            assertThat(actualHeaders).containsExactly(expectedHeaders);
        }
    }

}



