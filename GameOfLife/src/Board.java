import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

import static java.lang.System.out;

/**
 * Board with Points that may be expanded (with automatic change of cell
 * number) with mouse event listener
 */

public class Board extends JComponent implements MouseInputListener, ComponentListener {
    private static final long serialVersionUID = 1L;
    private Point[][] points;
    private int size = 14;

    public Board(int length, int height) {
        addMouseListener(this);
        addComponentListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
        setOpaque(true);
    }

    // single iteration
    public void iteration() {
        if (Point.getIsRain()) {
            for (Point[] point : points) {
                point[0].drop();
            }
        }
        for (int x = 0; x < points.length; ++x)
            for (int y = 0; y < points[x].length; ++y)
                points[x][y].calculateNewState();

        for (int x = 0; x < points.length; ++x)
            for (int y = 0; y < points[x].length; ++y)
                points[x][y].changeState();
        this.repaint();
    }

    // clearing board
    public void clear() {
        for (int x = 0; x < points.length; ++x)
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].setState(0);
            }
        this.repaint();
    }

    private void initialize(int length, int height) {
        points = new Point[length][height];

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y] = new Point();
            }
        }

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y].clearNeighbours();
                if (!Point.getIsRain()) {
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            Point neighbour = points[(x + i + points.length) % points.length][(y + j + points[x].length) % points[x].length];
                            if ((i != 0 || j != 0)) {
                                points[x][y].addNeighbour(neighbour);
                            }
                        }
                    }
                }
                if (Point.getIsRain()) {
                    if (y - 1 >= 0) {
                        Point neighbour = points[x][y - 1];
                        points[x][y].addNeighbour(neighbour);
                    }
                }
            }
        }
    }

    //paint background and separators between cells
    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        g.setColor(Color.GRAY);
        drawNetting(g, size);
    }

    // draws the background netting
    private void drawNetting(Graphics g, int gridSpace) {
        Insets insets = getInsets();
        int firstX = insets.left;
        int firstY = insets.top;
        int lastX = this.getWidth() - insets.right;
        int lastY = this.getHeight() - insets.bottom;

        int x = firstX;
        while (x < lastX) {
            g.drawLine(x, firstY, x, lastY);
            x += gridSpace;
        }

        int y = firstY;
        while (y < lastY) {
            g.drawLine(firstX, y, lastX, y);
            y += gridSpace;
        }

        for (x = 0; x < points.length; ++x) {
            for (y = 0; y < points[x].length; ++y) {
                if (points[x][y].getState() != 0) {
                    switch (points[x][y].getState()) {
                        case 1:
                            g.setColor(new Color(0.0f, 0.0f, 1.0f, 0.16f));
                            if (!Point.getIsRain()) {
                                g.setColor(new Color(0.0f, 0.0f, 1.0f, 1.0f));
                            }
                            break;
                        case 2:
                            g.setColor(new Color(0.0f, 0.0f, 1.0f, 0.32f));
                            break;
                        case 3:
                            g.setColor(new Color(0.0f, 0.0f, 1.0f, 0.5f));
                            break;
                        case 4:
                            g.setColor(new Color(0.0f, 0.0f, 1.0f, 0.66f));
                            break;
                        case 5:
                            g.setColor(new Color(0.0f, 0.0f, 1.0f, 0.82f));
                            break;
                        case 6:
                            g.setColor(new Color(0.0f, 0.0f, 1.0f, 1.0f));
                            break;
                    }
                    g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));
                }
            }
        }

    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            points[x][y].clicked();
            this.repaint();
        }
    }

    public void isRainChanged() {
        initialize(points.length, points[0].length);
    }

    public void componentResized(ComponentEvent e) {
        int dlugosc = (this.getWidth() / size) + 1;
        int wysokosc = (this.getHeight() / size) + 1;
        initialize(dlugosc, wysokosc);
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            points[x][y].setState(1);
            this.repaint();
        }
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

}
