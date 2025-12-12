package dao;

import model.DatabaseConnection;
import model.MemberBorrowItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {

    // 🔹 ÖDÜNÇ EKLEME (SENİN KODUN – DEĞİŞMEDİ)
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

    // 🔹 AKTİF ÖDÜNÇLERİ GETİR (İADE TARİHİ DAHİL)
    public List<MemberBorrowItem> getActiveBorrowsByMember(int memberId) {

        List<MemberBorrowItem> list = new ArrayList<>();

        String sql = """
            SELECT br.id AS borrow_id,
                   br.book_id,
                   b.title,
                   br.borrow_date,
                   br.due_date,
                   br.status
            FROM borrow br
            JOIN books b ON b.id = br.book_id
            WHERE br.member_id = ? AND br.status = 'BORROWED'
        """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, memberId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new MemberBorrowItem(
                        rs.getInt("borrow_id"),
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getDate("borrow_date").toLocalDate(),
                        rs.getDate("due_date") != null
                                ? rs.getDate("due_date").toLocalDate()
                                : null,
                        rs.getString("status")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public void returnBook(int borrowId, int bookId) {

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {

            String sql1 = "UPDATE borrow SET status='RETURNED', return_date=NOW() WHERE id=?";
            try (PreparedStatement ps1 = conn.prepareStatement(sql1)) {
                ps1.setInt(1, borrowId);
                ps1.executeUpdate();
            }

            String sql2 = "UPDATE books SET quantity = quantity + 1 WHERE id=?";
            try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
                ps2.setInt(1, bookId);
                ps2.executeUpdate();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
