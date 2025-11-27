import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class StaffDashboardController {

    @FXML
    private void handleLogout(ActionEvent event) {

        StaffSession.clear();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close(); // ya da login ekranına dön
    }
}
