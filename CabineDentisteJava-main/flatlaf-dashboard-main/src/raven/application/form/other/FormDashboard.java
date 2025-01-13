package raven.application.form.other;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class FormDashboard extends JPanel {

    public FormDashboard() {
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Tableau de Bord", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Main content area
        JPanel contentPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        contentPanel.setBackground(getBackground());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(contentPanel, BorderLayout.CENTER);

        // Add charts
        contentPanel.add(createBarChartPanel());
        contentPanel.add(createPieChartPanel());
        /*contentPanel.add(createPlaceholderChartPanel("Factures"));
        contentPanel.add(createPlaceholderChartPanel("Rendez-vous"));*/

        // Stats panel
        add(createStatsPanel(), BorderLayout.SOUTH);
    }

    private JPanel createBarChartPanel() {
        // Data for bar chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(100, "Patients", "Janvier");
        dataset.addValue(170, "Patients", "Février");
        dataset.addValue(300, "Patients", "Mars");
        dataset.addValue(180, "Patients", "Avril");
        dataset.addValue(198, "Patients", "Mai");
        dataset.addValue(175, "Patients", "Juin");
        dataset.addValue(250, "Patients", "Juillet");
        dataset.addValue(50, "Patients", "Aout");
        dataset.addValue(40, "Patients", "Septembre");
        dataset.addValue(30, "Patients", "Octobre");
        dataset.addValue(50, "Patients", "Novembre");
        dataset.addValue(30, "Patients", "Decembre");

        // Create bar chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Nombre des patients pour mois",
                "Mois",
                "Nombre des patients",
                dataset
        );

        // Customize chart
        //chart.setBackgroundPaint(getBackground());
        chart.getTitle().setPaint(Color.WHITE);

        // Wrap chart in a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(200, 300));
        chartPanel.setBackground(new Color(30, 30, 30));

        return chartPanel;
    }

    private JPanel createPieChartPanel() {
        // Data for pie chart
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Complété", 40);
        dataset.setValue("En attente", 30);
        dataset.setValue("Annulé", 30);

        // Create pie chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Distribution des Rendez-vous",
                dataset,
                true, // legend
                true, // tooltips
                false // URLs
        );

        // Customize chart
        //chart.setBackgroundPaint(getBackground());
        chart.getTitle().setPaint(Color.WHITE);

        // Wrap chart in a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        chartPanel.setBackground(new Color(30, 30, 30));

        return chartPanel;
    }

    /*private JPanel createPlaceholderChartPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createLineBorder(new Color(54, 162, 235)));

        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(Color.WHITE);

        panel.add(label, BorderLayout.CENTER);
        return panel;
    }*/

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //statsPanel.setBackground(new Color(30, 30, 30));

        // Add stat cards
        statsPanel.add(createStatCard("Total des Patients", "120", new Color(46, 204, 113)));
        statsPanel.add(createStatCard("Taux de croissance", "15%", new Color(52, 152, 219)));
        statsPanel.add(createStatCard("Revenu mensuel", "5000Dh", new Color(231, 76, 60)));

        return statsPanel;
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(40, 40, 40));
        card.setBorder(BorderFactory.createLineBorder(color, 2));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueLabel.setForeground(color);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }
}
