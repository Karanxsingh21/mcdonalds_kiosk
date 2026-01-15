package com.mcd.ui;

import com.mcd.db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Login extends JFrame {

    JTextField userField;
    JPasswordField passField;
    JButton loginBtn;

    public Login() {
        setTitle("Login - McDonald's Kiosk");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel title = new JLabel("Admin Login", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.RED);

        userField = new JTextField();
        passField = new JPasswordField();

        loginBtn = new JButton("Login");

        add(title);
        add(userField);
        add(passField);
        add(loginBtn);

        loginBtn.addActionListener(e -> login());
    }

    private void login() {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        try {
            Connection con = DBConnection.getConnection();

            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database problem!");
                return;
            }

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                dispose();                     // CLOSE LOGIN
                new KioskUI().setVisible(true); // OPEN KIOSK
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials!");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred!");
        }
    }
}
