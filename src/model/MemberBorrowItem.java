package model;

public class MemberBorrowItem {

    private int borrowId;   // borrow tablosundaki ID
    private int bookId;     // books tablosundaki ID
    private String title;
    private String borrowDate;
    private String status;

    public MemberBorrowItem(int borrowId, int bookId, String title, String borrowDate, String status) {
        this.borrowId = borrowId;
        this.bookId = bookId;
        this.title = title;
        this.borrowDate = borrowDate;
        this.status = status;
    }

    public int getBorrowId() { return borrowId; }
    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getBorrowDate() { return borrowDate; }
    public String getStatus() { return status; }
}
