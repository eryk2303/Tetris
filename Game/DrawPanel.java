import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * class for drawing coats
 */
class DrawPanel extends JPanel {

    LinkedList<TetrisBox> tetrisBoxes = new LinkedList<>(); //newConcurrentQueue //stackoverflow.com/questions/6916385/is-there-a-concurrent-list-in-javas-jdk

    private int width;
    private int height;

    private int quantity;
    public DrawPanel(int quantity)
    {
        this.quantity = quantity;

    }
    /**
     * method for adding new box
     *
     * @param shape - box shape
     * @param x     - x position
     * @param y     - y position
     * @param speed - box speed
     * @param point - array to write whether the field on the board is occupied
     */
    public void addBox(char shape, int x, int y, int speed, int[][][] point) {
        if ('A' == shape) {
            AShapeBox newBox = new AShapeBox(x, y, width, height, point, quantity);
            newBox.setSpeed(speed);
            tetrisBoxes.add(newBox);

        } else if ('B' == shape) {
            BShapeBox newBox = new BShapeBox(x, y, width, height, point, quantity);
            newBox.setSpeed(speed);
            tetrisBoxes.add(newBox);
        } else if ('C' == shape) {
            CShapeBox newBox = new CShapeBox(x, y, width, height, point, quantity);
            newBox.setSpeed(speed);
            tetrisBoxes.add(newBox);
        } else if ('D' == shape) {
            DShapeBox newBox = new DShapeBox(x, y, width, height, point, quantity);
            newBox.setSpeed(speed);
            tetrisBoxes.add(newBox);
        } else if ('E' == shape) {
            EShapeBox newBox = new EShapeBox(x, y, width, height, point, quantity);
            newBox.setSpeed(speed);
            tetrisBoxes.add(newBox);
        } else if ('F' == shape) {
            BlockBox newBox = new BlockBox(x, y, width, height, point, quantity);
            newBox.setSpeed(speed);
            tetrisBoxes.add(newBox);
        } else if ('G' == shape) {
            TreatBox newBox = new TreatBox(x, y, width, height, point, quantity);
            newBox.setSpeed(speed);
            tetrisBoxes.add(newBox);
        }

    }

    /**
     * method to get current box in the list
     *
     * @return - current box in the list
     */
    public TetrisBox getCurrentBox() {
        return tetrisBoxes.getLast();
    }

    /**
     * method to get list which boxes
     *
     * @return
     */
    public LinkedList<TetrisBox> getBox() {
        return tetrisBoxes;
    }

    /**
     * method for drawing layout
     *
     * @param g
     */

    public void drawLayout(Graphics g) {
        super.paintComponent(g);

            width = (quantity - 3) * (this.getWidth() / quantity);
        height = this.getHeight();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
    }

    /**
     * method for painting component
     *
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawLayout(g);
        tetrisBoxes.forEach(box -> box.drawShape(g, tetrisBoxes));
    }

    /**
     * method to get current width
     *
     * @return - current width
     */
    public int getWidthG() {
        return width;
    }

    /**
     * method to get current height
     *
     * @return - current heightn
     */
    public int getHeightG() {
        return height;
    }


}
