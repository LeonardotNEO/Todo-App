package ntnu.idatt1002.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;

/**
 * Controller for categoryMenuButton.fxml
 */
public class CategoryMenuButtonController {
    @FXML private Button buttonAddTask;
    @FXML private Button buttonClickCategory;

    /**
     * Method used when we want to load a normal category button
     * @param category Name of the category (stand-alone category)
     */
    public void initializeNormalCategory(String category){
        buttonClickCategory.setText(category);

        if(buttonClickCategory.getText().equals(UserStateService.getCurrentUser().getCurrentlySelectedCategory())){
            buttonAddTask.setVisible(true);
            buttonAddTask.setManaged(true);
        } else {
            buttonAddTask.setVisible(false);
            buttonAddTask.setManaged(false);
        }

        // add listener when clicking buttonClickCategory
        buttonClickCategory.setOnAction(event -> {
            try {
                buttonClickNormalCategory(buttonClickCategory.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // add listener when clicking buttonAddTask
        buttonAddTask.setOnAction(event -> {
            try {
                buttonAddTask();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // set style of this clickCategoryButton
        setStyleOfButton(buttonClickCategory);
    }

    /**
     * Method used when we want to load a category button under a project
     * @param category Name of the category
     * @param project Name of the project
     */
    public void initializeProjectCategory(String category, String project){
        buttonClickCategory.setText(category);

        if(buttonClickCategory.getText().equals(UserStateService.getCurrentUser().getCurrentlySelectedProjectCategory())){
            buttonAddTask.setVisible(true);
            buttonAddTask.setManaged(true);
        } else {
            buttonAddTask.setVisible(false);
            buttonAddTask.setManaged(false);
        }

        // add listener to button
        buttonClickCategory.setOnAction(event -> {
            try {
                buttonClickProjectCategory(buttonClickCategory.getText(), project);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // add listener when clicking buttonAddTask
        buttonAddTask.setOnAction(event -> {
            try {
                buttonAddTask();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // set style of this clickCategoryButton
        setStyleOfButtonProject(buttonClickCategory);
    }

    /**
     * Method running when addTask button is clicked (to the right of currently selected category)
     * @throws IOException
     */
    public void buttonAddTask() throws IOException {
        // Load newEditTask page. get fxml variable and controller variable
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newEditTask.fxml"));
        Node node = loader.load();
        NewEditTaskController newEditTaskController = loader.getController();

        // load the task part of newEditTaskController
        newEditTaskController.initializeNewTask();

        // set dashboard content to editMenu
        DashboardController.getInstance().setCenterContent(node);
    }

    /**
     * Method used for setting the style of the category button.
     * Different styles depending on which category and if that category is selected.
     * @param button
     */
    public void setStyleOfButton(Button button){
        // set icons for trash bin and finished tasks
        FontAwesomeIconView icon = (FontAwesomeIconView) button.getGraphic();

        // set the style of selected button
        if(button.getText().equals("All tasks")){
            icon.setGlyphName("LIST");
            icon.getStyleClass().add("categoryButton-alltasks #icon");
            button.getStyleClass().add("categoryButton-alltasks");
            buttonAddTask.setVisible(false);
            buttonAddTask.setManaged(false);
        }
        if(button.getText().equals("Trash bin")){
            icon.setGlyphName("TRASH");
            icon.getStyleClass().add("categoryButton-trashbin #icon");
            button.getStyleClass().add("categoryButton-trashbin");
            buttonAddTask.setVisible(false);
            buttonAddTask.setManaged(false);
        }
        if(button.getText().equals("Finished tasks")){
            icon.setGlyphName("CHECK");
            icon.getStyleClass().add("categoryButton-finished #icon");
            button.getStyleClass().add("categoryButton-finished");
            buttonAddTask.setVisible(false);
            buttonAddTask.setManaged(false);
        }
        if(UserStateService.getCurrentUser().getCurrentlySelectedCategory() != null){
            if(UserStateService.getCurrentUser().getCurrentlySelectedCategory().equals(button.getText())){
                button.getStyleClass().removeAll(button.getStyleClass());
                button.getStyleClass().add("categoryButton-selected");
                icon.getStyleClass().add("categoryButton-selected #icon");
            }
        }
    }

    public void setStyleOfButtonProject(Button button){
        // set icons for trash bin and finished tasks
        FontAwesomeIconView icon = (FontAwesomeIconView) button.getGraphic();

        if(UserStateService.getCurrentUser().getCurrentlySelectedProjectCategory() != null){
            if(UserStateService.getCurrentUser().getCurrentlySelectedProjectCategory().equals(button.getText())){
                button.getStyleClass().removeAll(button.getStyleClass());
                button.getStyleClass().add("categoryButton-selected");
                icon.getStyleClass().add("categoryButton-selected #icon");
            }
        }
    }

    public void buttonClickNormalCategory(String category) throws IOException {

        UserStateService.getCurrentUser().setCurrentlySelectedCategory(category);

        DashboardController.getInstance().initialize();
    }

    public void buttonClickProjectCategory(String category, String project) throws IOException {
        UserStateService.getCurrentUser().setCurrentlySelectedProjectCategory(category);
        UserStateService.getCurrentUser().setCurrentlySelectedProject(project);

        DashboardController.getInstance().initialize();
    }

    public void setCategoryName(String name){
        buttonClickCategory.setText(name);
    }

}
