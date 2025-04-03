package Giaodien;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StatisticsFrame extends JFrame {
    private JComboBox<String> filterOptions;

    public StatisticsFrame() {
        setTitle("Thống kê số lượng cuộc gọi");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        filterOptions = new JComboBox<>(new String[]{"Thống kê theo giờ", "Thống kê theo tháng", "Thống kê theo năm"});
        JButton filterButton = new JButton("Xem thống kê");

        controlPanel.add(filterOptions);
        controlPanel.add(filterButton);

        JFreeChart barChart = ChartFactory.createBarChart(
                "Số lượng cuộc gọi",
                "Thời gian",
                "Số lượng cuộc gọi",
                createDataset("Thống kê theo giờ")
        );

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(760, 500));

        setLayout(new BorderLayout());
        add(controlPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);

        filterButton.addActionListener(e -> {
            String selectedOption = (String) filterOptions.getSelectedItem();
            JFreeChart updatedChart = ChartFactory.createBarChart(
                    "Số lượng cuộc gọi",
                    "Thời gian",
                    "Số lượng cuộc gọi",
                    createDataset(selectedOption)
            );
            chartPanel.setChart(updatedChart);
        });
    }

    private CategoryDataset createDataset(String option) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String url = "jdbc:sqlserver://vu:1433;user=sa;password=123;databaseName=QuanLyDichVu1080;encrypt=true;trustServerCertificate=true;loginTimeOut=4;";
        String query = "";

        if ("Thống kê theo giờ".equals(option)) {
            query = "SELECT DATEPART(HOUR, BatDauCG) AS Gio, COUNT(*) AS SoLuong " +
                    "FROM CUOCGOI GROUP BY DATEPART(HOUR, BatDauCG) ORDER BY Gio";
        } else if ("Thống kê theo tháng".equals(option)) {
            query = "SELECT FORMAT(BatDauCG, 'yyyy-MM') AS ThangNam, COUNT(*) AS SoLuong " +
                    "FROM CUOCGOI GROUP BY FORMAT(BatDauCG, 'yyyy-MM') ORDER BY ThangNam";
        } else if ("Thống kê theo năm".equals(option)) {
            query = "SELECT FORMAT(BatDauCG, 'yyyy') AS Nam, COUNT(*) AS SoLuong " +
                    "FROM CUOCGOI GROUP BY FORMAT(BatDauCG, 'yyyy') ORDER BY Nam";
        }

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String timeLabel = rs.getString(1);
                int count = rs.getInt("SoLuong");
                dataset.addValue(count, "Số lượng cuộc gọi", timeLabel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu thống kê!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StatisticsFrame frame = new StatisticsFrame();
            frame.setVisible(true);
        });
    }
}
