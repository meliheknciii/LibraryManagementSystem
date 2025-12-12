package dao;

import model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;


public class BorrowDAO {
    public void insertBorrow(int memberId, int bookId, int staffId, LocalDate dueDate) {

        String sql = """
            INSERT INTO borrow (member_id, book_id, staff_id, borrow_date, due_date)
            VALUES (?, ?, ?, CURDATE(), ?)
        """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, memberId);
            ps.setInt(2, bookId);
            ps.setInt(3, staffId);
            ps.setDate(4, java.sql.Date.valueOf(dueDate));

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ödünç kaydı eklenemedi (DB hatası)");
        }
    }
}
