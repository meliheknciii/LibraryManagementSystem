import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProfileController {

    @FXML private Label labelName;
    @FXML private Label labelEmail;
    @FXML private Label labelTc;

    public void setUserData(int id, String username, String email, String tc) {
        labelName.setText("Ad: " + username);
        labelEmail.setText("Email: " + email);
        labelTc.setText("TC: " + tc);
    }
}