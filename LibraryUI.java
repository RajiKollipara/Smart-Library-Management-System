import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LibraryUI extends JFrame {

    LibraryManager manager = new LibraryManager();

    JTextField txtId = new JTextField();
    JTextField txtTitle = new JTextField();
    JTextField txtAuthor = new JTextField();
    JTextField txtStudent = new JTextField();

    JButton btnAdd = new JButton("Add Book");
    JButton btnDelete = new JButton("Delete");
    JButton btnSearch = new JButton("Search");
    JButton btnIssue = new JButton("Issue");
    JButton btnReturn = new JButton("Return");

    DefaultTableModel model;
    JTable table;

    public LibraryUI() {

        setTitle("Smart Library Management System");
        setSize(850, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel input = new JPanel(new GridLayout(5,2,10,10));
        input.setBorder(BorderFactory.createTitledBorder("Book Details"));

        input.add(new JLabel("Book ID"));
        input.add(txtId);

        input.add(new JLabel("Title"));
        input.add(txtTitle);

        input.add(new JLabel("Author"));
        input.add(txtAuthor);

        input.add(new JLabel("Student"));
        input.add(txtStudent);

        input.add(btnAdd);
        input.add(btnDelete);

        model = new DefaultTableModel(
                new String[]{"ID","Title","Author","Status","Student"},0);

        table = new JTable(model);

        JScrollPane scroll = new JScrollPane(table);

        JPanel bottom = new JPanel();
        bottom.add(btnSearch);
        bottom.add(btnIssue);
        bottom.add(btnReturn);

        add(input, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        refreshTable();

        btnAdd.addActionListener(e -> addBook());
        btnDelete.addActionListener(e -> deleteBook());
        btnSearch.addActionListener(e -> searchBook());
        btnIssue.addActionListener(e -> issueBook());
        btnReturn.addActionListener(e -> returnBook());
    }

    private void addBook() {

        String id = txtId.getText();
        String title = txtTitle.getText();
        String author = txtAuthor.getText();

        if(id.isEmpty() || title.isEmpty() || author.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Fill all fields");
            return;
        }

        manager.addBook(new Book(id,title,author));
        refreshTable();
        clearFields();
    }

    private void deleteBook() {
        manager.deleteBook(txtId.getText());
        refreshTable();
        clearFields();
    }

    private void searchBook() {

        Book b = manager.searchBook(txtId.getText());

        if(b == null){
            JOptionPane.showMessageDialog(this,"Book Not Found");
            return;
        }

        txtTitle.setText(b.getTitle());
        txtAuthor.setText(b.getAuthor());
        txtStudent.setText(b.getIssuedTo());
    }

    private void issueBook() {

        boolean ok = manager.issueBook(
                txtId.getText(),
                txtStudent.getText());

        JOptionPane.showMessageDialog(this,
                ok ? "Book Issued" : "Cannot Issue");

        refreshTable();
    }

    private void returnBook() {

        boolean ok = manager.returnBook(txtId.getText());

        JOptionPane.showMessageDialog(this,
                ok ? "Book Returned" : "Cannot Return");

        refreshTable();
    }

    private void refreshTable() {

        model.setRowCount(0);

        for(Book b : manager.getBooks()){
            model.addRow(b.toTableRow());
        }
    }

    private void clearFields() {

        txtId.setText("");
        txtTitle.setText("");
        txtAuthor.setText("");
        txtStudent.setText("");
    }
}