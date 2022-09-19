import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import object.Teacher;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipFile;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

public class FileParserTest {

    private ClassLoader classLoader = FileParserTest.class.getClassLoader();

    private String archiveName = "testZipFile.zip";
    private String xlsFileName = "exampleXLS.xls";
    private String pdfFileName = "samplePDF.pdf";
    private String csvFileName = "sampleCSV.csv";
    private String jsonFile = "teacher.json";

    @Test
    void parsePdfFromZipTest() throws Exception {
        try (InputStream pdfFileStream = getFileFromArchive(pdfFileName)) {
            PDF pdf = new PDF(pdfFileStream);
            assertThat(pdf.numberOfPages).isEqualTo(2);
            assertThat(pdf.text).containsAnyOf("A Simple PDF File");
        }
    }

    @Test
    void parseCsvFromZipTest() throws Exception {
        try (InputStream csvFileStream = getFileFromArchive(csvFileName)) {
            CSVReader csvReader = new CSVReader(new InputStreamReader(csvFileStream, UTF_8));
            List<String[]> csv = csvReader.readAll();
            assertThat(csv).contains(new String[]{"Ivan", "Petrov", "33", "Moscow"});

        }
    }

    @Test
    void parseXlsFromZipTest() throws Exception {
        try (InputStream xlsFileStream = getFileFromArchive(xlsFileName)) {
            XLS xls = new XLS(xlsFileStream);
            assertThat(xls.excel.getSheetAt(0).getRow(13).getCell(6).getStringCellValue()).contains("16/08/2016");
            assertThat(xls.excel.getSheetAt(0).getRow(13).getCell(0).getNumericCellValue()).isEqualTo(13);
            assertThat(xls.excel.getSheetAt(0).getRow(13).getCell(1).getStringCellValue()).contains("Sherron");
            assertThat(xls.excel.getSheetAt(0).getRow(13).getCell(2).getStringCellValue()).contains("Ascencio");
            assertThat(xls.excel.getSheetAt(0).getRow(13).getCell(3).getStringCellValue()).contains("Female");
            assertThat(xls.excel.getSheetAt(0).getRow(13).getCell(4).getStringCellValue()).contains("Great Britain");
            assertThat((xls.excel.getSheetAt(0).getRow(13).getCell(5).getNumericCellValue())).isEqualTo(32.0);
            assertThat((xls.excel.getSheetAt(0).getRow(13).getCell(7).getNumericCellValue())).isEqualTo(3256.0);
            assertThat(xls.excel.getSheetAt(0).getPhysicalNumberOfRows()).isEqualTo(101);
        }
    }

    @Test
    void parseJsonTest() throws IOException {
        try (InputStream is = classLoader.getResourceAsStream(jsonFile)) {
            ObjectMapper mapper = new ObjectMapper();
            Teacher teacher = mapper.readValue(is, Teacher.class);
            assertThat(teacher.getName()).isEqualTo("Olga Seeva");
            assertThat(teacher.getAge()).isEqualTo(38);
            assertThat(teacher.getGender()).isEqualTo("woman");
            assertThat(teacher.getWorkExperience()).isEqualTo(10);
            assertThat(teacher.getSubjects()).isEqualTo(new String[]{"russian language", "literature"});
        }
    }

    private InputStream getFileFromArchive(String fileName) throws Exception {
        File zipFile = new File("src/test/resources/" + archiveName);
        ZipFile zip = new ZipFile(zipFile);
        return zip.getInputStream(zip.getEntry(fileName));
    }
}
