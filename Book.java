import java.io.Serializable;

public class Book implements Serializable {

    private String bookId;
    private String title;
    private String author;
    private boolean issued;
    private String issuedTo;

    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.issued = false;
        this.issuedTo = "";
    }

    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return issued; }
    public String getIssuedTo() { return issuedTo; }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    public void setIssuedTo(String name) {
        this.issuedTo = name;
    }

    public Object[] toTableRow() {
        return new Object[]{
            bookId,
            title,
            author,
            issued ? "Issued" : "Available",
            issuedTo
        };
    }

    @Override
    public String toString() {
        return bookId + "," + title + "," + author + "," + issued + "," + issuedTo;
    }

    public static Book fromCSV(String line) {
        String[] d = line.split(",", -1);
        Book b = new Book(d[0], d[1], d[2]);
        b.setIssued(Boolean.parseBoolean(d[3]));
        b.setIssuedTo(d[4]);
        return b;
    }
}