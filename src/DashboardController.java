import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class DashboardController {

    @FXML private Label labelWelcome;
    @FXML private AnchorPane contentArea;

    private int userId;
    private String username;
    private String email;
    private String tc;

    public void setUserData(int id, String username, String email, String tc) {
        this.userId = id;
        this.username = username;
        this.email = email;
        this.tc = tc;

        labelWelcome.setText("Hoşgeldin " + username + ",");
    }

    // --- CONTENT VIEW LOADER ---
    private void setContent(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent view = loader.load();

            // Paneli temizle
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);

            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);

            // Eğer view controller kullanıcı bilgisi istiyorsa gönder
            if (fxmlFile.equals("ProfileView.fxml")) {
                ProfileController pc = loader.getController();
                pc.setUserData(userId, username, email, tc);
            }
            if (fxmlFile.equals("BorrowView.fxml")) {
                BorrowController bc = loader.getController();
                bc.setUserData(userId, username);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // --- BUTTON ACTIONS ---
    @FXML
    private void openBookSearch() {
        setContent("BookSearchView.fxml");
    }

    @FXML
    private void openBorrowOperations() {
        setContent("BorrowView.fxml");
    }

    @FXML
    private void openProfileView() {
        setContent("ProfileView.fxml");
    }
}
