package jude;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Responsible for the main window of the chatbot interface.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Jude jude;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image judeImage = new Image(this.getClass().getResourceAsStream("/images/jude.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Jude instance
     */
    public void setJude(Jude d) {
        jude = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Jude's reply and then appends them
     * to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = jude.getResponse(input);
        String commandType = jude.getCommandType();

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getJudeDialog(response, judeImage, commandType)
        );
        userInput.clear();
    }

}
