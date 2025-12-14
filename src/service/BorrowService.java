package service;

import dao.BorrowDAO;

import java.time.LocalDate;

public class BorrowService {

    private final BorrowDAO borrowDAO = new BorrowDAO();

    // ==================================================
    // 📌 ÖDÜNÇ VER
    // ==================================================
    public void borrowBook(int memberId,
                           int bookId,
                           int staffId,
                           LocalDate dueDate) {

        if (memberId <= 0) {
            throw new RuntimeException("Üye bilgisi geçersiz!");
        }

        if (bookId <= 0) {
            throw new RuntimeException("Kitap seçilmedi!");
        }

        if (staffId <= 0) {
            throw new RuntimeException("Personel oturumu bulunamadı!");
        }

        if (dueDate == null || dueDate.isBefore(LocalDate.now())) {
            throw new RuntimeException("Geçerli bir teslim tarihi seçmelisiniz!");
        }

        borrowDAO.insertBorrow(
                memberId,
                bookId,
                staffId,
                dueDate
        );
    }

    // ==================================================
    // 📌 İADE ET
    // ==================================================
    public void returnBook(int borrowId) {

        if (borrowId <= 0) {
            throw new RuntimeException("Geçersiz ödünç kaydı!");
        }

        borrowDAO.returnBook(borrowId);
    }
}
