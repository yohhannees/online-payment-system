import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class customer_details extends JFrame implements ActionListener {

    JTable t1;
    JButton b1;
    JButton refreshButton;
    String x[] = {"ID","Name","Mbp/sec","City","Phone"};
    String y[][] = new String[20][8];
    int i=0, j=0;
    JTextField searchField; // Add a search field

    customer_details(){
        super("Customer Details");
        setSize(1200,650);
        setLocation(200,200);

        try{
            conn c1  = new conn();
            String s1 = "select * from customer";
            ResultSet rs  = c1.s.executeQuery(s1);
            while(rs.next()){
                y[i][j++]=rs.getString("id");
                y[i][j++]=rs.getString("name");
                y[i][j++]=rs.getString("Mbp/sec");
                y[i][j++]=rs.getString("city");
                y[i][j++]=rs.getString("phone number");
                i++;
                j=0;
            }
            t1 = new JTable(y,x);

        }catch(Exception e){
            e.printStackTrace();
        }

        searchField = new JTextField(); // Create a new text field for search
        searchField.addActionListener(this);
        add(searchField, "North");

        b1 = new JButton("Search");
        add(b1,"South");
        JScrollPane sp = new JScrollPane(t1);
        add(sp);
        b1.addActionListener(this);
        refreshButton = new JButton("Refresh"); // Create the refresh button
        add(refreshButton, "East");
        refreshButton.addActionListener(this);

        // Add a mouse listener to the table
        t1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                int row = t1.getSelectedRow();
                if (row != -1) {
                    String id = (String) t1.getValueAt(row, 0);
                    openUpdateCustomerDetails(id);
                }
            }
        });
    }

    public void actionPerformed(ActionEvent ae){
        if (ae.getSource() == b1) {
            String searchTerm = searchField.getText();
            search(searchTerm);
        }
        else if (ae.getSource() == refreshButton) {
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
            conn c1  = new conn();
            String s1 = "select * from customer where name like '%" + searchTerm + "%'";
            ResultSet rs  = c1.s.executeQuery(s1);
            int row = 0;
            while(rs.next()){
                y[row][0]=rs.getString("id");
                y[row][1]=rs.getString("name");
                y[row][2]=rs.getString("Mbp/sec");
                y[row][3]=rs.getString("city");
                y[row][4]=rs.getString("phone number");
                row++;
            }
            t1.repaint(); // Repaint the table with search results

        } catch(Exception e){
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
            conn c1  = new conn();
            String s1 = "select * from customer";
            ResultSet rs  = c1.s.executeQuery(s1);
            int row = 0;
            while(rs.next()){
                y[row][0]=rs.getString("id");
                y[row][1]=rs.getString("name");
                y[row][2]=rs.getString("Mbp/sec");
                y[row][3]=rs.getString("city");
                y[row][4]=rs.getString("phone number");
                row++;
            }
            t1.repaint(); // Repaint the table with the original data

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void openUpdateCustomerDetails(String id) {
        EventQueue.invokeLater(() -> {
            try {
                UpdateCustomerDetails frame = new UpdateCustomerDetails(id);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args){
        new customer_details().setVisible(true);
    }
}
