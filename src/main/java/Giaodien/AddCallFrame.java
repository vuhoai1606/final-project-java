package Giaodien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class AddCallFrame extends JFrame {
    private JLabel callId, phone, hoDem, ten, maDV, maNV, thoiGian, batDau;
    private JTextField callIdField, phoneField, hoField, tenField;
    private JComboBox<String> maDVBox, maNVBox;
    private JComboBox<Integer> hourBox, minuteBox, secondBox;
    private JComboBox<Integer> dayBox, monthBox, yearBox, startHourBox, startMinuteBox, startSecondBox;
//    private AddCallListener addCallListener;
    private java.util.function.Predicate<String> isCallIdExists;
    private JTable parentTable;

    public AddCallFrame() {
        setTitle("Add Call Frame");
        setSize(500, 780);
        setLayout(null);
        setLocationRelativeTo(null);

        // Mã cuộc gọi
        callId = new JLabel("Mã cuộc gọi:");
        callId.setBounds(30, 20, 150, 50);
        callId.setFont(new Font("Arial", Font.BOLD, 18));
        add(callId);
        callIdField = new JTextField();
        callIdField.setBounds(200, 20, 250, 50);
        callIdField.setFont(new Font("Arial", Font.BOLD, 18));
        add(callIdField);

        // Số điện thoại
        phone = new JLabel("Số điện thoại:");
        phone.setBounds(30, 90, 150, 50);
        phone.setFont(new Font("Arial", Font.BOLD, 18));
        add(phone);
        phoneField = new JTextField();
        phoneField.setBounds(200, 90, 250, 50);
        phoneField.setFont(new Font("Arial", Font.BOLD, 18));
        add(phoneField);

        // Họ đệm khách hàng
        hoDem = new JLabel("Họ đệm KH:");
        hoDem.setBounds(30, 160, 150, 50);
        hoDem.setFont(new Font("Arial", Font.BOLD, 18));
        add(hoDem);
        hoField = new JTextField();
        hoField.setBounds(200, 160, 250, 50);
        hoField.setFont(new Font("Arial", Font.BOLD, 18));
        add(hoField);

        // Tên khách hàng
        ten = new JLabel("Tên KH:");
        ten.setBounds(30, 230, 150, 50);
        ten.setFont(new Font("Arial", Font.BOLD, 18));
        add(ten);
        tenField = new JTextField();
        tenField.setBounds(200, 230, 250, 50);
        tenField.setFont(new Font("Arial", Font.BOLD, 18));
        add(tenField);

        // Mã dịch vụ
        maDV = new JLabel("Mã dịch vụ:");
        maDV.setBounds(30, 300, 150, 50);
        maDV.setFont(new Font("Arial", Font.BOLD, 18));
        add(maDV);
        maDVBox = new JComboBox<>(generateCodes("DV", 1, 10));
        maDVBox.setBounds(200, 300, 250, 50);
        maDVBox.setFont(new Font("Arial", Font.BOLD, 18));
        add(maDVBox);

        // Mã nhân viên
        maNV = new JLabel("Mã nhân viên:");
        maNV.setBounds(30, 370, 150, 50);
        maNV.setFont(new Font("Arial", Font.BOLD, 18));
        add(maNV);
        maNVBox = new JComboBox<>(generateCodes("NV", 1, 10));
        maNVBox.setBounds(200, 370, 250, 50);
        maNVBox.setFont(new Font("Arial", Font.BOLD, 18));
        add(maNVBox);

        // Thời gian cuộc gọi
        thoiGian = new JLabel("Thời gian CG:");
        thoiGian.setBounds(30, 450, 150, 50);
        thoiGian.setFont(new Font("Arial", Font.BOLD, 18));
        add(thoiGian);
        JLabel gio = new JLabel("h:");
        gio.setBounds(200, 450, 20, 50);
        gio.setFont(new Font("Arial", Font.BOLD, 18));
        add(gio);
        hourBox = createNumberBox(0, 23);
        hourBox.setBounds(225, 450, 50, 50);
        hourBox.setFont(new Font("Arial", Font.BOLD, 18));
        add(hourBox);
        JLabel phut = new JLabel("m:");
        phut.setBounds(285, 450, 25, 50);
        phut.setFont(new Font("Arial", Font.BOLD, 18));
        add(phut);
        minuteBox = createNumberBox(0, 59);
        minuteBox.setBounds(315, 450, 50, 50);
        minuteBox.setFont(new Font("Arial", Font.BOLD, 18));
        add(minuteBox);
        JLabel giay = new JLabel("s:");
        giay.setBounds(375, 450, 20, 50);
        giay.setFont(new Font("Arial", Font.BOLD, 18));
        add(giay);
        secondBox = createNumberBox(1, 59);
        secondBox.setBounds(400, 450, 50, 50);
        secondBox.setFont(new Font("Arial", Font.BOLD, 18));
        add(secondBox);

        // Bắt đầu cuộc gọi - Ngày tháng năm
        batDau = new JLabel("Bắt đầu CG:");
        batDau.setBounds(30, 520, 150, 50);
        batDau.setFont(new Font("Arial", Font.BOLD, 18));
        add(batDau);
        JLabel nam = new JLabel("y:");
        nam.setBounds(200, 520, 20, 50);
        nam.setFont(new Font("Arial", Font.BOLD, 18));
        add(nam);
        yearBox = createNumberBox(2010, 2025);
        yearBox.setBounds(222, 520, 65, 50);
        yearBox.setFont(new Font("Arial", Font.BOLD, 18));
        add(yearBox);
        JLabel thang = new JLabel("m:");
        thang.setBounds(292, 520, 25, 50);
        thang.setFont(new Font("Arial", Font.BOLD, 18));
        add(thang);
        monthBox = createNumberBox(1, 12);
        monthBox.setBounds(319, 520, 50, 50);
        monthBox.setFont(new Font("Arial", Font.BOLD, 18));
        add(monthBox);
        JLabel ngay = new JLabel("d:");
        ngay.setBounds(374, 520, 20, 50);
        ngay.setFont(new Font("Arial", Font.BOLD, 18));
        add(ngay);
        dayBox = createNumberBox(1, 31);
        dayBox.setBounds(396, 520, 50, 50);
        dayBox.setFont(new Font("Arial", Font.BOLD, 18));
        add(dayBox);

        // Bắt đầu cuộc gọi - Giờ phút giây
        JLabel hour = new JLabel("h:");
        hour.setBounds(200, 590, 20, 50);
        hour.setFont(new Font("Arial", Font.BOLD, 18));
        add(hour);
        startHourBox = createNumberBox(0, 23);
        startHourBox.setBounds(225, 590, 50, 50);
        startHourBox.setFont(new Font("Arial", Font.BOLD, 18));
        add(startHourBox);
        JLabel min = new JLabel("m:");
        min.setBounds(285, 590, 25, 50);
        min.setFont(new Font("Arial", Font.BOLD, 18));
        add(min);
        startMinuteBox = createNumberBox(0, 59);
        startMinuteBox.setBounds(315, 590, 50, 50);
        startMinuteBox.setFont(new Font("Arial", Font.BOLD, 18));
        add(startMinuteBox);
        JLabel sec = new JLabel("s:");
        sec.setBounds(375, 590, 20, 50);
        sec.setFont(new Font("Arial", Font.BOLD, 18));
        add(sec);
        startSecondBox = createNumberBox(0, 59);
        startSecondBox.setBounds(400, 590, 50, 50);
        startSecondBox.setFont(new Font("Arial", Font.BOLD, 18));
        add(startSecondBox);

        // Button Lưu
        JButton addButton = new JButton("Lưu");
        addButton.setFont(new Font("Arial", Font.BOLD, 18));
        addButton.setBounds(175, 660, 150, 60);
        addButton.addActionListener(e -> handleAddButton());
        add(new JLabel()); // Empty space
        add(addButton);

        // Update days in combo box based on month and year
        monthBox.addActionListener(e -> updateDaysInMonth());
        yearBox.addActionListener(e -> updateDaysInMonth());

        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private String[] generateCodes(String prefix, int start, int end) {
        String[] codes = new String[end - start + 1];
        for (int i = 0; i < codes.length; i++) {
            codes[i] = prefix + String.format("%02d", start + i);
        }
        return codes;
    }

    private JComboBox<Integer> createNumberBox(int start, int end) {
        Integer[] numbers = new Integer[end - start + 1];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = start + i;
        }
        return new JComboBox<>(numbers);
    }

    private JPanel createTimePanel(JComboBox<Integer> hourBox, JComboBox<Integer> minuteBox,
                                   JComboBox<Integer> secondBox) {
        JPanel panel = new JPanel();
        panel.add(hourBox);
        panel.add(minuteBox);
        panel.add(secondBox);
        return panel;
    }

    private JPanel createDatePanel(JComboBox<Integer> yearBox, JComboBox<Integer> monthBox, JComboBox<Integer> dayBox) {
        JPanel panel = new JPanel();
        panel.add(yearBox);
        panel.add(monthBox);
        panel.add(dayBox);
        return panel;
    }

    private void updateDaysInMonth() {
        int year = (int) yearBox.getSelectedItem();
        int month = (int) monthBox.getSelectedItem();

        int daysInMonth;
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                daysInMonth = 30;
                break;
            case 2:
                daysInMonth = isLeapYear(year) ? 29 : 28;
                break;
            default:
                daysInMonth = 31;
        }

        dayBox.removeAllItems();
        for (int i = 1; i <= daysInMonth; i++) {
            dayBox.addItem(i);
        }
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private void handleAddButton() {
        String callId = callIdField.getText().trim();
        // Kiểm tra mã cuộc gọi có tồn tại không
        if (isCallIdExists(callId)) {
            JOptionPane.showMessageDialog(this, "Mã cuộc gọi đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Dừng xử lý nếu mã đã tồn tại
        }
        String phone = phoneField.getText();
        String firstName = hoField.getText().trim();
        String lastName = tenField.getText().trim();
        String serviceCode = (String) maDVBox.getSelectedItem();
        String employeeCode = (String) maNVBox.getSelectedItem();

        int callHour = (int) hourBox.getSelectedItem();
        int callMinute = (int) minuteBox.getSelectedItem();
        int callSecond = (int) secondBox.getSelectedItem();

        int startDay = (int) dayBox.getSelectedItem();
        int startMonth = (int) monthBox.getSelectedItem();
        int startYear = (int) yearBox.getSelectedItem();

        int startHour = (int) startHourBox.getSelectedItem();
        int startMinute = (int) startMinuteBox.getSelectedItem();
        int startSecond = (int) startSecondBox.getSelectedItem();

        // Validate input
        if (callId.isEmpty() || phone.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hãy điền đầy đủ các thông tin.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (phone.length() != 10 || !phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải 10 chữ số.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String callTime = String.format("%02d:%02d:%02d", callHour, callMinute, callSecond);
        String startDateTime = String.format("%04d-%02d-%02d %02d:%02d:%02d", startYear, startMonth, startDay,
                startHour, startMinute, startSecond);

        // Kết nối CSDL
        String url = "jdbc:sqlserver://DESKTOP-0A5HI5T:1433;"
                + "user=sa;"
                + "password=123;"
                + "databaseName=QuanLyDichVu1080;"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeOut=4;";

        // Kiểm tra nếu SDT đã tồn tại
        String checkCustomerSQL = "SELECT hoDemKH, TenKH FROM KHACHHANG WHERE SDT = ?";
        boolean isCustomerExists = false;

        String insertCallSQL = "INSERT INTO CUOCGOI (MaCG, SDT, MaDV, MaNV, batDauCG, thoiGianCG) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(url)) {
            try (PreparedStatement psCheck = con.prepareStatement(checkCustomerSQL)) {
                psCheck.setString(1, phone);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        // SDT đã tồn tại, tự động điền thông tin khách hàng
                        firstName = rs.getString("hoDemKH");
                        lastName = rs.getString("TenKH");
                        isCustomerExists = true;
                    }
                }
            }

            // Nếu SDT chưa tồn tại, thêm vào bảng KHACHHANG
            if (!isCustomerExists) {
                String insertCustomerSQL = "INSERT INTO KHACHHANG (SDT, hoDemKH, TenKH) VALUES (?, ?, ?)";
                try (PreparedStatement psCustomer = con.prepareStatement(insertCustomerSQL)) {
                    psCustomer.setString(1, phone);
                    psCustomer.setString(2, firstName);
                    psCustomer.setString(3, lastName);
                    psCustomer.executeUpdate();
                }
            }

            // Thêm dữ liệu vào bảng CUOCGOI
            try (PreparedStatement psCall = con.prepareStatement(insertCallSQL)) {
                psCall.setString(1, callId);
                psCall.setString(2, phone);
                psCall.setString(3, serviceCode);
                psCall.setString(4, employeeCode);
                psCall.setString(5, startDateTime);
                psCall.setString(6, callTime);
                psCall.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Lưu thông tin cuộc gọi thành công!", "Success", JOptionPane.INFORMATION_MESSAGE);

            if (parentTable != null) {
                DefaultTableModel model = (DefaultTableModel) parentTable.getModel();
                model.addRow(new Object[]{callId, phone, firstName, lastName, serviceCode, employeeCode, callTime, startDateTime});
            }

            dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu dữ liệu!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isCallIdExists(String callId) {
        String url = "jdbc:sqlserver://DESKTOP-0A5HI5T:1433;"
                + "user=sa;"
                + "password=123;"
                + "databaseName=QuanLyDichVu1080;"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeOut=4;";
        String query = "SELECT 1 FROM CUOCGOI WHERE MaCG = ?";

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, callId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Nếu có kết quả, mã cuộc gọi đã tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kiểm tra mã cuộc gọi!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public void setParentTable(JTable table) {
        this.parentTable = table;
    }
}