package Form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import model.TipoVeiculo;

public class AxisPanel extends JPanel {

    private static final int PREF_W = 200;
    private static final int PREF_H = 300;

    private static final int AXIS_W = 49;
    private static final int AXIS_H = 10;

    private static final int TIRE_W = 25;
    private static final int TIRE_H = 50;

    private List<Rectangle> tires = new ArrayList<Rectangle>();
    private List<Rectangle> axis = new ArrayList<Rectangle>();

    public void addTireRect(int x, int y, int width, int height) {
        Rectangle rect = new Rectangle(x, y, width, height);
        tires.add(rect);
    }

    public AxisPanel() {
    }
    
    public void modificarDesenhoEixosPorTipoVeiculo(TipoVeiculo t){
    }

    public void erasePanelForNewSelection() {
        tires = new ArrayList<Rectangle>();
        axis = new ArrayList<Rectangle>();

        repaint();
    }

    public void createNormalCar() {
        erasePanelForNewSelection();

        addTireRect(75, 60, TIRE_W, TIRE_H);
        addTireRect(150, 60, TIRE_W, TIRE_H);
        axis.add(getEixoRectangle(75, 60));

        addTireRect(75, 120, TIRE_W, TIRE_H);
        addTireRect(150, 120, TIRE_W, TIRE_H);
        axis.add(getEixoRectangle(75, 120));
    }

    public void createForCarreta1Axis1Set() {
        erasePanelForNewSelection();
        // left set of tires of axis 1
        addTireRect(75, 60, TIRE_W, TIRE_H);

        // right set of tire of axis 1
        addTireRect(150, 60, TIRE_W, TIRE_H);

        // axis 1
        axis.add(getEixoRectangle(75, 60));
    }

    public void createForCarreta1Axis2Set() {
        erasePanelForNewSelection();
        // left set of tires of axis 1
        addTireRect(48, 60, TIRE_W, TIRE_H); // -27 x of main tire
        addTireRect(75, 60, TIRE_W, TIRE_H);

        // right set of tire of axis 1
        addTireRect(150, 60, TIRE_W, TIRE_H);
        addTireRect(177, 60, TIRE_W, TIRE_H);// +27 x of main tire

        // axis 1
        axis.add(getEixoRectangle(75, 60));
    }

    public void createForCarreta2Axis1Set() {
        erasePanelForNewSelection();

        // first axis
        addTireRect(75, 60, TIRE_W, TIRE_H);
        addTireRect(150, 60, TIRE_W, TIRE_H);
        axis.add(getEixoRectangle(75, 60));

        // second axis
        addTireRect(75, 120, TIRE_W, TIRE_H);
        addTireRect(150, 120, TIRE_W, TIRE_H);
        axis.add(getEixoRectangle(75, 120));
    }

    public void createForCarreta2Axis2Set() {
        erasePanelForNewSelection();

        // first axis
        addTireRect(48, 60, TIRE_W, TIRE_H); // -27 x of main tire
        addTireRect(75, 60, TIRE_W, TIRE_H);

        addTireRect(150, 60, TIRE_W, TIRE_H);
        addTireRect(177, 60, TIRE_W, TIRE_H);// +27 x of main tire
        axis.add(getEixoRectangle(75, 60));

        // second axis
        addTireRect(48, 120, TIRE_W, TIRE_H);
        addTireRect(75, 120, TIRE_W, TIRE_H);

        addTireRect(150, 120, TIRE_W, TIRE_H);
        addTireRect(177, 120, TIRE_W, TIRE_H);// +27 x of main tire
        axis.add(getEixoRectangle(75, 120));
    }

    private Rectangle getEixoRectangle(int x, int y) {
        // x = xRectangle + xRectangle largura || y = yRectangle + 20
        return new Rectangle(x + TIRE_W + 1, y + 20, AXIS_W, AXIS_H);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};

        for (int i = 0; i < tires.size(); i++) {
            Rectangle rect = tires.get(i);

            // draws tire border
            g2.setColor(Color.BLACK);
            g2.draw(rect);

            // fills tire with inner color
            g2.setColor(Color.GRAY);
            g2.fillRect((int) rect.getX() + 1, (int) rect.getY() + 1,
                    TIRE_W - 1, TIRE_H - 1);

            // draws letter inside tire
            g2.setColor(Color.BLACK);
            g2.drawString(letters[i], (int) rect.getX() + 10,
                    (int) rect.getY() + 30);
        }

        for (Rectangle rect : axis) {

            // draws axis border
            g2.setColor(Color.DARK_GRAY);
            g2.draw(rect);

            // fills axis with inner color
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect((int) rect.getX(), (int) rect.getY() + 1, AXIS_W,
                    AXIS_H - 1);
        }
    }

    public List<Rectangle> getTires() {
        return tires;
    }

    public void setTires(List<Rectangle> tires) {
        this.tires = tires;
    }
}

// private void createTiresFor1Axis() {
//
// addRectangle(75, 60, TIRE_W, TIRE_H);
// addRectangle(150, 60, TIRE_W, TIRE_H);
// squares.add(getEixoRectangle(75, 60));
//
// addRectangle(75, 120, TIRE_W, TIRE_H);
// addRectangle(150, 120, TIRE_W, TIRE_H);
// squares.add(getEixoRectangle(75, 120));
//
// }
//
// private void create4AxisSetWithOneTires() {
//
// // first axis
// addRectangle(75, 60, TIRE_W, TIRE_H);
// addRectangle(150, 60, TIRE_W, TIRE_H);
// axis.add(getEixoRectangle(75, 60));
//
// // second axis
// addRectangle(75, 120, TIRE_W, TIRE_H);
// addRectangle(150, 120, TIRE_W, TIRE_H);
// axis.add(getEixoRectangle(75, 120));
//
// // third axis
// addRectangle(75, 180, TIRE_W, TIRE_H);
// addRectangle(150, 180, TIRE_W, TIRE_H);
// axis.add(getEixoRectangle(75, 180));
//
// // fourth axis
// addRectangle(75, 240, TIRE_W, TIRE_H);
// addRectangle(150, 240, TIRE_W, TIRE_H);
// axis.add(getEixoRectangle(75, 240));
// }
