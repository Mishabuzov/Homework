package gui;

import com.sun.java.swing.plaf.motif.MotifBorders;
import entities.Category;
import exceptions.DatabaseException;
import exceptions.InvalidInputException;
import repositories.CategoryRep;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mikhail on 18.11.2015.
 */
public class CategoriesGui {

    private JComboBox comboParent;

    protected void crCategory() {

        GoodsGui gui = new GoodsGui();

        JFrame mainFrame = new JFrame("CREATE PRODUCT");
        JPanel mainPanel = new JPanel(null);
        JPanel categoryFormPanel = new JPanel(new GridBagLayout());

        JLabel nameLabel = new JLabel("Name", JLabel.LEFT);
        categoryFormPanel.add(nameLabel, gui.newLabelConstraints());
        JTextField nameField = new JTextField();
        nameLabel.setLabelFor(nameField);
        categoryFormPanel.add(nameField, gui.newTextFieldConstraints());

        try {
            JLabel parentLabel = new JLabel("Parent", JLabel.LEFT);
            categoryFormPanel.add(parentLabel, gui.newLabelConstraints());
            comboParent = new JComboBox(CategoryRep.getAllCategoriesNames());
            comboParent.setSelectedIndex(0);
            categoryFormPanel.add(comboParent, gui.newTextFieldConstraints());
        } catch (DatabaseException ex) {

        }

        JLabel info = new JLabel("", JLabel.LEFT);
        categoryFormPanel.add(info, gui.newDangerLabelConstraints());

        JButton saveButton = new JButton("Add category");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    int parent = CategoryRep.getIdByName((String) comboParent.getSelectedItem());
                    CategoryRep.add(new Category(
                            CategoryRep.getCategoriesId(),
                            name,
                            parent));
                    gui.crGui();
                    gui.showSuccessWindow("new category has been added!");
                    mainFrame.setVisible(false);
                } catch (DatabaseException ex) {
                    info.setText("There are problems with DB!");
                } catch (InvalidInputException ex) {
                    info.setText(ex.getMessage());
                }
            }
        });
        categoryFormPanel.add(saveButton, gui.newTextFieldConstraints());

        categoryFormPanel.add(new JPanel(), gui.generalConstraints());

        JButton exitButton = new JButton("Cancel");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                gui.crGui();
            }
        });

        categoryFormPanel.add(exitButton, gui.newButtonConstraints());

        categoryFormPanel.setBorder(new MotifBorders.BevelBorder(true, Color.blue, Color.orange));
        categoryFormPanel.setBounds(50, 50, 300, 300);

        mainPanel.add(categoryFormPanel);
        mainFrame.add(mainPanel);
        mainFrame.setResizable(false);
        mainFrame.setPreferredSize(new Dimension(400, 450));
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}
