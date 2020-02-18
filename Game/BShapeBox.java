import java.awt.*;
import java.util.List;
import java.util.function.Predicate;

public class BShapeBox extends TetrisBox {

    private int countY;

    int length=4;
    public BShapeBox(int x, int y, int width, int height, int[][][] point, int quantity) {

        super(Color.RED, x, y, width, height, point, quantity);
        for(int i =0; i<4; i++) {
            if(i<2){
            tmpX[i] = x + i * (width /number) + ((number-4)/2)* width /number;
            tmpY[i] = y;

        }
            else{
                tmpX[i] = x + (i-2) * (width /number) + ((number-4)/2)* width /number;
                tmpY[i] = y + height /number;

            }
        }

    }



    @Override
    public void drawShape(Graphics g, List<TetrisBox> tetrisBoxes) {
        for (int i = 0; i < length; i++) {
            g.setColor(Color.black);
            g.fillRect(tmpX[i], tmpY[i], (int) width /number, (int) height /number);
            g.setColor(colour);
            g.fillRect(tmpX[i] + 2, tmpY[i] + 2, (int) width /number - 4, (int) height /number - 4);


        }

        point();
        canDown();
    }


    public void rotate()
    {

    }
    private Predicate<TetrisBox> hasCollisionWithCurrent() {
        return box -> (y + 30) >= box.getY(); //condition of collision for a single box
    }
}


