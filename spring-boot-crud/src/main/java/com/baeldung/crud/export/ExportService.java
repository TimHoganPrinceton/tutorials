package com.baeldung.crud.export;

import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class ExportService {

    @Value("${export.directory:#{null}}")
    private String exportDirectoryName;

    @Autowired
    DataSource dataSource;

    public void exportList() throws Exception {
        if (exportDirectoryName == null) {
            log.error("export.directory property not defined. Cannot export list.");
            return;
        }
        log.info("output directory is '{}'", exportDirectoryName);

        String tableName = "demo_user";
        String queryString = "select * from " + tableName;
        String fileName = determineFileName("user-list");
        Path outputPath = Paths.get(exportDirectoryName, fileName);
        log.info("Extracting for table {}   file {}   using query <{}>", tableName, outputPath, queryString);
        extractToFile(queryString, outputPath);
    }

    private String determineFileName(String prefix) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");
        String dateString = "T" + df.format(new Date());
        return prefix + "." + dateString + ".csv";
    }

    private void extractToFile(String queryString, Path outputPath) throws Exception {
        try (
                Writer fileWriter = new OutputStreamWriter(new FileOutputStream(outputPath.toFile()), StandardCharsets.UTF_8);
                CSVWriter writer = new CSVWriter(fileWriter);
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(queryString);
                ResultSet rs = stmt.executeQuery();
        ) {
            writer.writeAll(rs, true);
        } catch (Exception e) {
            //if we have a problem creating the extract, delete the file
            Files.deleteIfExists(outputPath);
            throw e;
        }
    }
}
