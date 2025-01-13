package raven.application.form;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.application.Application;
import Infermier.Infermier;

import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import raven.application.form.other.CreateAccountForm;

/**
 *
 * @author Raven
 */
public class LoginForm extends javax.swing.JPanel {

    public LoginForm() {
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("al center center"));

        panelLogin1.setBackground(new Color(77, 161, 169));
        lbTitle.putClientProperty(FlatClientProperties.STYLE, "font:$h1.font");
        txtPass.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true;showCapsLock:true");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, "borderWidth:0;focusWidth:0");
        txtUser.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nom d'utilisateur");
        txtPass.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mot de passe");
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        panelLogin1 = new raven.application.form.PanelLogin();
        lbTitle = new javax.swing.JLabel();
        lbUser = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        lbPass = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        cmdLogin = new javax.swing.JButton();
        cmdCreateAccount = new javax.swing.JButton();

        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Connexion à Sanati");
        panelLogin1.add(lbTitle);

        lbUser.setText("Nom d'utilisateur");
        panelLogin1.add(lbUser);
        panelLogin1.add(txtUser);

        lbPass.setText("Mot de passe");
        panelLogin1.add(lbPass);
        panelLogin1.add(txtPass);

        cmdLogin.setText("S'Authentifier");
        cmdLogin.addActionListener(evt -> cmdLoginActionPerformed(evt));
        panelLogin1.add(cmdLogin);

        cmdCreateAccount.setText("Créer Nouveau Compte");
        cmdCreateAccount.addActionListener(evt -> cmdCreateAccountActionPerformed(evt)); // Attach the ActionListener
        panelLogin1.add(cmdCreateAccount);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(218, Short.MAX_VALUE)
                                .addComponent(panelLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(197, 197, 197))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(panelLogin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(96, Short.MAX_VALUE))
        );
    }

    private void cmdLoginActionPerformed(java.awt.event.ActionEvent evt) {
        String username = txtUser.getText();
        String password = new String(txtPass.getPassword());

        List<Infermier> infermiers = Infermier.loadFromFile("C:\\Users\\Admin\\Desktop\\CabineDentisteJava-main\\CabineDentisteJava-main\\flatlaf-dashboard-main\\data\\acc.txt");

        boolean isValid = infermiers.stream()
                .anyMatch(infermier -> infermier.getNomUtilisateur().equals(username) &&
                        infermier.getMotDePasse().equals(password));

        if (isValid) {
            JOptionPane.showMessageDialog(this, "Connexion réussie!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            Application.login();
        } else {
            JOptionPane.showMessageDialog(this, "Nom d'utilisateur ou mot de passe incorrect!", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cmdCreateAccountActionPerformed(java.awt.event.ActionEvent evt) {
        Application.showForm(new CreateAccountForm());
    }

    // Variables declaration
    private javax.swing.JButton cmdLogin;
    private javax.swing.JButton cmdCreateAccount;
    private javax.swing.JLabel lbPass;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbUser;
    private raven.application.form.PanelLogin panelLogin1;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUser;
}
