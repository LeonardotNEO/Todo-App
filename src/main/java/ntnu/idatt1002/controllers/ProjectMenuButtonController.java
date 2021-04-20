package ntnu.idatt1002.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;

public class ProjectMenuButtonController {
    @FXML private VBox categories;
    @FXML private Button buttonNewCategory;
    @FXML private Button projectTitle;

    /**
     * When this projectmenu is loaded, we set addCategory button to visible if its the currently selected project
     */
    public void initializeProject(String projectName){
        projectTitle.setText(projectName);

        // hide or show add category button
        if(projectName.equals(UserStateService.getCurrentUser().getCurrentlySelectedProject())){
            buttonNewCategory.setVisible(true);
            buttonNewCategory.setManaged(true);
        } else {
            buttonNewCategory.setVisible(false);
            buttonNewCategory.setManaged(false);
        }

        // get icon of button
        FontAwesomeIconView iconProject = (FontAwesomeIconView) projectTitle.getGraphic();

        // set style of project button if this is the user currently selected project
        if(projectName.equals(UserStateService.getCurrentUser().getCurrentlySelectedProject())){
            projectTitle.getStyleClass().removeAll(projectTitle.getStyleClass());
            projectTitle.getStyleClass().add("categoryButton-selected");
            iconProject.getStyleClass().add("categoryButton-selected #icon");
        }
    }

    public void addCategoryUI(AnchorPane anchorPane){
        categories.getChildren().add(anchorPane);
    }

    public Button getProjectTitleButton(){
        return projectTitle;
    }

    public void buttonNewCategory() throws IOException {
        // Load newEditTask page. get fxml variable and controller variable
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/newEditCategory.fxml"));
        Node node = loader.load();
        NewEditCategoryController newEditCategoryController = loader.getController();

        // load the newCategory part of newEditCategoryController
        newEditCategoryController.setProjectName(projectTitle.getText());
        newEditCategoryController.intializeNewCategory();

        // set dashboard content to editMenu
        DashboardController.getInstance().setCenterContent(node);
    }

    public void buttonClickProject() throws IOException {
        // show add category button for the selected project
        categories.setVisible(true);
        categories.setManaged(true);

        // set currently selected project to this project
        UserStateService.getCurrentUser().setCurrentlySelectedProject(projectTitle.getText());

        // update dashboard to show changes
        DashboardController.getInstance().initialize();
    }

}
