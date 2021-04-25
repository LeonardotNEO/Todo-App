package ntnu.idatt1002.dao;

import ntnu.idatt1002.model.Task;
import ntnu.idatt1002.model.User;
import ntnu.idatt1002.utils.DateUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class for writing user data to csv files and retrieving the generated files
 */
public final class ExcelDAO {
    private static final String SAVEPATH = "src/main/resources/saves";
    private static final String[] HEADERS = {
            "name",
            "project",
            "category",
            "description",
            "priority",
            "start date",
            "deadline",
            "location"
    };

    /**
     * Export user data to a csv file under the user folder
     * @param username {@code username} variable for a {@link User}
     * @throws IOException if file could not be found
     */
    public static void write(String username) throws IOException {
        if(UserDAO.exists(username)){
            //Clear file
            FileWriter fileWriter = new FileWriter(filepath(username), false);

            //Create printer
            CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader(HEADERS));

            //Get content
            ArrayList<Task> tasks = TaskDAO.list(username);

            //Print content
            if(tasks != null) {
                for(Task task : tasks) {
                    printer.printRecord(
                            task.getName(),
                            task.getProject(),
                            task.getCategory(),
                            task.getDescription(),
                            task.getPriority(),
                            DateUtils.getFormattedDate(task.getStartDate()),
                            DateUtils.getFormattedDate(task.getDeadline()),
                            task.getLocation()
                    );
                }
            }

            //Close printer
            printer.flush();
            printer.close();
            fileWriter.close();
        }
    }

    /**
     * Get user csv file from storage
     * @param username {@code username} variable for a {@link User}
     * @return A {@link File} object referencing the csv file, or {@code null} if file does not exist
     */
    public static File read(String username){
        File file = new File(filepath(username));
        if(!file.exists()){ return null; }
        else{ return file; }
    }

    //Get paths
    private static String userDir(String username){
        return (SAVEPATH + "/" + username + "/");
    }

    private static String filepath(String username){
        return (userDir(username) + username + ".csv");
    }
}
