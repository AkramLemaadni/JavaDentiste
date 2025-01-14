package raven.application.form.other;

import Facture.Facture;
import Patient.Patient;
import RendezVous.Appointment;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FormDashboard extends JPanel {

    public FormDashboard() {
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Tableau de Bord", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // Main content area
        JPanel contentPanel = new JPanel(new GridLayout(2, 1, 10, 10)); // 2 rows for charts
        contentPanel.setBackground(getBackground());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(contentPanel, BorderLayout.CENTER);

        // Add both charts
        contentPanel.add(createBarChartPanel());
        contentPanel.add(createPieChartPanel());

        // Stats panel
        add(createStatsPanel(), BorderLayout.SOUTH);
    }

    private JPanel createBarChartPanel() {
        // Data for bar chart
        int[] patientPerMonth = calculatePatientPerMonth();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String[] months = {
                "Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"
        };

        for (int i = 0; i < 12; i++) {
            dataset.addValue(patientPerMonth[i], "Patients", months[i]);
        }

        // Create bar chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Nombre des Patients par Mois",
                "Mois",
                "Nombre des Patients",
                dataset
        );

        // Wrap chart in a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 400));

        return chartPanel;
    }

    private JPanel createPieChartPanel() {
        // Data for pie chart
        DefaultPieDataset dataset = calculateAppointmentStatus();

        // Create pie chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Statut des Rendez-vous",
                dataset,
                true, // legend
                true, // tooltips
                false // URLs
        );

        // Wrap chart in a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 400));

        return chartPanel;
    }

    private int[] calculatePatientPerMonth() {
        int[] patientCount = new int[12]; // Array to hold the count for each month
        List<Patient> patients = Patient.loadFromFile("C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\patients.txt");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Patient patient : patients) {
            try {
                LocalDate dateAdded = LocalDate.parse(patient.getDateAdded(), formatter);
                int monthIndex = dateAdded.getMonthValue() - 1; // Month is 1-based
                patientCount[monthIndex]++;
            } catch (Exception e) {
                System.out.println("Invalid date format for patient: " + patient.getName());
            }
        }

        return patientCount;
    }

    private DefaultPieDataset calculateAppointmentStatus() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<Appointment> appointments = Appointment.loadFromFile("C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\appointments.txt");

        int completed = 0;
        int pending = 0;
        int canceled = 0;

        for (Appointment appointment : appointments) {
            String status = appointment.getStatus().toLowerCase();
            switch (status) {
                case "complété":
                    completed++;
                    break;
                case "en attente":
                    pending++;
                    break;
                case "annulé":
                    canceled++;
                    break;
            }
        }

        dataset.setValue("Complété", completed);
        dataset.setValue("En attente", pending);
        dataset.setValue("Annulé", canceled);

        return dataset;
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add stat cards
        statsPanel.add(createStatCard("Total des Patients", String.valueOf(calculateTotalPatients()), new Color(46, 204, 113)));
        statsPanel.add(createStatCard("Total des Rendez-vous", String.valueOf(calculateTotalAppointments()), new Color(52, 152, 219)));
        statsPanel.add(createStatCard("Montant Total des Factures Payéé", calculateMontantTotal() + " Dh", new Color(231, 76, 60)));


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

    private int calculateTotalPatients() {
        List<Patient> patients = Patient.loadFromFile("C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\patients.txt");
        return patients.size();
    }

    private int calculateTotalAppointments() {
        List<Appointment> appointments = Appointment.loadFromFile("C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\appointments.txt");
        return appointments.size();
    }

    private double calculateMontantTotal() {
        List<Facture> factures = Facture.loadFromFile("C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\factures.txt");
        return factures.stream()
                .filter(facture -> facture.getDescription().equalsIgnoreCase("Payéé")) // Only include paid invoices
                .mapToDouble(Facture::getAmount) // Map to amounts
                .sum(); // Sum up the amounts
    }
}
