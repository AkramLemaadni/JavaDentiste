package raven.application.form.other;

import Infermier.Infermier;
import raven.application.Application;
import raven.application.form.LoginForm;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CreateAccountForm extends JPanel {

    public CreateAccountForm() {
        setLayout(new GridLayout(7, 2, 10, 10));
        JLabel lblName = new JLabel("Nom:");
        JTextField txtName = new JTextField();
        JLabel lblPhone = new JLabel("Numéro Tel:");
        JTextField txtPhone = new JTextField();
        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField();
        JLabel lblUsername = new JLabel("Nom d'utilisateur:");
        JTextField txtUsername = new JTextField();
        JLabel lblPassword = new JLabel("Mot de Passe:");
        JPasswordField txtPassword = new JPasswordField();
        JButton btnSave = new JButton("Créer Compte");

        add(lblName);
        add(txtName);
        add(lblPhone);
        add(txtPhone);
        add(lblEmail);
        add(txtEmail);
        add(lblUsername);
        add(txtUsername);
        add(lblPassword);
        add(txtPassword);
        add(new JLabel());
        add(btnSave);

        btnSave.addActionListener(e -> {
            String name = txtName.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String username = txtUsername.getText();
            String password = new String(txtPassword.getPassword());

            List<Infermier> infermiers = Infermier.loadFromFile("data/login.txt");
            int newId = infermiers.size() + 1;

            Infermier newInfermier = new Infermier(newId, name, phone, email, username, password);
            infermiers.add(newInfermier);
            Infermier.saveAllToFile("data/login.txt", infermiers);

            JOptionPane.showMessageDialog(this, "Compte créé avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            Application.showForm(new LoginForm());
        });
    }
}
