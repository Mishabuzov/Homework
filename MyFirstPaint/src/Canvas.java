import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Created by Mikhail on 09.12.2015.
 */
public class Canvas extends JPanel {

    private Graphics2D g2;
    private int currentX;
    private int currentY;
    private int oldX;
    private int oldY;
    private BufferedImage image;

    public Canvas() {
        // setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // save coord x,y when mouse is pressed
                oldX = e.getX();
                oldY = e.getY();
            }
        });

        AffineTransform at = new AffineTransform();

        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                // coord x,y when drag mouse
                currentX = e.getX();
                currentY = e.getY();

                if (g2 != null) {
                    // draw line if g2 context is not null
                    g2.drawLine(oldX, oldY, currentX, currentY);
                    // g2.fillOval(oldX, oldY, 5, 5);
                    // refresh draw area to repaint
                    repaint();
                    // store current coords x,y as olds x,y
                    oldX = currentX;
                    oldY = currentY;
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        if (image == null) {
            // image to draw null ==> we create
            //  image = (BufferedImage) createImage(getSize().width, getSize().height);
            image = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_RGB);
            g2 = image.createGraphics();
            // enable antialiasing
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // clear draw area
            backWhite();
        }
        g.drawImage(image, 0, 0, this);

    }

    public void red() {
        // apply red color on g2 context
        g2.setPaint(Color.red);
    }

    public void black() {
        g2.setPaint(Color.black);
    }

    public void green() {
        g2.setPaint(Color.green);
    }

    public void blue() {
        g2.setPaint(Color.blue);
    }

    public void white() {
        g2.setPaint(Color.WHITE);
    }

    public void mirroring() {
        AffineTransform at = new AffineTransform();
        at.scale(-1, 1);
        at.translate(-image.getWidth(), 0);
        g2.setTransform(at);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(image, at, null);
        repaint();
        if (Gui.comboBackground.getSelectedItem().equals("white")) {
            black();
        } else if (Gui.comboBackground.getSelectedItem().equals("yellow")) {
            black();
        } else if (Gui.comboBackground.getSelectedItem().equals("blue")) {
            white();
        } else if (Gui.comboBackground.getSelectedItem().equals("dark")) {
            white();
        } else if (Gui.comboBackground.getSelectedItem().equals("red")) {
            black();
        }
    }

    public void backWhite() {
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.BLACK);
        repaint();
    }

    public void backBlue() {
        g2.setPaint(Color.BLUE);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.WHITE);
        repaint();
    }

    public void backRed() {
        g2.setPaint(Color.RED);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.BLACK);
        repaint();
    }

    public void backYellow() {
        g2.setPaint(Color.YELLOW);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.black);
        repaint();
    }

    public void backDark() {
        g2.setPaint(Color.black);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.WHITE);
        repaint();
    }

}
