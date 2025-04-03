package Giaodien;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.*;

import javax.swing.*;

public class BarChartExample extends JFrame{
    public BarChartExample(){
        this.init();
        this.setVisible(true);
    }
    public void init(){
//        Biểu đồ cột (Bar Chart): Sử dụng ChartFactory.createBarChart(...).
        /**
         DefaultCategoryDataset dataset = new DefaultCategoryDataset();//để lưu trữ dữ liệu dạng bảng
         dataset.addValue(8, "Marks", "Maths");
         dataset.addValue(7, "Marks", "Science");
         dataset.addValue(9, "Marks", "English");
         dataset.addValue(6, "Marks", "Hindi");
         dataset.addValue(10, "Marks", "History");
         JFreeChart barChart = ChartFactory.createBarChart(
         "biểu đồ bán hàng",// tiêu đề
         "Category",// trục x
         "Score",// trục y
         dataset,// dữ liệu
         PlotOrientation.VERTICAL,//  Hướng biểu đồ: Dọc
         true, // Hiển thị chú thích
         true, // Hiển thị gợi ý (tooltips)
         false // Không sử dụng URL
         );
         **/
//        Biểu đồ đường (Line Chart): Sử dụng ChartFactory.createLineChart(...).
//        Biểu đồ tròn (Pie Chart): Sử dụng ChartFactory.createPieChart(...).
        /**
         DefaultPieDataset dataset = new DefaultPieDataset();
         dataset.setValue("Maths", 8);
         dataset.setValue("Science", 7);
         dataset.setValue("English", 9);
         dataset.setValue("Hindi", 6);
         dataset.setValue("History", 10);

         JFreeChart pieChart = ChartFactory.createPieChart(
         "Biểu đồ điểm số",  // Tiêu đề
         dataset,             // Dữ liệu
         true,                // Hiển thị chú thích
         true,                // Hiển thị gợi ý (tooltips)
         false                // Không sử dụng URL
         );

         **/
//        Biểu đồ phân tán (Scatter Plot): Sử dụng ChartFactory.createScatterPlot(...).
//        JFreeChart barChart = ChartFactory.createBarChart(
//                "biểu đồ bán hàng",// tiêu đề
//                "Category",// trục x
//                "Score",// trục y
//                dataset,// dữ liệu
//                PlotOrientation.VERTICAL,//  Hướng biểu đồ: Dọc
//                true, // Hiển thị chú thích
//                true, // Hiển thị gợi ý (tooltips)
//                false // Không sử dụng URL
//        );
        //tạo một panel để hiển thị
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();//để lưu trữ dữ liệu dạng bảng
        dataset.addValue(8, "Marks", "Maths");
        dataset.addValue(7, "Marks", "Science");
        dataset.addValue(9, "Marks", "English");
        dataset.addValue(6, "Marks", "Hindi");
        dataset.addValue(10, "Marks", "History");
        JFreeChart barChart = ChartFactory.createBarChart(
                "biểu đồ bán hàng",// tiêu đề
                "Category",// trục x
                "Score",// trục y
                dataset,// dữ liệu
                PlotOrientation.HORIZONTAL,//  Hướng biểu đồ: Dọc
                true, // Hiển thị chú thích
                true, // Hiển thị gợi ý (tooltips)
                false // Không sử dụng URL
        );
        ChartPanel chartPanel = new ChartPanel(barChart);
        setContentPane(chartPanel);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        BarChartExample example = new BarChartExample();
    }
}
