import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Mikhail on 09.12.2015.
 */
public class Gui {

    JFrame frame;
    JButton clearBtn;
    JButton blackBtn;
    JButton blueBtn;
    JButton greenBtn;
    JButton redBtn;
    JButton whiteBtn;
    JButton mirror;
    JLabel backgroundLabel;
    JLabel pensilLabel;
    static JComboBox comboBackground;

    Canvas canvas;

    protected static int actionNum = 0;

    public static void main(String[] args) {
        new Gui().onCreateGui();
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clearBtn) {
                comboBackground.setSelectedItem(comboBackground.getSelectedItem());
            } else if (e.getSource() == blackBtn) {
                canvas.black();
            } else if (e.getSource() == blueBtn) {
                canvas.blue();
            } else if (e.getSource() == greenBtn) {
                canvas.green();
            } else if (e.getSource() == redBtn) {
                canvas.red();
            } else if (e.getSource() == whiteBtn) {
                canvas.white();
            } else if (e.getSource() == mirror) {
                canvas.mirroring();
            }
        }
    };

    public void onCreateGui() {
        frame = new JFrame("Paint");
        JPanel mainPanel = new JPanel(new BorderLayout());
        // create draw area
        canvas = new Canvas();
        // add to main pane
        mainPanel.add(canvas, BorderLayout.CENTER);
        // create controls to apply colors and call clear feature
        JPanel backColor = new JPanel();
        JPanel labelColor = new JPanel();
        JPanel controls = new JPanel();

        backgroundLabel = new JLabel("Background", JLabel.LEFT);
        String[] colors = {"white", "red", "dark", "blue", "yellow"};
        comboBackground = new JComboBox(colors);
        comboBackground.setSelectedItem(0);
        comboBackground.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBackground.getSelectedItem().equals("white")) {
                    actionNum = 0;
                    canvas.backWhite();
                } else if (comboBackground.getSelectedItem().equals("yellow")) {
                    actionNum = 4;
                    canvas.backYellow();
                } else if (comboBackground.getSelectedItem().equals("blue")) {
                    actionNum = 3;
                    canvas.backBlue();
                } else if (comboBackground.getSelectedItem().equals("dark")) {
                    actionNum = 2;
                    canvas.backDark();
                } else if (comboBackground.getSelectedItem().equals("red")) {
                    actionNum = 1;
                    canvas.backRed();
                }
            }
        });
        backColor.add(backgroundLabel);
        backgroundLabel.setLabelFor(comboBackground);
        backColor.add(comboBackground);
        mirror = new JButton("Mirror");
        mirror.addActionListener(actionListener);
        backColor.add(mirror);

        pensilLabel = new JLabel("Pensil color", JLabel.LEFT);
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(actionListener);
        blackBtn = new JButton("Black");
        blackBtn.addActionListener(actionListener);
        blueBtn = new JButton("Blue");
        blueBtn.addActionListener(actionListener);
        greenBtn = new JButton("Green");
        greenBtn.addActionListener(actionListener);
        redBtn = new JButton("Red");
        redBtn.addActionListener(actionListener);
        whiteBtn = new JButton("White");
        whiteBtn.addActionListener(actionListener);


        // add to panel
        labelColor.add(pensilLabel);
        pensilLabel.setLabelFor(controls);
        controls.add(greenBtn);
        controls.add(blueBtn);
        controls.add(whiteBtn);
        controls.add(blackBtn);
        controls.add(redBtn);
        controls.add(clearBtn);
        labelColor.add(controls);

        // add to main panel
        mainPanel.add(labelColor, BorderLayout.NORTH);
        mainPanel.add(backColor, BorderLayout.SOUTH);
        frame.add(mainPanel);
        frame.setSize(800, 650);
        frame.setResizable(false);
        // can close frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // show the swing paint result
        frame.setVisible(true);
    }
}
