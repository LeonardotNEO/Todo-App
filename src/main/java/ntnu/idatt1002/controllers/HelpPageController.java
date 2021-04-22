package ntnu.idatt1002.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ntnu.idatt1002.HelpSection;
import ntnu.idatt1002.service.HelpService;
import ntnu.idatt1002.service.UserStateService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class HelpPageController {
    @FXML private VBox helpMenuVBox;
    @FXML private Text headerText;
    @FXML private Text descriptionText;
    @FXML private VBox vboxForInfoText;
    @FXML private ScrollPane infoScrollBar;

    private final String DEFAULT_HOVER = "orange";
    private final String DEFAULT_IDLE = "#00254d";
    private final double SCROLL_SPEED = 3;
    public void initialize() {
        fillMenuPage();
    }

    public void fillMenuPage() {
        ArrayList<String> pages = HelpService.getSections();
        ArrayList<Button> buttons = new ArrayList<>();

        // Set default values
        String color = DEFAULT_IDLE;
        String hoverColor = DEFAULT_HOVER;

        // Create color theme from the users current theme
        if(UserStateService.checkIfUserState()) {
            String theme = UserStateService.getCurrentUser().getTheme();
            String[] colorTheme =  theme.split(";");
            color = colorTheme[2].split(":")[1];
            hoverColor = colorTheme[3].split(":")[1];
        }

        String idleStyle = "-fx-background-color: " +  color + "; -fx-background-radius: 5; -fx-font-size: 16; -fx-text-fill: white; -fx-background-size: 100% 100%;";
        String hoverStyle = "-fx-background-color: " +  color + ";-fx-text-fill: " +  hoverColor + "; -fx-background-radius: 5; -fx-font-size: 16; -fx-background-size: 100% 100%;";

        // Set spacing
        helpMenuVBox.setSpacing(5);

        // Add all buttons
        pages.forEach(page -> {

            // Create new button
            Button button = new Button(page);
            button.setMaxWidth(1.7976931348623157E308);
            button.setPrefWidth(Region.USE_COMPUTED_SIZE);

            // Add button to list of buttons
            buttons.add(button);

            // Set default style
            button.setStyle(idleStyle);

            // Add hover effects
            button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
            button.setOnMouseExited(e -> button.setStyle(idleStyle));

            // Reset all other buttons
            button.setOnMouseClicked( event -> buttons.stream().filter(b -> b != button).forEach(b -> {
                b.setOnMouseExited(e -> b.setStyle(idleStyle));
                b.setStyle(idleStyle);
            }));

            // Add on action event
            button.setOnAction(event -> {
                try {
                    // Make the button stay in on hover style
                    button.setOnMouseExited(e -> {});

                    // Load info pages
                    getInfoPage(page);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Add button to the vbox
            helpMenuVBox.getChildren().add(button);
        });
    }

    public void getInfoPage(String section) throws IOException {
        // Reset the scrollbar
        infoScrollBar.setHvalue(0);
        infoScrollBar.setVvalue(0);

        // Clearing children
        vboxForInfoText.getChildren().clear();
        System.out.println( "After clear " + vboxForInfoText.getHeight());
        // Getting information
        HelpSection helpSection = HelpService.getSection(section);

        // Setting header and description
        assert helpSection != null;
        headerText.setText(helpSection.getSection());
        descriptionText.setText(helpSection.getDescription());

        // Getting fields
        ArrayList<HelpSection.Info> fields = helpSection.getFields();

        for(HelpSection.Info field: fields) {
            // Load controller and AnchorPane
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/infoField.fxml"));
            AnchorPane infoField = loader.load();
            InfoFieldController infoFieldController = loader.getController();

            // Add title if it exists
            if(field.getTitle() != null) {
                infoFieldController.setTitle(field.getTitle());
            } else {
                infoFieldController.hideTitle();
            }

            //Add text if it exists
            if(field.getText() != null) {
                infoFieldController.setText(field.getText());
            } else {
                infoFieldController.hideText();
            }

            //Add image if it exists
            if(field.getImage() != null) {
                InputStream stream = new FileInputStream(field.getImage());
                Image image = new Image(stream);
                infoFieldController.setImageView(image);
            } else {
                infoFieldController.hideImage();
            }
            System.out.println(infoField.heightProperty());
            // Adding it to the vbox
            vboxForInfoText.getChildren().add(vboxForInfoText.getChildren().size(), infoField);
            //System.out.println("I finished");
        }

        // Changing the scrollbar scroll speed
        Platform.runLater(() -> {
            double height = vboxForInfoText.getHeight();
            System.out.println(height);
            infoScrollBar.getContent().setOnScroll(scrollEvent -> {
                double deltaY = scrollEvent.getDeltaY() * SCROLL_SPEED/height;//(SCROLL_SPEED/718.4)/height  ;//*(718.4/height);// ((SCROLL_SPEED*718.4)/height);
                System.out.println("Delta Y: " + scrollEvent.getDeltaY());
                infoScrollBar.setVvalue(infoScrollBar.getVvalue() - deltaY);
            });
        });
    }
}