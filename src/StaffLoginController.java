import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;

public class StaffLoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    @FXML
    private void handleStaffLogin() {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if(user.isEmpty() || pass.isEmpty()) {
            statusLabel.setText("Boş alan bırakmayın");
            return;
        }

        try {
            Connection conn = DatabaseConnection.connect();

            String sql = "SELECT * FROM staff WHERE (username=? OR email=?) AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user);
            ps.setString(2, user);
            ps.setString(3, pass);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffDashboard.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) statusLabel.getScene().getWindow();
                stage.setScene(new Scene(root));
            }else{
                statusLabel.setText("Bilgiler yanlış");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Veritabanı hatası");
        }
    }

    @FXML
    private void goBack() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SelectLoginView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) statusLabel.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
