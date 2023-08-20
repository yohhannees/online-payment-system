import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class bill_details extends JFrame implements ActionListener {

    JTable t1;
    JButton b1;
    JButton refreshButton;
    String x[] = {"Customer ID", "Month", "Amount"};
    String y[][] = new String[20][3];
    int i = 0, j = 0;
    JTextField searchField; // Add a search field

    bill_details() {
        super("Bill Details");
        setSize(800, 400);
        setLocation(200, 200);

        try {
            conn c1 = new conn();
            String s1 = "SELECT * FROM bill";
            ResultSet rs = c1.s.executeQuery(s1);
            while (rs.next()) {
                y[i][j++] = rs.getString("customer_id");
                y[i][j++] = rs.getString("month");
                y[i][j++] = rs.getString("amount");
                i++;
                j = 0;
            }
            t1 = new JTable(y, x);

        } catch (Exception e) {
            e.printStackTrace();
        }

        searchField = new JTextField(); // Create a new text field for search
        searchField.addActionListener(this);
        add(searchField, "North");

        b1 = new JButton("Search");
        add(b1, "South");
        JScrollPane sp = new JScrollPane(t1);
        add(sp);
        b1.addActionListener(this);

        refreshButton = new JButton("Refresh"); // Create the refresh button
        add(refreshButton, "East");
        refreshButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {

                String searchTerm = searchField.getText();
                search(searchTerm);

        } else if (ae.getSource() == refreshButton) {
            refreshTable();
        }
    }

    public void search(String searchTerm) {
        for (int row = 0; row < y.length; row++) {
            for (int col = 0; col < y[row].length; col++) {
                y[row][col] = null;
            }
        }
        // Perform the search in the database using the searchTerm
        try {
            conn c1 = new conn();
            String s1 = "SELECT * FROM bill WHERE customer_id LIKE '%" + searchTerm + "%'";
            ResultSet rs = c1.s.executeQuery(s1);
            int row = 0;
            while (rs.next()) {
                y[row][0] = rs.getString("customer_id");
                y[row][1] = rs.getString("month");
                y[row][2] = rs.getString("amount");
                row++;
            }
            t1.repaint(); // Repaint the table with search results

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshTable() {
        // Clear the table
        for (int row = 0; row < y.length; row++) {
            for (int col = 0; col < y[row].length; col++) {
                y[row][col] = null;
            }
        }

        // Retrieve the original data from the database
        try {
            conn c1 = new conn();
            String s1 = "SELECT * FROM bill";
            ResultSet rs = c1.s.executeQuery(s1);
            int row = 0;
            while (rs.next()) {
                y[row][0] = rs.getString("customer_id");
                y[row][1] = rs.getString("month");
                y[row][2] = rs.getString("amount");
                row++;
            }
            t1.repaint(); // Repaint the table with the original data

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new bill_details().setVisible(true);
    }
}
