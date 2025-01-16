package Giaodien;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;


/// //
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Comparator;

/// ///


public class Display extends JFrame implements MouseListener {
    private Image backgroundImage;
    private JFrame frame;
    private JPanel dichVuPn, quanLyPn, dangXuatPn, infoQuanLy, infoDichVu, chinhSuaPn, mainPanel;
    private JTable statisticsTable;
    String maCGValue, soDienThoaiValue, hoDemKHValue, tenKHValue, maDVValue, maNVValue, thoiGianCGValue, batDauCGValue;
    private Connection connection;

    public Display() {
        frame = new JFrame("Thông tin dịch vụ 1080");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(1800, 900);
        frame.setLocationRelativeTo(null);
        ImageIcon iconvnpt = new ImageIcon("vnpt_logo.png");
        frame.setIconImage(iconvnpt.getImage());
        frame.setVisible(true);

        // VNPT thông tin dịch vụ 1080
        ImageIcon icon = new ImageIcon("vnpt.jpg");
        Image img = icon.getImage().getScaledInstance(1800, 200, Image.SCALE_SMOOTH);
        JLabel vnpt = new JLabel(new ImageIcon(img)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Tùy chỉnh vị trí và vẽ chữ
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.WHITE); // Màu chữ
                g2d.setFont(new Font("Arial", Font.BOLD, 35)); // Font chữ

                // Vị trí chữ (x, y)
                int x = 550; // Tọa độ ngang
                int y = 115; // Tọa độ dọc
                g2d.drawString("THÔNG TIN DỊCH VỤ 1080", x, y);
            }
        };
        vnpt.setBounds(0, 0, 1800, 200);

        frame.add(vnpt);

        // tạo thanh menu
        JPanel menu = new JPanel();
        menu.setLayout(null);
        menu.setBackground(Color.gray);
        menu.setBounds(0, 200, 300, 700);

        // dịch vụ 1080
        dichVuPn = new JPanel();
        dichVuPn.setLayout(null);
        dichVuPn.setBounds(10, 40, 280, 100);
        dichVuPn.setBackground(new Color(0, 102, 255));
        dichVuPn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dichVuPn.addMouseListener(this);
        JLabel dichVuLb = new JLabel("Dịch vụ 1080");
        dichVuLb.setFont(new Font("Arial", Font.BOLD, 19));
        dichVuLb.setForeground(Color.white);
        dichVuLb.setBounds(80, 10, 120, 80);
        ImageIcon dichVuIcon = new ImageIcon("manage_search.png");
        JLabel iconDichVu = new JLabel(dichVuIcon);
        iconDichVu.setBounds(15, 20, 55, 55);

        dichVuPn.add(dichVuLb);
        dichVuPn.add(iconDichVu);
        menu.add(dichVuPn);

        // Quản lý thông tin
        quanLyPn = new JPanel();
        quanLyPn.setLayout(null);
        quanLyPn.setBounds(10, 220, 280, 100);
        quanLyPn.setBackground(new Color(0, 102, 255));
        quanLyPn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        quanLyPn.addMouseListener(this);
        JLabel quanLyLb = new JLabel("Quản lý thông tin");
        quanLyLb.setFont(new Font("Arial", Font.BOLD, 19));
        quanLyLb.setForeground(Color.white);
        quanLyLb.setBounds(80, 10, 160, 80);
        ImageIcon quanLyIcon = new ImageIcon("list.png");
        JLabel iconQuanLy = new JLabel(quanLyIcon);
        iconQuanLy.setBounds(15, 20, 55, 55);

        quanLyPn.add(quanLyLb);
        quanLyPn.add(iconQuanLy);
        menu.add(quanLyPn);

        // Đăng xuất
        dangXuatPn = new JPanel();
        dangXuatPn.setLayout(null);
        dangXuatPn.setBounds(65, 570, 170, 70);
        dangXuatPn.setBackground(new Color(194, 0, 0));
        dangXuatPn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dangXuatPn.addMouseListener(this);
        JLabel dangXuatLb = new JLabel("Đăng xuất");
        dangXuatLb.setFont(new Font("Arial", Font.BOLD, 19));
        dangXuatLb.setForeground(new Color(217, 217, 217));
        dangXuatLb.setBounds(70, 10, 100, 50);
        ImageIcon dangXuatIcon = new ImageIcon("logout.png");
        JLabel iconDangXuat = new JLabel(dangXuatIcon);
        iconDangXuat.setBounds(5, 8, 55, 55);

        dangXuatPn.add(dangXuatLb);
        dangXuatPn.add(iconDangXuat);
        menu.add(dangXuatPn);

        frame.add(menu);
        frame.setResizable(false);

        //-------------------------- THÔNG TIN DỊCH VỤ ---------------------------------
        //---------------------------------- Bảng dịch vụ
        infoDichVu = new JPanel();
        infoDichVu.setLayout(null);
        infoDichVu.setBounds(0, 0, 1445, 620);

        JLabel dv1080 = new JLabel("DỊCH VỤ 1080");
        dv1080.setFont(new Font("Arial", Font.BOLD, 30));
        dv1080.setForeground(new Color(0, 102, 255));
        dv1080.setBounds(570, 0, 200, 50);
        infoDichVu.add(dv1080);

        // Bảng statisticsTable
        DefaultTableModel tableDV = new DefaultTableModel(new String[]{"Mã dịch vụ","Tên dịch vụ","Giá (VNĐ/phút)"}, 0);
        statisticsTable = new JTable(tableDV) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa ô
            }
        };

        // Tự động điều chỉnh chiều cao hàng dựa trên nội dung
        statisticsTable.setRowHeight(50);
        statisticsTable.setRowMargin(5);

        // Căn giữa nội dung cho các cột "Mã DV" và "Giá"
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        statisticsTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        statisticsTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        statisticsTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        // Điều chỉnh độ rộng các cột
        statisticsTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Mã DV
        statisticsTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Tên DV
        statisticsTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Giá

        // Thiết lập tiêu đề bảng
        statisticsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 22));
        statisticsTable.getTableHeader().setPreferredSize(new Dimension(100, 40));

        // Thiết lập nội dung bảng
        statisticsTable.setFont(new Font("Arial", Font.PLAIN, 20));


        // Thêm JScrollPane chứa JTable
        JScrollPane dichVuSP = new JScrollPane(statisticsTable);
        dichVuSP.setBounds(0, 60, 1445, 200);

        // Kết nối cơ sở dữ liệu và hiển thị dữ liệu
        String url = "jdbc:sqlserver://DESKTOP-0A5HI5T:1433;"
                + "user=sa;"
                + "password=123;"
                + "databaseName=QuanLyDichVu1080;"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeOut=4;";

        try (Connection con = DriverManager.getConnection(url)) {
            String sql = "SELECT MaDV, TenDV, Gia\n" +
                    "FROM DICHVU";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Thêm dữ liệu vào JTable
            while (rs.next()) {
                String maDV = rs.getString("MaDV");
                String tenDV = rs.getString("TenDV");
                String gia = rs.getString("Gia");
                tableDV.addRow(new Object[]{maDV, tenDV, gia});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        infoDichVu.add(dichVuSP); // Thêm bảng Dịch Vụ

        //---------------------------------- Bảng nhân viên
        JLabel nv = new JLabel("NHÂN VIÊN");
        nv.setFont(new Font("Arial", Font.BOLD, 30));
        nv.setForeground(new Color(0, 102, 255));
        nv.setBounds(580, 290, 200, 50);
        infoDichVu.add(nv);

        // Bảng statisticsTable
        DefaultTableModel tableNV = new DefaultTableModel(new String[]{"Mã nhân viên","Họ đệm nhân viên","Tên nhân viên"}, 0);
        statisticsTable = new JTable(tableNV);

        // Tự động điều chỉnh chiều cao hàng dựa trên nội dung
        statisticsTable.setRowHeight(50);
        statisticsTable.setRowMargin(5);

        // Căn giữa nội dung cho các cột "Mã DV" và "Giá"
        DefaultTableCellRenderer canGiua = new DefaultTableCellRenderer();
        canGiua.setHorizontalAlignment(SwingConstants.CENTER);
        statisticsTable.getColumnModel().getColumn(0).setCellRenderer(canGiua); // Mã NV
        statisticsTable.getColumnModel().getColumn(1).setCellRenderer(canGiua); // Họ đệm NV
        statisticsTable.getColumnModel().getColumn(2).setCellRenderer(canGiua); // Tên NV

        // Điều chỉnh độ rộng các cột
        statisticsTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Mã NV
        statisticsTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Họ đệm NV
        statisticsTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Tên NV

        // Thiết lập tiêu đề bảng
        statisticsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 22));
        statisticsTable.getTableHeader().setPreferredSize(new Dimension(100, 40));

        // Thiết lập nội dung bảng
        statisticsTable.setFont(new Font("Arial", Font.PLAIN, 20));

        // Thêm JScrollPane chứa JTable
        JScrollPane nhanVienSP = new JScrollPane(statisticsTable);
        nhanVienSP.setBounds(0, 350, 1445, 270);

        try (Connection con = DriverManager.getConnection(url)) {
            String sql = "SELECT MaNV, HoDemNV, TenNV\n" +
                    "FROM NHANVIEN";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Thêm dữ liệu vào JTable
            while (rs.next()) {
                String maNV = rs.getString("MaNV");
                String hoNV = rs.getString("HoDemNV");
                String tenNV = rs.getString("TenNV");
                tableNV.addRow(new Object[]{maNV, hoNV, tenNV});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        infoDichVu.add(nhanVienSP); // Thêm bảng Nhân Viên

        //-------------------------------- THÔNG TIN QUẢN LÝ ------------------------------------
        infoQuanLy = new JPanel();
        infoQuanLy.setLayout(null);
        infoQuanLy.setBounds(0, 0, 1445, 620);

        chinhSuaPn = new JPanel();
        chinhSuaPn.setLayout(null);
        chinhSuaPn.setBounds(1010, 0, 435, 620);

        // Bảng statisticsTable
        DefaultTableModel tableCG = new DefaultTableModel(new String[]{"Mã Cuộc Gọi","Số Điện Thoại","Họ Đệm KH","Tên KH","Mã DV","Mã NV","Thời Gian Cuộc Gọi","Bắt Đầu Cuộc Gọi"}, 0);
        statisticsTable = new JTable(tableCG);

        // Tự động điều chỉnh chiều cao hàng dựa trên nội dung
        statisticsTable.setRowHeight(50);
        statisticsTable.setRowMargin(5);

        // Căn giữa nội dung cho các cột "Mã DV" và "Giá"
//        DefaultTableCellRenderer canGiua = new DefaultTableCellRenderer();
        canGiua.setHorizontalAlignment(SwingConstants.CENTER);
        statisticsTable.getColumnModel().getColumn(0).setCellRenderer(canGiua);
        statisticsTable.getColumnModel().getColumn(1).setCellRenderer(canGiua);
        statisticsTable.getColumnModel().getColumn(2).setCellRenderer(canGiua);
        statisticsTable.getColumnModel().getColumn(3).setCellRenderer(canGiua);
        statisticsTable.getColumnModel().getColumn(4).setCellRenderer(canGiua);
        statisticsTable.getColumnModel().getColumn(5).setCellRenderer(canGiua);
        statisticsTable.getColumnModel().getColumn(6).setCellRenderer(canGiua);
        statisticsTable.getColumnModel().getColumn(7).setCellRenderer(canGiua);

        // Điều chỉnh độ rộng các cột
        statisticsTable.getColumnModel().getColumn(0).setPreferredWidth(170);
        statisticsTable.getColumnModel().getColumn(1).setPreferredWidth(190);
        statisticsTable.getColumnModel().getColumn(2).setPreferredWidth(170);
        statisticsTable.getColumnModel().getColumn(3).setPreferredWidth(130);
        statisticsTable.getColumnModel().getColumn(4).setPreferredWidth(130);
        statisticsTable.getColumnModel().getColumn(5).setPreferredWidth(130);
        statisticsTable.getColumnModel().getColumn(6).setPreferredWidth(270);
        statisticsTable.getColumnModel().getColumn(7).setPreferredWidth(270);


        // Thiết lập tiêu đề bảng
        statisticsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        statisticsTable.getTableHeader().setPreferredSize(new Dimension(100, 40));

        // Thiết lập nội dung bảng
        statisticsTable.setFont(new Font("Arial", Font.PLAIN, 16));

        // Thêm JScrollPane chứa JTable
        JScrollPane cuocGoiSP = new JScrollPane(statisticsTable);
        cuocGoiSP.setBounds(0, 0, 1000, 550);

        try (Connection con = DriverManager.getConnection(url)) {
            String sql = "SELECT CG.MaCG, CG.SDT, KH.HoDemKH, KH.TenKH, CG.MaDV, CG.MaNV, CG.ThoiGianCG, CG.BatDauCG\n" +
                    "FROM CUOCGOI CG\n" +
                    "JOIN KHACHHANG KH ON CG.SDT = KH.SDT;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Thêm dữ liệu vào JTable
            while (rs.next()) {
                String maCG = rs.getString("MaCG");
                String soDienThoai = rs.getString("SDT");
                String hoDemKH = rs.getString("HoDemKH");
                String tenKH = rs.getString("TenKH");
                String maDV = rs.getString("MaDV");
                String maNV = rs.getString("MaNV");
                String thoiGianCG = rs.getString("ThoiGianCG");
                String batDauCGCG = rs.getString("BatDauCG");
                tableCG.addRow(new Object[]{maCG, soDienThoai, hoDemKH, tenKH, maDV, maNV, thoiGianCG, batDauCGCG});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        infoQuanLy.add(cuocGoiSP);

        // Tìm kiếm
        JLabel search = new JLabel("Tìm kiếm");
        search.setFont(new Font("Arial", Font.BOLD, 30));
        search.setBounds(200, 560, 140, 50);
        infoQuanLy.add(search);
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.BOLD, 20));
        searchField.setBounds(350, 560, 300, 50);
        infoQuanLy.add(searchField);
        ImageIcon searchIcon = new ImageIcon("search.png");
        JButton searchButton = new JButton(searchIcon);
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBorderPainted(false);
        searchButton.setBackground(Color.white);
        searchButton.setBounds(660, 560, 50,50);
        infoQuanLy.add(searchButton);

        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim().toLowerCase(); // Lấy từ khóa tìm kiếm
            DefaultTableModel model = (DefaultTableModel) statisticsTable.getModel();

            // Nếu từ khóa rỗng hiển thị lỗi
            if (keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tìm kiếm trong bảng
            DefaultTableModel originalModel = getOriginalData(); // Lấy dữ liệu gốc từ database
            model.setRowCount(0); // Xóa dữ liệu cũ

            // Duyệt qua dữ liệu gốc và thêm hàng phù hợp
            for (int i = 0; i < originalModel.getRowCount(); i++) {
                String maCG = originalModel.getValueAt(i, 0).toString().toLowerCase();
                String tenKH = originalModel.getValueAt(i, 3).toString().toLowerCase();
                if (maCG.contains(keyword) || tenKH.contains(keyword)) {
                    model.addRow(new Object[]{
                            originalModel.getValueAt(i, 0),
                            originalModel.getValueAt(i, 1),
                            originalModel.getValueAt(i, 2),
                            originalModel.getValueAt(i, 3),
                            originalModel.getValueAt(i, 4),
                            originalModel.getValueAt(i, 5),
                            originalModel.getValueAt(i, 6),
                            originalModel.getValueAt(i, 7),
                    });
                }
            }
        });


        // Thêm, xóa, sửa thông tin
        JLabel maCG = new JLabel("Mã cuộc gọi:");
        maCG.setFont(new Font("Arial", Font.BOLD, 22));
        maCG.setBounds(10, 20, 130, 50);
        JTextField maCGtf = new JTextField();
        maCGtf.setFont(new Font("Arial", Font.BOLD, 20));
        maCGtf.setBounds(145, 20, 275, 50);
        chinhSuaPn.add(maCG);
        chinhSuaPn.add(maCGtf);

        JLabel sdt = new JLabel("Số điện thoại:");
        sdt.setFont(new Font("Arial", Font.BOLD, 22));
        sdt.setBounds(10, 80, 150, 50);
        JTextField sdttf = new JTextField();
        sdttf.setFont(new Font("Arial", Font.BOLD, 20));
        sdttf.setBounds(160, 80, 260, 50);
        chinhSuaPn.add(sdt);
        chinhSuaPn.add(sdttf);

        JLabel hoKH = new JLabel("Họ khách hàng:");
        hoKH.setFont(new Font("Arial", Font.BOLD, 22));
        hoKH.setBounds(10, 140, 163, 50);
        JTextField hoKHtf = new JTextField();
        hoKHtf.setFont(new Font("Arial", Font.BOLD, 20));
        hoKHtf.setBounds(178, 140, 242, 50);
        chinhSuaPn.add(hoKH);
        chinhSuaPn.add(hoKHtf);

        JLabel tenKH = new JLabel("Tên khách hàng:");
        tenKH.setFont(new Font("Arial", Font.BOLD, 22));
        tenKH.setBounds(10, 200, 180, 50);
        JTextField tenKHtf = new JTextField();
        tenKHtf.setFont(new Font("Arial", Font.BOLD, 20));
        tenKHtf.setBounds(192, 200, 228, 50);
        chinhSuaPn.add(tenKH);
        chinhSuaPn.add(tenKHtf);

        JLabel maDV = new JLabel("Mã dịch vụ:");
        maDV.setFont(new Font("Arial", Font.BOLD, 22));
        maDV.setBounds(10, 260, 120, 50);
        // ComboBox cho maDV
        String[] arrMaDV = {"DV01", "DV02", "DV03", "DV04", "DV05", "DV06", "DV07", "DV08", "DV09", "DV10"};
        JComboBox<String> cbMaDV = new JComboBox<>(arrMaDV);
        cbMaDV.setFont(new Font("Arial", Font.BOLD, 22));
        cbMaDV.setBounds(135, 260, 285, 50);
        chinhSuaPn.add(maDV);
        chinhSuaPn.add(cbMaDV);

        JLabel maNV = new JLabel("Mã nhân viên:");
        maNV.setFont(new Font("Arial", Font.BOLD, 22));
        maNV.setBounds(10, 320, 220, 50);
        JTextField maNVtf = new JTextField();
        maNVtf.setFont(new Font("Arial", Font.BOLD, 20));
        maNVtf.setBounds(228, 320, 192, 50);
        chinhSuaPn.add(maNV);
        chinhSuaPn.add(maNVtf);

        JLabel thoiGianCG = new JLabel("Thời gian cuộc gọi:");
        thoiGianCG.setFont(new Font("Arial", Font.BOLD, 22));
        thoiGianCG.setBounds(10, 380, 200, 50);
        JTextField thoiGianCGtf = new JTextField();
        thoiGianCGtf.setFont(new Font("Arial", Font.BOLD, 20));
        thoiGianCGtf.setBounds(215, 380, 205, 50);
        chinhSuaPn.add(thoiGianCG);
        chinhSuaPn.add(thoiGianCGtf);

        JLabel batDauCG = new JLabel("Bắt đầu cuộc gọi:");
        batDauCG.setFont(new Font("Arial", Font.BOLD, 22));
        batDauCG.setBounds(10, 440, 180, 50);
        JTextField batDauCGtf = new JTextField();
        batDauCGtf.setFont(new Font("Arial", Font.BOLD, 20));
        batDauCGtf.setBounds(195, 440, 225, 50);
        chinhSuaPn.add(batDauCG);
        chinhSuaPn.add(batDauCGtf);

        // Sự kiện chọn dòng trong bảng
        statisticsTable.getSelectionModel().addListSelectionListener(event -> {
            // Chỉ xử lý khi không còn thao tác nào khác trên bảng
            if (!event.getValueIsAdjusting()) {
                // Lấy thông tin của dòng được chọn
                int selectedRow = statisticsTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Lấy dữ liệu từ các cột của dòng được chọn
                    maCGValue = (String) statisticsTable.getValueAt(selectedRow, 0);
                    soDienThoaiValue = (String) statisticsTable.getValueAt(selectedRow, 1);
                    hoDemKHValue = (String) statisticsTable.getValueAt(selectedRow, 2);
                    tenKHValue = (String) statisticsTable.getValueAt(selectedRow, 3);
                    maDVValue = (String) statisticsTable.getValueAt(selectedRow, 4);
                    maNVValue = (String) statisticsTable.getValueAt(selectedRow, 5);
                    thoiGianCGValue = (String) statisticsTable.getValueAt(selectedRow, 6);
                    batDauCGValue = (String) statisticsTable.getValueAt(selectedRow, 7);

                    // Gán giá trị vào các textfield và combobox
                    maCGtf.setText(maCGValue);
                    sdttf.setText(soDienThoaiValue);
                    hoKHtf.setText(hoDemKHValue);
                    tenKHtf.setText(tenKHValue);
                    cbMaDV.setSelectedItem(maDVValue);
                    maNVtf.setText(maNVValue);
                    thoiGianCGtf.setText(thoiGianCGValue);
                    batDauCGtf.setText(batDauCGValue);
                }
            }
        });

        // THÊM
        JButton addButton = new JButton("Thêm");
        addButton.setBounds(45, 505, 100, 50);
        addButton.setFont(new Font("Arial", Font.PLAIN, 18));
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        chinhSuaPn.add(addButton);

        addButton.addActionListener(e -> {
            AddCallFrame addFrame = new AddCallFrame();
            addFrame.setParentTable(statisticsTable); // Truyền bảng từ Display
            addFrame.setVisible(true);
        });

        // SỬA
        JButton editButton = new JButton("Sửa");
        editButton.setBounds(165, 505, 100, 50);
        editButton.setFont(new Font("Arial", Font.PLAIN, 18));
        editButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        chinhSuaPn.add(editButton);

        editButton.addActionListener(e -> {
            int selectedRow = statisticsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để chỉnh sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return; // Không có dòng nào được chọn
            }

            // Hiển thị hộp thoại xác nhận
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có muốn chỉnh sửa thông tin cuộc gọi này không?",
                    "Xác nhận chỉnh sửa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String maCGEdit = maCGtf.getText();
                String sdtEdit = sdttf.getText();
                String hoKHEdit = hoKHtf.getText();
                String tenKHEdit = tenKHtf.getText();
                String maDVEdit = cbMaDV.getSelectedItem().toString();
                String maNVEdit = maNVtf.getText();
                String thoiGianCGEdit = thoiGianCGtf.getText();
                String batDauCGEdit = batDauCGtf.getText();

                // Cập nhật CSDL CUOCGOI
                String updateCGSQL = "UPDATE CUOCGOI SET MaCG = ?, MaDV = ?, MaNV = ?, ThoiGianCG = ?, BatDauCG = ? WHERE MaCG = ?";
                // Cập nhật CSDL KHACHHANG
                String updateKHSQL = "UPDATE KHACHHANG SET SDT = ?, HoDemKH = ?, TenKH = ? WHERE SDT = ?";

                try (Connection con = DriverManager.getConnection(url)) {
                    // Cập nhật CUOCGOI
                    try (PreparedStatement psCG = con.prepareStatement(updateCGSQL)) {
                        psCG.setString(1, maCGEdit);
                        psCG.setString(2, maDVEdit);
                        psCG.setString(3, maNVEdit);
                        psCG.setString(4, thoiGianCGEdit);
                        psCG.setString(5, batDauCGEdit);
                        psCG.setString(6, maCGValue);
                        psCG.executeUpdate();
                    }

                    // Cập nhật KHACHHANG
                    try (PreparedStatement psKH = con.prepareStatement(updateKHSQL)) {
                        psKH.setString(1, sdtEdit);
                        psKH.setString(2, hoKHEdit);
                        psKH.setString(3, tenKHEdit);
                        psKH.setString(4, soDienThoaiValue);
                        psKH.executeUpdate();
                    }

                    // Cập nhật dữ liệu trong JTable
                    statisticsTable.setValueAt(maCGEdit, selectedRow, 0);
                    statisticsTable.setValueAt(sdtEdit, selectedRow, 1);
                    statisticsTable.setValueAt(hoKHEdit, selectedRow, 2);
                    statisticsTable.setValueAt(tenKHEdit, selectedRow, 3);
                    statisticsTable.setValueAt(maDVEdit, selectedRow, 4);
                    statisticsTable.setValueAt(maNVEdit, selectedRow, 5);
                    statisticsTable.setValueAt(thoiGianCGEdit, selectedRow, 6);
                    statisticsTable.setValueAt(batDauCGEdit, selectedRow, 7);

                    JOptionPane.showMessageDialog(frame, "Cập nhật thành công!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Lỗi khi cập nhật dữ liệu!");
                }
            }
        });

        // XÓA
        JButton deleteButton = new JButton("Xóa");
        deleteButton.setBounds(285, 505, 100, 50);
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 18));
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        chinhSuaPn.add(deleteButton);

        deleteButton.addActionListener(e -> {
            int selectedRow = statisticsTable.getSelectedRow(); // Lấy dòng được chọn
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return; // Không có dòng nào được chọn
            }

            // Hiển thị hộp thoại xác nhận
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa thông tin cuộc gọi này không?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                /// Lấy mã cuộc gọi từ dòng được chọn
                String callId = statisticsTable.getValueAt(selectedRow, 0).toString();

                // Xóa trong cơ sở dữ liệu
                String deleteSQL = "DELETE FROM CUOCGOI WHERE MaCG = ?";
                try (Connection con = DriverManager.getConnection(url);
                     PreparedStatement ps = con.prepareStatement(deleteSQL)) {
                    ps.setString(1, callId);
                    ps.executeUpdate();

                    // Xóa trong JTable
                    DefaultTableModel model = (DefaultTableModel) statisticsTable.getModel();
                    model.removeRow(selectedRow);

                    JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Lỗi khi xóa dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // SẮP XẾP
        JButton sortButton = new JButton("Sắp xếp");
        sortButton.setBounds(110, 565, 100, 50);
        sortButton.setFont(new Font("Arial", Font.PLAIN, 18));
        sortButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        chinhSuaPn.add(sortButton);

        sortButton.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) statisticsTable.getModel();
            int rowCount = model.getRowCount();
            int columnCount = model.getColumnCount();

            // Chuyển dữ liệu từ JTable sang mảng để sắp xếp
            Object[][] data = new Object[rowCount][columnCount];
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    data[i][j] = model.getValueAt(i, j);
                }
            }

            // Chọn tiêu chí sắp xếp
            String[] options = {"Mã Cuộc Gọi", "Tên Khách Hàng", "Bắt Đầu Cuộc Gọi"};
            int criteria = JOptionPane.showOptionDialog(
                    frame,
                    "Chọn tiêu chí sắp xếp:",
                    "Sắp xếp",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (criteria == -1) return; // Người dùng hủy

            // Chọn hướng sắp xếp
            String[] orderOptions = {"Tăng dần", "Giảm dần"};
            int order = JOptionPane.showOptionDialog(
                    frame,
                    "Chọn thứ tự sắp xếp:",
                    "Sắp xếp",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    orderOptions,
                    orderOptions[0]
            );

            if (order == -1) return; // Người dùng hủy

            // Xác định cột sắp xếp
            Comparator<Object[]> comparator = null;
            if (criteria == 0) { // Sắp xếp theo Mã Cuộc Gọi
                comparator = Comparator.comparing(o -> o[0].toString());
            } else if (criteria == 1) { // Sắp xếp theo Tên Khách Hàng
                comparator = Comparator.comparing(o -> o[3].toString());
            } else if (criteria == 2) { // Sắp xếp theo Bắt Đầu Cuộc Gọi
                comparator = Comparator.comparing(o -> o[7].toString());
            }

            if (order == 1) { // Nếu chọn Giảm dần
                comparator = comparator.reversed();
            }

            // Sắp xếp dữ liệu
            if (comparator != null) {
                Arrays.sort(data, comparator);
            }

            // Làm mới dữ liệu trong bảng
            model.setRowCount(0); // Xóa tất cả các hàng cũ
            for (Object[] row : data) {
                model.addRow(row); // Thêm hàng mới đã sắp xếp
            }
        });

        // Tải lại trang
        JButton reloadButton = new JButton("Reload");
        reloadButton.setBounds(230, 565, 100, 50);
        reloadButton.setFont(new Font("Arial", Font.PLAIN, 18));
        reloadButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        chinhSuaPn.add(reloadButton);

        reloadButton.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) statisticsTable.getModel();
            model.setRowCount(0); // Xóa dữ liệu cũ
            loadInitialData(model); // Hàm load dữ liệu ban đầu
        });

        infoQuanLy.add(chinhSuaPn);

        mainPanel = new JPanel(null);
        mainPanel.setBounds(320, 220, 1445, 620);
        mainPanel.add(infoDichVu);
        mainPanel.add(infoQuanLy);
        frame.add(mainPanel);

        infoDichVu.setVisible(true); // Hiển thị Dịch Vụ khi chạy
        infoQuanLy.setVisible(false);  // Ẩn Quản lý thông tin

        dichVuPn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Hiển thị infoDichVu và ẩn infoQuanLy
                infoQuanLy.setVisible(false);
                infoDichVu.setVisible(true);
            }
        });

        quanLyPn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Hiển thị infoQuanLy và ẩn infoDichVu
                infoDichVu.setVisible(false);
                infoQuanLy.setVisible(true);
            }
        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == dangXuatPn){
            // Hiển thị hộp thoại xác nhận đăng xuất
            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Bạn có chắc chắn muốn thoát ứng dụng không?",
                    "Xác nhận đăng xuất",
                    JOptionPane.YES_NO_OPTION);

            // Nếu người dùng chọn YES, thoát ứng dụng
            if (confirm == JOptionPane.YES_OPTION) {
                new LoginFormGUI();
                frame.dispose(); // Đóng cửa sổ hiện tại
            }
        }
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == dichVuPn) {
            dichVuPn.setBackground(new Color(96, 100, 191));
        } else if (e.getSource() == quanLyPn) {
            quanLyPn.setBackground(new Color(96, 100, 191));
        } else if (e.getSource() == dangXuatPn) {
            dangXuatPn.setBackground(new Color(255, 77, 77));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == dichVuPn) {
            dichVuPn.setBackground(new Color(0, 102, 255));
        } else if (e.getSource() == quanLyPn) {
            quanLyPn.setBackground(new Color(0, 102, 255));
        } else if (e.getSource() == dangXuatPn) {
            dangXuatPn.setBackground(new Color(194, 0, 0));
        }
    }

    private DefaultTableModel getOriginalData() {
        DefaultTableModel originalModel = new DefaultTableModel(new String[]{
                "Mã Cuộc Gọi", "Số Điện Thoại", "Họ Đệm KH", "Tên KH", "Mã DV", "Mã NV", "Thời Gian Cuộc Gọi", "Bắt Đầu Cuộc Gọi"
        }, 0);

        String url = "jdbc:sqlserver://DESKTOP-0A5HI5T:1433;"
                + "user=sa;password=123;databaseName=QuanLyDichVu1080;encrypt=true;trustServerCertificate=true;loginTimeOut=4;";

        try (Connection con = DriverManager.getConnection(url)) {
            String sql = "SELECT CG.MaCG, CG.SDT, KH.HoDemKH, KH.TenKH, CG.MaDV, CG.MaNV, CG.ThoiGianCG, CG.BatDauCG "
                    + "FROM CUOCGOI CG JOIN KHACHHANG KH ON CG.SDT = KH.SDT;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                originalModel.addRow(new Object[]{
                        rs.getString("MaCG"), rs.getString("SDT"), rs.getString("HoDemKH"),
                        rs.getString("TenKH"), rs.getString("MaDV"), rs.getString("MaNV"),
                        rs.getString("ThoiGianCG"), rs.getString("BatDauCG")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return originalModel;
    }

    private void loadInitialData(DefaultTableModel model) {
        String url = "jdbc:sqlserver://DESKTOP-0A5HI5T:1433;"
                + "user=sa;password=123;databaseName=QuanLyDichVu1080;encrypt=true;trustServerCertificate=true;loginTimeOut=4;";

        try (Connection con = DriverManager.getConnection(url)) {
            String sql = "SELECT CG.MaCG, CG.SDT, KH.HoDemKH, KH.TenKH, CG.MaDV, CG.MaNV, CG.ThoiGianCG, CG.BatDauCG "
                    + "FROM CUOCGOI CG JOIN KHACHHANG KH ON CG.SDT = KH.SDT;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            model.setRowCount(0); // Xóa dữ liệu cũ trong bảng
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("MaCG"), rs.getString("SDT"), rs.getString("HoDemKH"),
                        rs.getString("TenKH"), rs.getString("MaDV"), rs.getString("MaNV"),
                        rs.getString("ThoiGianCG"), rs.getString("BatDauCG")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ cơ sở dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

}

