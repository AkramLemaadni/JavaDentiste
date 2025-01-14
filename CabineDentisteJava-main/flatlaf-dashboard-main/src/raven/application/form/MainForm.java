package raven.application.form;

import Doctor.DoctorForm;
import Facture.FactureForm;
import Infermier.InfermierForm;
import RendezVous.RendezVousForm;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

import raven.application.Application;
import raven.application.form.other.FormDashboard;
import raven.application.form.other.FormInbox;
import raven.application.form.other.FormRead;
import raven.menu.Menu;
import raven.menu.MenuAction;


public class MainForm extends JLayeredPane {

    // Fields
    private Menu menu;
    private JPanel panelBody;
    private JButton menuButton;

    public MainForm() {
        init();
    }

    private void init() {
        // Initialize the MainForm layout and components
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new MainFormLayout());

        menu = new Menu();
        panelBody = new JPanel(new BorderLayout());

        // Change: Initialize menuButton BEFORE calling initMenuArrowIcon
        menuButton = new JButton();
        initMenuArrowIcon();

        menuButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.button.background;"
                + "arc:999;"
                + "focusWidth:0;"
                + "borderWidth:0");
        menuButton.addActionListener((ActionEvent e) -> {
            setMenuFull(!menu.isMenuFull());
        });

        // Initialize menu events
        initMenuEvent();

        // Add components to the layered pane
        setLayer(menuButton, JLayeredPane.POPUP_LAYER);
        add(menuButton);
        add(menu);
        add(panelBody);
    }

    @Override
    public void applyComponentOrientation(ComponentOrientation o) {
        super.applyComponentOrientation(o);
        initMenuArrowIcon();
    }

    private void initMenuArrowIcon() {
        // Change: Ensure menuButton is initialized before setting the icon
        if (menuButton == null) {
            menuButton = new JButton();
        }
        String icon = (getComponentOrientation().isLeftToRight()) ? "menu_left.svg" : "menu_right.svg";
        menuButton.setIcon(new FlatSVGIcon("raven/icon/svg/" + icon, 0.8f));
    }

    private void initMenuEvent() {
        menu.addMenuEvent((int index, int subIndex, MenuAction action) -> {
            switch (index) {
                case 0: // Tableau de bord
                    Application.showForm(new FormDashboard());
                    break;

                case 1: // Patient
                    if (subIndex == 0) { // Assuming "Patient" has no sub-menu
                        Application.showForm(new Patient.PatientForm());
                    } else {
                        action.cancel();
                    }
                    break;

                case 2: // Docteur
                    Application.showForm(new DoctorForm()); // Replace with your Doctor form
                    break;

                case 3: //Infermier
                    Application.showForm(new InfermierForm());
                    break;

                case 4: // Rendez-vous
                    if (subIndex == 0) { // Assuming "Rendez-vous" has no sub-menu
                        Application.showForm(new RendezVousForm()); // Replace with your Rendez-vous form
                    } else {
                        action.cancel();
                    }
                    break;

                case 5: // Facture
                    Application.showForm(new FactureForm()); // Replace with your Facture form
                    break;

                case 6: // Logout
                    Application.logout();
                    break;

                default:
                    action.cancel();
            }
        });
    }


    private void setMenuFull(boolean full) {
        String icon = (getComponentOrientation().isLeftToRight())
                ? (full ? "menu_left.svg" : "menu_right.svg")
                : (full ? "menu_right.svg" : "menu_left.svg");

        menuButton.setIcon(new FlatSVGIcon("raven/icon/svg/" + icon, 0.8f));
        menu.setMenuFull(full);
        revalidate();
    }

    public void showForm(Component component) {
        // Update the content area with the specified component
        panelBody.removeAll();
        panelBody.add(component, BorderLayout.CENTER);
        panelBody.repaint();
        panelBody.revalidate();
    }

    public void setSelectedMenu(int index, int subIndex) {
        menu.setSelectedMenu(index, subIndex);
    }

    public void hideMenu() {
        menu.hideMenuItem();
    }


    private class MainFormLayout implements LayoutManager {
        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return new Dimension(5, 5);
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return new Dimension(0, 0);
        }

        @Override
        public void layoutContainer(Container parent) {
            Insets insets = UIScale.scale(parent.getInsets());
            int x = insets.left;
            int y = insets.top;
            int width = parent.getWidth() - (insets.left + insets.right);
            int height = parent.getHeight() - (insets.top + insets.bottom);

            int menuWidth = UIScale.scale(menu.isMenuFull() ? menu.getMenuMaxWidth() : menu.getMenuMinWidth());
            int menuX = parent.getComponentOrientation().isLeftToRight() ? x : x + width - menuWidth;
            menu.setBounds(menuX, y, menuWidth, height);

            int buttonWidth = menuButton.getPreferredSize().width;
            int buttonHeight = menuButton.getPreferredSize().height;
            int buttonX = parent.getComponentOrientation().isLeftToRight()
                    ? menuX + menuWidth - buttonWidth
                    : menuX - buttonWidth;

            menuButton.setBounds(buttonX, UIScale.scale(30), buttonWidth, buttonHeight);

            int bodyX = parent.getComponentOrientation().isLeftToRight() ? x + menuWidth : x;
            panelBody.setBounds(bodyX, y, width - menuWidth, height);
        }
    }
}
