package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
}
