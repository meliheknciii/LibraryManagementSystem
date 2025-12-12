package controller;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.MemberSession;
import service.BorrowService;

import java.time.LocalDate;

public class BorrowConfirmController {

    @FXML private Label bookTitleLabel;
    @FXML private DatePicker dueDatePicker;

    private int bookId;

    private BorrowService borrowService = new BorrowService();

    // Ana ekrandan veri almak için
    public void setBook(int bookId, String title) {
        this.bookId = bookId;
        bookTitleLabel.setText(title);
    }

    @FXML
    private void confirmBorrow() {

        LocalDate dueDate = dueDatePicker.getValue();

        if (dueDate == null || dueDate.isBefore(LocalDate.now())) {
            System.out.println("Geçerli bir iade tarihi seçin!");
            return;
        }

        borrowService.borrowBook(
                MemberSession.getMemberId(),
                bookId,
                1, // staffId (şimdilik)
                dueDate
        );

        closeWindow();
    }

    @FXML
    private void cancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) dueDatePicker.getScene().getWindow();
        stage.close();
    }
}
