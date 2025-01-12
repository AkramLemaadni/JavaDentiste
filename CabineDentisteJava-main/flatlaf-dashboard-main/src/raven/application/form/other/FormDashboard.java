package raven.application.form.other;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class FormDashboard extends JPanel {

    private JLabel lb;
    private JPanel graphPanel;

    public FormDashboard() {
        initComponents();
        addGraph();
    }

    private void initComponents() {
        // Title Label
        lb = new JLabel("Tableau De Bord", SwingConstants.CENTER);
        lb.setFont(new Font("Arial", Font.BOLD, 24));

        // Set up layout
        setLayout(new BorderLayout());

        // Add top panel with title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(lb, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Initialize graph panel
        graphPanel = new JPanel(new BorderLayout());
        add(graphPanel, BorderLayout.CENTER);
    }

    private void addGraph() {
        // Create a dataset for the graph
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(30, "Patients", "January");
        dataset.addValue(50, "Patients", "February");
        dataset.addValue(40, "Patients", "March");

        // Create a bar chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Number of Patients per Month", // Chart title
                "Month", // X-axis label
                "Number of Patients", // Y-axis label
                dataset // Data
        );

        // Create a ChartPanel and add it to the graph panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 400));
        graphPanel.add(chartPanel, BorderLayout.CENTER);
    }
}
