package gui;

import com.sun.java.swing.plaf.motif.MotifBorders;
import entities.Product;
import exceptions.DatabaseException;
import exceptions.InvalidInputException;
import repositories.CategoryRep;
import repositories.GoodsRep;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mikhail on 17.11.2015.
 */
public class GoodsGui {

    private JFrame mainFrame;
    private JComboBox comboCategory;
    private JPanel productFormPanel;

    public static void main(String[] args) {
        GoodsGui gui = new GoodsGui();
        gui.crGui();
    }

    protected void crGui() {
        mainFrame = new JFrame("CREATE PRODUCT");
        JPanel mainPanel = new JPanel(null);
        productFormPanel = new JPanel(new GridBagLayout());

        JLabel nameLabel = new JLabel("Name", JLabel.LEFT);
        productFormPanel.add(nameLabel, this.newLabelConstraints());
        JTextField nameField = new JTextField();
        nameLabel.setLabelFor(nameField);
        productFormPanel.add(nameField, this.newTextFieldConstraints());

        JLabel priceLabel = new JLabel("Price", JLabel.LEFT);
        productFormPanel.add(priceLabel, this.newLabelConstraints());
        JTextField priceField = new JTextField();
        priceLabel.setLabelFor(priceField);
        productFormPanel.add(priceField, this.newTextFieldConstraints());

        JLabel weightLabel = new JLabel("Weight", JLabel.LEFT);
        productFormPanel.add(weightLabel, this.newLabelConstraints());
        JTextField weightField = new JTextField();
        weightLabel.setLabelFor(weightField);
        productFormPanel.add(weightField, this.newTextFieldConstraints());

        JLabel info = new JLabel("", JLabel.LEFT);

        JLabel categoryLabel = new JLabel("Category", JLabel.LEFT);
        productFormPanel.add(categoryLabel, this.newLabelConstraints());
        try {
            comboCategory = new JComboBox(CategoryRep.getAllCategoriesNames());
            comboCategory.setSelectedIndex(0);
            productFormPanel.add(comboCategory, this.newTextFieldConstraints());
        } catch (DatabaseException ex) {
            info.setText("There are problems with DB!");
            ex.printStackTrace();
        }

        JLabel countryLabel = new JLabel("Made in", JLabel.LEFT);
        productFormPanel.add(countryLabel, this.newLabelConstraints());
        String[] elements = new String[]{"China", "Russia",
                "USA", "Germany"};
        JComboBox comboCountry = new JComboBox(elements);
        comboCountry.setSelectedIndex(0);
        productFormPanel.add(comboCountry, this.newTextFieldConstraints());

        productFormPanel.add(info, this.newDangerLabelConstraints());


        productFormPanel.add(new JPanel(), this.generalConstraints());

        JButton saveButton = new JButton("Create product");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String price = priceField.getText();
                String weight = weightField.getText();
                String country = (String) comboCountry.getSelectedItem();
                String category = (String) comboCategory.getSelectedItem();

                try {
                    if (Double.parseDouble(price) < 0 ||
                            Double.parseDouble(weight) < 0) {
                        throw new NumberFormatException();
                    }
                    GoodsRep.add(new Product(name,
                            Double.parseDouble(price),
                            Double.parseDouble(weight),
                            country,
                            CategoryRep.getIdByName(category)));
                    showSuccessWindow("New product has been created!");
                    nameField.setText("");
                    priceField.setText("");
                    weightField.setText("");
                    comboCountry.setSelectedIndex(0);
                    comboCategory.setSelectedIndex(0);
                } catch (DatabaseException ex) {
                    info.setText("There are problems with DB!");
                } catch (InvalidInputException ex) {
                    info.setText(ex.getMessage());
                } catch (NumberFormatException ex) {
                    info.setText("Incorrect value of price or weight!");
                }
            }
        });
        productFormPanel.add(saveButton, this.newButtonConstraints());

        JButton showButton = new JButton("list of goods");
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] columnNames = {
                        "Name",
                        "Price ($)",
                        "Weight (kg)",
                        "Country",
                        "Category"
                };

                try {
                    String[][] data = GoodsRep.getGoodsTable();

                    JTable table = new JTable(data, columnNames);

                    JDialog dialog = new JDialog(mainFrame, "GOODS");
                    JScrollPane scrollPane = new JScrollPane(table);
                    dialog.getContentPane().add(scrollPane);
                    dialog.setPreferredSize(new Dimension(450, 200));
                    dialog.pack();
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                } catch (DatabaseException ex) {
                    info.setText("There are problems with DB!");
                }
            }
        });

        JButton categoryButton = new JButton("add new category");
        categoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CategoriesGui category = new CategoriesGui();
                category.crCategory();
                mainFrame.setVisible(false);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(categoryButton);
        buttonPanel.add(showButton);
        productFormPanel.add(buttonPanel, this.newButtonConstraints());

        productFormPanel.setBorder(new MotifBorders.BevelBorder(true, Color.blue, Color.orange));
        productFormPanel.setBounds(50, 50, 300, 300);

        mainPanel.add(productFormPanel);
        mainFrame.add(mainPanel);
        mainFrame.setResizable(false);
        mainFrame.setPreferredSize(new Dimension(400, 450));
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    protected void showSuccessWindow(String message) {
        JDialog dialog = new JDialog(mainFrame, "Success!");
        dialog.setLayout(new FlowLayout());
        JLabel dialogLabel = new JLabel(message, JLabel.CENTER);
        dialog.add(dialogLabel);
        JButton dialogButton = new JButton("OK");
        dialogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
        dialog.add(dialogButton);
        dialog.setPreferredSize(new Dimension(200, 90));
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    protected GridBagConstraints generalConstraints() {
        GridBagConstraints spacer = new GridBagConstraints();
        spacer.fill = GridBagConstraints.BOTH; // заполн€ет всЄ выделенное пространство
        spacer.gridwidth = GridBagConstraints.REMAINDER; // заполн€ет все столбцы и €чейки.
        spacer.weighty = 1.0; //extra space vertically
        return spacer;
    }

    protected GridBagConstraints newConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        // a little breathing room
        c.insets = new Insets(5, 5, 5, 5);
        return c;
    }

    protected GridBagConstraints newLabelConstraints() {
        GridBagConstraints c = this.newConstraints();
        // right-align labels
        c.anchor = GridBagConstraints.BASELINE_TRAILING;
        c.weightx = 0.0;
        return c;
    }

    protected GridBagConstraints newTextFieldConstraints() {
        GridBagConstraints c = this.newConstraints();
        c.anchor = GridBagConstraints.BASELINE;
        // grow text fields horizontally
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        // text fields end a row
        c.gridwidth = GridBagConstraints.REMAINDER;
        return c;
    }

    protected GridBagConstraints newDangerLabelConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.ipady = 15;
        c.weightx = 0.0;
        c.insets = new Insets(10, 40, 5, 40);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = GridBagConstraints.REMAINDER;
        return c;
    }

    protected GridBagConstraints newButtonConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(1, 5, 1, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1.0;
        return c;
    }

}