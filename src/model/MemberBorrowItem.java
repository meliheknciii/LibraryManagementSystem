package model;

import java.time.LocalDate;

public class MemberBorrowItem {

    private int borrowId;
    private int bookId;
    private String title;
    private LocalDate borrowDate;
    private LocalDate dueDate;      // ✅ EKLENDİ
    private String status;

    public MemberBorrowItem(
            int borrowId,
            int bookId,
            String title,
            LocalDate borrowDate,
            LocalDate dueDate,
            String status
    ) {
        this.borrowId = borrowId;
        this.bookId = bookId;
        this.title = title;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public int getBorrowId() { return borrowId; }
    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }   // ✅
    public String getStatus() { return status; }
}

