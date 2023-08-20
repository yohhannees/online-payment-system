import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class pay_bill extends JFrame implements ActionListener {
    JLabel customerIdLabel, mbpSecLabel, monthLabel, amountLabel;
    JTextField customerIdField, mbpSecField, monthField;
    JButton searchButton, payBillButton;
    JTextArea billTextArea;

    pay_bill() {
        super("Bill Payment");
        setLocation(350, 200);
        setSize(450, 350);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBackground(Color.WHITE);

        customerIdLabel = new JLabel("Customer ID:");
        customerIdField = new JTextField();
        monthLabel = new JLabel("Month:");
        monthField = new JTextField();
        amountLabel = new JLabel("Amount to Pay:");
        billTextArea = new JTextArea();

        searchButton = new JButton("Search");
        payBillButton = new JButton("Pay Bill");

        panel.add(customerIdLabel);
        panel.add(customerIdField);
        panel.add(monthLabel);
        panel.add(monthField);
        panel.add(amountLabel);
        panel.add(billTextArea);
        panel.add(searchButton);
        panel.add(payBillButton);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        searchButton.addActionListener(this);
        payBillButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == searchButton) {
            String customerId = customerIdField.getText();

            try {
                conn c1 = new conn();
                String s1 = "SELECT * FROM customer WHERE id = '" + customerId + "'";
                ResultSet resultSet = c1.s.executeQuery(s1);

                if (resultSet.next()) {
                    String customerName = resultSet.getString("name");
                    int mbpSec = resultSet.getInt("Mbp/sec");
                    String city = resultSet.getString("city");
                    String phoneNumber = resultSet.getString("phone number");

                    // Display customer details in the billTextArea
                    billTextArea.setText("Customer ID: " + customerId + "\n" +
                            "Customer Name: " + customerName + "\n" +
                            "Mbps/Sec: " + mbpSec + "\n" +
                            "City: " + city + "\n" +
                            "Phone Number: " + phoneNumber + "\n" +
                            "AMOUNT: " + (mbpSec * 10) + " BIRR");
                } else {
                    JOptionPane.showMessageDialog(null, "Customer not found!");
                }

                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (ae.getSource() == payBillButton) {
            String customerId = customerIdField.getText();
            String month = monthField.getText();

            try {
                conn c1 = new conn();
                String s1 = "SELECT * FROM customer WHERE id = '" + customerId + "'";
                ResultSet resultSet = c1.s.executeQuery(s1);

                if (resultSet.next()) {
                    int mbpSec = resultSet.getInt("Mbp/sec");
                    int amount = mbpSec * 10; // Calculate amount to pay

                    // Insert the payment details into the bill table
                    String query = "INSERT INTO bill (customer_id, month, amount) VALUES ('" + customerId + "', '" + month + "', " + amount + ")";
                    c1.s.executeUpdate(query);

                    JOptionPane.showMessageDialog(null, "Payment successful!");
                } else {
                    JOptionPane.showMessageDialog(null, "Customer not found!");
                }

                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new pay_bill().setVisible(true);
    }
}
