package com.example.helloworld;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginUI extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JPanel panel;
    private final JFrame frame;
    private JTable table1;
    private JPanel panel1;

    public static void main(String[] args) {
        new LoginUI();
    }

    public LoginUI() {
        frame = new JFrame("Login Frame");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(250, 200));
        frame.setResizable(false);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        txtUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    JOptionPane.showMessageDialog(null, "Chưa nhập password");
                }
            }
        });

        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });
    }

    private void login() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        //Tạo bảng
        JFrame frame2 = new JFrame("Dữ liệu bảng");
        frame2.setLayout(new FlowLayout());
        frame2.setSize(400, 400);

        DefaultTableModel defaultTableModel = new DefaultTableModel();
        JTable table = new JTable(defaultTableModel);
        table.setPreferredScrollableViewportSize(new Dimension(300, 100));
        table.setFillsViewportHeight(true);
        frame2.add(new JScrollPane(table));
        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("First Name");
        defaultTableModel.addColumn("Last Name");
        defaultTableModel.addColumn("Username");

        try {
            //viết code kết nối cơ sở dữ liệu và kiểm tra đăng nhập
            String dbURL = "jdbc:mysql://localhost/mysql_db";
            String username = txtUsername.getText();
            String password = String.valueOf(txtPassword.getPassword());
            conn = (Connection) DriverManager.getConnection(dbURL, username, password);
            if (conn != null) {
                JOptionPane.showMessageDialog(null, "Đăng nhập thành công");
            }
            // Câu lệnh Xem dữ liệu
            String sql = "select * from lab4";
            // Tạo đối tượng thực thi câu lệnh Select
            st = (Statement) conn.createStatement();
            // Thực thi
            rs = st.executeQuery(sql);

            // Nếu không có dữ liệu trong bảng
            if (!rs.isBeforeFirst()) {
                JOptionPane.showMessageDialog(null, "Bảng không có dữ liệu!");
            }

            while (rs.next()) {
                //Retrieving details from the database and storing it in the String variables
                int id = rs.getInt("ID");
                String firstName = rs.getString("First Name");
                String lastName = rs.getString("Last Name");
                int age = rs.getInt("Age");

                defaultTableModel.addRow(new Object[]{id, firstName, lastName, age});//Adding row in Table
                frame2.setVisible(true);//Setting the visibility of second Frame
                frame2.validate();

            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Sai thông tin đăng nhập",
                    "Warning", JOptionPane.ERROR_MESSAGE);
        }
    }

}