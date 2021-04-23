package ntnu.idatt1002.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import ntnu.idatt1002.App;
import ntnu.idatt1002.service.UserStateService;

import java.io.IOException;

public class DeleteCategoryProjectController {

    private String project;
    private String category;
    private DashboardController dashboardController;
    private String delete;
    @FXML private Text headerText;
    @FXML private Text descriptionText;
    @FXML private Button confirmButton;
    @FXML private Button cancelButton;

    Popup popup = new Popup();

    public static void display(DashboardController dashboardController, String project, String category,
                               String delete) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ConfirmationController.class.getResource("/fxml/deleteCategoryProject.fxml"));
        AnchorPane anchorPane = fxmlLoader.load(); //root

        anchorPane.setStyle(UserStateService.getCurrentUser().getTheme());
        DeleteCategoryProjectController deleteCategoryProjectControllerInstance = fxmlLoader.getController();
        deleteCategoryProjectControllerInstance.dashboardController = dashboardController;
        deleteCategoryProjectControllerInstance.project = project;
        deleteCategoryProjectControllerInstance.category = category;
        deleteCategoryProjectControllerInstance.delete = delete;

        deleteCategoryProjectControllerInstance.popup.getContent().add(anchorPane);
        deleteCategoryProjectControllerInstance.popup.setAutoHide(true);
        deleteCategoryProjectControllerInstance.popup.show(App.getStage());
    }

    @FXML
    void clickCancelButton() {
        popup.hide();
    }

    @FXML
    void clickConfirmButton() throws IOException {
        if (delete.equalsIgnoreCase("project")) {
            dashboardController.deleteProject();
        } else if (delete.equalsIgnoreCase("category")) {
            dashboardController.deleteCategory();
        }
        popup.hide();
    }

}
