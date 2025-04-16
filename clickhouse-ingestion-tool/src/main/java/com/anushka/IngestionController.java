package com.anushka;

import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@RestController
public class IngestionController {

    @Autowired
    private ClickHouseService clickHouseService;

    @Autowired
    private CsvService csvService;

    // Handle connection form (already wired in your index.html)
    @PostMapping("/connect")
    public String connectClickHouse(@RequestParam String host,
            @RequestParam int port,
            @RequestParam String database,
            @RequestParam String username,
            @RequestParam String jwtToken) {
        try {
            clickHouseService.connect(host, port, database, username, jwtToken);
            return "Connected to ClickHouse!";
        } catch (SQLException e) {
            return "Connection Failed: " + e.getMessage();
        }
    }

    @PostMapping("/ingest/ch-to-file")
    public String chToFile(@RequestParam String tableName,
            @RequestParam String filePath) {
        try {
            ResultSet rs = clickHouseService.executeQuery("SELECT * FROM " + tableName);
            List<String[]> csvData = new ArrayList<>();

            int columnCount = rs.getMetaData().getColumnCount();
            String[] headers = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                headers[i - 1] = rs.getMetaData().getColumnName(i);
            }
            csvData.add(headers);

            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getString(i);
                }
                csvData.add(row);
            }

            csvService.writeCsv(filePath, csvData);
            return "Export complete! Records written: " + (csvData.size() - 1);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/ingest/file-to-ch")
    public String fileToCh(@RequestParam String filePath,
            @RequestParam String tableName) {
        try {
            List<String[]> rows = csvService.readCsv(filePath);
            if (rows.size() < 2)
                return "File is empty or only contains headers";

            String[] headers = rows.get(0);
            String columnList = String.join(",", headers);

            for (int i = 1; i < rows.size(); i++) {
                String[] row = rows.get(i);
                String values = "'" + String.join("','", row) + "'";
                String insertQuery = String.format(
                        "INSERT INTO %s (%s) VALUES (%s)",
                        tableName, columnList, values);
                clickHouseService.executeUpdate(insertQuery);
            }

            return "Import complete! Records inserted: " + (rows.size() - 1);
        } catch (IOException | CsvException | SQLException e) {
            return "Error: " + e.getMessage();
        }
    }
}