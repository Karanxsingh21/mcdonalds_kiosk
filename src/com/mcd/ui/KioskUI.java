package com.mcd.ui;

import com.mcd.model.Item;
import javax.swing.*;
import java.awt.*;

public class KioskUI extends JFrame {

    DefaultListModel<Item> menuModel = new DefaultListModel<>();
    DefaultListModel<Item> cartModel = new DefaultListModel<>();
    JList<Item> menuList, cartList;
    JLabel imageLabel, totalLabel;
    double total = 0;

    public KioskUI() {
        setTitle("McDonald's Self Service Kiosk");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // title
        JLabel title = new JLabel("Welcome to McDonald's", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(220, 20, 60));
        add(title, BorderLayout.NORTH);

        // Menu Items
        menuModel.addElement(new Item("Big Mac", 199, "images/bigmac.png"));
        menuModel.addElement(new Item("McChicken", 149, "images/mcchicken.png"));
        menuModel.addElement(new Item("Fries", 99, "images/fries.png"));
        menuModel.addElement(new Item("Coke", 59, "images/coke.png"));

        menuList = new JList<>(menuModel);
        styleList(menuList);

        cartList = new JList<>(cartModel);
        styleList(cartList);

        // Image Display
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(300, 250));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        imageLabel.setOpaque(true);
        imageLabel.setBackground(Color.WHITE);

        menuList.addListSelectionListener(e -> {
            Item item = menuList.getSelectedValue();
            if (item != null) {
                ImageIcon icon = new ImageIcon(item.imagePath);
                Image img = icon.getImage().getScaledInstance(300, 250, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(img));
            }
        });

        // Buttons
        JButton addBtn = createButton("Add ➜");
        JButton removeBtn = createButton("Remove");
        JButton orderBtn = createButton("Place Order");

        addBtn.addActionListener(e -> {
            Item item = menuList.getSelectedValue();
            if (item != null) {
                cartModel.addElement(item);
                total += item.price;
                updateTotal();
            }
        });

        removeBtn.addActionListener(e -> {
            Item item = cartList.getSelectedValue();
            if (item != null) {
                cartModel.removeElement(item);
                total -= item.price;
                updateTotal();
            }
        });

        orderBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Order Placed!\nTotal: Rs. " + total,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            cartModel.clear();
            total = 0;
            updateTotal();
            imageLabel.setIcon(null);
        });

        // ---------------- PANELS ----------------
        JPanel menuPanel = createCardPanel("Menu", new JScrollPane(menuList));
        JPanel cartPanel = createCardPanel("Cart", new JScrollPane(cartList));

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 20));
        buttonPanel.add(addBtn);
        buttonPanel.add(removeBtn);
        buttonPanel.add(orderBtn);

        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        centerPanel.add(menuPanel);
        centerPanel.add(buttonPanel);
        centerPanel.add(cartPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(imageLabel, BorderLayout.EAST);

        // Total
        totalLabel = new JLabel("Total: Rs. 0.0");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalLabel.setForeground(new Color(0, 128, 0));
        totalLabel.setHorizontalAlignment(JLabel.RIGHT);
        add(totalLabel, BorderLayout.SOUTH);
    }

    // Total Amount
    private void updateTotal() {
        totalLabel.setText("Total: Rs. " + total);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBackground(new Color(255, 193, 7));
        btn.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        return btn;
    }

    private void styleList(JList<Item> list) {
        list.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        list.setFixedCellHeight(40);
        list.setSelectionBackground(new Color(255, 193, 7));
        list.setSelectionForeground(Color.BLACK);
    }

    private JPanel createCardPanel(String title, Component comp) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setBackground(Color.WHITE);
        panel.add(comp);
        return panel;
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new KioskUI().setVisible(true);
    }
}
