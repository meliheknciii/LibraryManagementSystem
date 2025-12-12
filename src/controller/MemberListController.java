package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import model.Member;

public class MemberListController {

    @FXML private TableView<Member> tableMembers;
    @FXML private TableColumn<Member, Integer> colId;
    @FXML private TableColumn<Member, String> colUsername;
    @FXML private TableColumn<Member, String> colEmail;
    @FXML private TableColumn<Member, String> colTc;

    private ObservableList<Member> memberList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTc.setCellValueFactory(new PropertyValueFactory<>("tc"));

        loadMembers();
    }

    private void loadMembers() {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");

            while (rs.next()) {
                memberList.add(new Member(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("tc")
                ));
            }

            tableMembers.setItems(memberList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleBorrow() {
        Member selectedMember = tableMembers.getSelectionModel().getSelectedItem();

        if (selectedMember == null) {
            showAlert("Lütfen bir üye seçin!");
            return;
        }

        openBorrowView(selectedMember);
    }
    private void openBorrowView(Member member) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/BorrowView.fxml")
            );
            Parent root = loader.load();

            BorrowController controller = loader.getController();
            controller.setUserData(
                    member.getId(),
                    member.getUsername()
            );

            Stage stage = new Stage();
            stage.setTitle("Ödünç Ver - " + member.getUsername());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Uyarı");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
