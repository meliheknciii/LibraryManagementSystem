package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import model.StaffSession;

public class StaffDashboardController {
    @FXML private AnchorPane contentArea;

    @FXML
    private void handleLogout(ActionEvent event) {

        StaffSession.clear();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close(); // ya da login ekranına dön
    }
    @FXML
    private void openAddBook() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddBookView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Kitap Ekle");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void openBookManage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/BookManageView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Kitap Yönetimi");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) { e.printStackTrace(); }
    }
    @FXML
    private void openBorrowList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/StaffBorrowListView.fxml"));
            Parent root = loader.load();
            contentArea.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
