package raven.application.form;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.application.Application;

import java.awt.*;

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

        panelLogin1.setBackground(new Color(77, 161, 169)); // Remplacez par la couleur désiré
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$h1.font");

        txtPass.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0");
        txtUser.setBackground(java.awt.Color.white);
        txtUser.setForeground(new java.awt.Color(46, 80, 119));
        txtPass.setBackground(java.awt.Color.white);
        txtPass.setForeground(new java.awt.Color(46, 80, 119));
        txtUser.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nom d'utilisateur");
        txtPass.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mot de passe");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelLogin1 = new raven.application.form.PanelLogin();
        lbTitle = new javax.swing.JLabel();
        lbUser = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        lbPass = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        cmdLogin = new javax.swing.JButton();
        java.awt.Color textColor = new java.awt.Color(46, 80, 119);
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Connexion à Sanati");
        lbTitle.setForeground(textColor);
        panelLogin1.add(lbTitle);

        lbUser.setText("Nom d'utilisateur");
        lbUser.setForeground(textColor);
        lbUser.setFont(lbUser.getFont().deriveFont(java.awt.Font.BOLD));
        panelLogin1.add(lbUser);
        panelLogin1.add(txtUser);

        lbPass.setText("Mot de passe");
        lbPass.setForeground(textColor);
        lbPass.setFont(lbPass.getFont().deriveFont(java.awt.Font.BOLD));
        panelLogin1.add(lbPass);
        panelLogin1.add(txtPass);

        cmdLogin.setText("S'Authentifier");
        cmdLogin.setForeground(java.awt.Color.WHITE);
        cmdLogin.setBackground(new java.awt.Color(46, 80, 119));
        cmdLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLoginActionPerformed(evt);
            }
        });
        panelLogin1.add(cmdLogin);

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
    }// </editor-fold>//GEN-END:initComponents

    private void cmdLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLoginActionPerformed
        Application.login();
    }//GEN-LAST:event_cmdLoginActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdLogin;
    private javax.swing.JLabel lbPass;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbUser;
    private raven.application.form.PanelLogin panelLogin1;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
