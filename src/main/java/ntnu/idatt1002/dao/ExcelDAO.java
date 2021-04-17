package ntnu.idatt1002.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSetMetaData;

public final class ExcelDAO {
    private static final String SAVEPATH = "src/main/resources/saves";

    public static void export(String username) throws IOException {
        FileWriter fileWriter = new FileWriter(filepath(username));
        CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("username"));
        printer.printRecord(username);

        printer.flush();
        printer.close();
        fileWriter.close();
    }

    private static String userDir(String username){
        return (SAVEPATH + "/" + username + "/");
    }

    private static String filepath(String username){
        return (userDir(username) + username + ".csv");
    }
}
