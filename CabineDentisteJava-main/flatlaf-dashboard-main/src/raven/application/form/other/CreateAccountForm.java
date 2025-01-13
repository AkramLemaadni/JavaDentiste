package raven.application.form.other;

import Infermier.Infermier;
import raven.application.Application;
import raven.application.form.LoginForm;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CreateAccountForm extends JPanel {

    public CreateAccountForm() {
        initUI();
    }

    private void initUI() {
        // Set layout with padding and gaps
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Title Panel
        JLabel lblTitle = new JLabel("Créer un Nouveau Compte", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(46, 80, 119));
        add(lblTitle, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations Personnelles"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel lblName = new JLabel("Nom:");
        lblName.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblName, gbc);

        gbc.gridx = 1;
        JTextField txtName = new JTextField(20);
        formPanel.add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblPhone = new JLabel("Numéro Tel:");
        lblPhone.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblPhone, gbc);

        gbc.gridx = 1;
        JTextField txtPhone = new JTextField(20);
        formPanel.add(txtPhone, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblEmail, gbc);

        gbc.gridx = 1;
        JTextField txtEmail = new JTextField(20);
        formPanel.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblUsername = new JLabel("Nom d'utilisateur:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblUsername, gbc);

        gbc.gridx = 1;
        JTextField txtUsername = new JTextField(20);
        formPanel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel lblPassword = new JLabel("Mot de Passe:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(lblPassword, gbc);

        gbc.gridx = 1;
        JPasswordField txtPassword = new JPasswordField(20);
        formPanel.add(txtPassword, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton btnSave = new JButton("Créer Compte");
        btnSave.setBackground(new Color(46, 80, 119));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Arial", Font.BOLD, 14));
        btnSave.setFocusPainted(false);
        btnSave.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(btnSave);

        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listener for Button
        btnSave.addActionListener(e -> {
            String name = txtName.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            // Validate inputs
            if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Load existing users
            List<Infermier> infermiers = Infermier.loadFromFile("C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\acc.txt");
            int newId = infermiers.size() + 1;

            // Create new user and save
            Infermier newInfermier = new Infermier(newId, name, phone, email, username, password);
            infermiers.add(newInfermier);
            Infermier.saveAllToFile("C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\acc.txt", infermiers);

            JOptionPane.showMessageDialog(this, "Compte créé avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            Application.showForm(new LoginForm());
        });
    }
}
