package Giaodien;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class LoginFormGUI extends JFrame {
    private JFrame frame;

    public LoginFormGUI() {
        frame = new JFrame("Login");
        ImageIcon icon = new ImageIcon("vnpt_logo.png");
        frame.setIconImage(icon.getImage());
        frame.setSize(520, 680);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(59, 89, 152));

        JLabel loginlb = new JLabel("Login");
        loginlb.setBounds(0, 25, 520, 100);
        loginlb.setForeground(Color.white);
        loginlb.setFont(new Font("Dialog", Font.BOLD, 40));
        loginlb.setHorizontalAlignment(SwingConstants.CENTER);

        frame.add(loginlb);

        // phần userName
        JLabel userNameLB = new JLabel("Username:");
        userNameLB.setBounds(30, 150, 400, 25);
        userNameLB.setForeground(Color.white);
        userNameLB.setFont(new Font("Dialog", Font.PLAIN, 18));

        JTextField userNameField = new JTextField();
        userNameField.setBounds(30, 185, 430, 55);
        userNameField.setBackground(new Color(102, 117, 127));
        userNameField.setForeground(Color.white);
        userNameField.setFont(new Font("Dialog", Font.PLAIN, 24));

        frame.add(userNameLB);
        frame.add(userNameField);

        // phần password
        JLabel passwordLB = new JLabel("Password:");
        passwordLB.setBounds(30, 330, 400, 25);
        passwordLB.setForeground(Color.white);
        passwordLB.setFont(new Font("Dialog", Font.PLAIN, 18));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setEchoChar('*');
        passwordField.setBounds(30, 365, 430, 55);
        passwordField.setBackground(new Color(102, 117, 127)
        );
        passwordField.setForeground(Color.white);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 24));

        // Icon con mắt
        ImageIcon eyeIcon = new ImageIcon("visibility.png");
        ImageIcon eyeOffIcon = new ImageIcon("visibility_off.png");
        JButton eyeButton = new JButton(eyeOffIcon);
        eyeButton.setBounds(467, 378, 30, 30); // Đặt vào cuối JPasswordField
        eyeButton.setBackground(new Color(59, 89, 152));
        eyeButton.setBorderPainted(false);
        eyeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // chế độ ẩn/mở mật khẩu
        eyeButton.addActionListener(e -> {
            if (passwordField.getEchoChar() == '*') {
                // Mật khẩu đang bị ẩn (có dấu sao), sẽ hiển thị mật khẩu
                passwordField.setEchoChar((char) 0);
                eyeButton.setIcon(eyeIcon);// chuyển icon mắt
            } else {
                passwordField.setEchoChar('*');
                eyeButton.setIcon(eyeOffIcon);
            }
        });

        frame.add(passwordLB);
        frame.add(passwordField);
        frame.add(eyeButton);

        // button login
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(125, 520, 250, 50);
        loginButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        // hiện hình bàn tay click khi đưa chuột vào
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setBackground(Color.white);

        frame.add(loginButton);

        frame.setVisible(true);

        // Hành động đăng nhập
        String tkAdmin = "admin";
        String mkAdmin = "admin";
        ActionListener loginAction = e -> {
            String userName = userNameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            if(userName.equals(tkAdmin) && password.equals(mkAdmin)) {
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                // Hiển thị giao diện Display
                new Display();
                dispose();
            }
            else {
                JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu không chính xác!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        };

        // Gán sự kiện cho nút Login
        loginButton.addActionListener(loginAction);

        // Gán sự kiện Enter cho passwordField
        passwordField.addActionListener(loginAction);
    }
}
