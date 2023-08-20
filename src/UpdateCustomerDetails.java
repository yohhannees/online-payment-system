import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class UpdateCustomerDetails extends JFrame implements ActionListener {

    JTextField idField, nameField, mbpsField, cityField, phoneField;
    JButton updateButton;
    String customerId; // New variable to store the customer ID

    UpdateCustomerDetails(String customerId) {
        super("Update Customer Details");
        setSize(400, 300);
        setLocation(400, 200);
        this.customerId = customerId; // Assign the customer ID

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField();

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel mbpsLabel = new JLabel("Mbp/sec:");
        mbpsField = new JTextField();

        JLabel cityLabel = new JLabel("City:");
        cityField = new JTextField();

        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField();

        updateButton = new JButton("Update");

        setLayout(new GridLayout(6, 2));

        add(idLabel);
        add(idField);
        add(nameLabel);
        add(nameField);
        add(mbpsLabel);
        add(mbpsField);
        add(cityLabel);
        add(cityField);
        add(phoneLabel);
        add(phoneField);
        add(updateButton);

        updateButton.addActionListener(this);

        // Retrieve the customer details from the database
        try {
            conn c1 = new conn();
            String query = "SELECT * FROM customer WHERE id=?";
            PreparedStatement pstmt = c1.c.prepareStatement(query);
            pstmt.setString(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                idField.setText(rs.getString("id"));
                nameField.setText(rs.getString("name"));
                mbpsField.setText(rs.getString("Mbp/sec"));
                cityField.setText(rs.getString("city"));
                phoneField.setText(rs.getString("phone number"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == updateButton) {
            String id = idField.getText();
            String name = nameField.getText();
            String mbps = mbpsField.getText();
            String city = cityField.getText();
            String phone = phoneField.getText();

            // Update the customer details in the database
            try {
                conn c1 = new conn();
                String query = "UPDATE customer SET name=?, `Mbp/sec`=?, city=?, `phone number`=? WHERE id=?";
                PreparedStatement pstmt = c1.c.prepareStatement(query);
                pstmt.setString(1, name);
                pstmt.setString(2, mbps);
                pstmt.setString(3, city);
                pstmt.setString(4, phone);
                pstmt.setString(5, id);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Customer details updated successfully!");

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating customer details. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        new UpdateCustomerDetails("customer_id").setVisible(true);
    }
}
