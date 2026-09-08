import java.io.*;
import java.util.ArrayList;

public class LibraryManager {

    private ArrayList<Book> books = new ArrayList<>();
    private final String FILE_NAME = "books.csv";

    public LibraryManager() {
        loadBooks();
    }

    public void addBook(Book book) {
        books.add(book);
        saveBooks();
    }

    public void deleteBook(String id) {
        books.removeIf(book -> book.getBookId().equalsIgnoreCase(id));
        saveBooks();
    }

    public Book searchBook(String key) {
        for (Book book : books) {
            if (book.getBookId().equalsIgnoreCase(key) ||
                book.getTitle().toLowerCase().contains(key.toLowerCase())) {
                return book;
            }
        }
        return null;
    }

    public boolean issueBook(String id, String student) {
        Book b = searchBook(id);
        if (b != null && !b.isIssued()) {
            b.setIssued(true);
            b.setIssuedTo(student);
            saveBooks();
            return true;
        }
        return false;
    }

    public boolean returnBook(String id) {
        Book b = searchBook(id);
        if (b != null && b.isIssued()) {
            b.setIssued(false);
            b.setIssuedTo("");
            saveBooks();
            return true;
        }
        return false;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    private void saveBooks() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Book b : books)
                out.println(b.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBooks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                books.add(Book.fromCSV(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}