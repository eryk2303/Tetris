import java.awt.*;
import java.util.List;
import java.util.function.Predicate;

public class BlockBox extends TetrisBox {

    private int countY;
    private  int tmpXTmp[] = new int[4];

    public BlockBox(int x, int y, int width, int height, int[][][] point, int quantity) {

        super(Color.BLACK, x, y, width, height, point,  quantity);
        for(int i =0; i<1; i++) {
            tmpX[i] = x  + i * (width / number) + ((number-4)/2) * (width / number);
            tmpY[i] = y;
            countY = 0;
        }
    }




    @Override
    public void drawShape(Graphics g, List<TetrisBox> tetrisBoxes) {
        for (int i = 0; i < 1; i++) {
            g.setColor(Color.black);
            g.fillRect(tmpX[i], tmpY[i], (int) width / number, (int) height / number);

            g.setColor(colour);
            g.fillRect(tmpX[i] + 2, tmpY[i] + 2, (int) width / number - 4, (int) height / number - 4);


        }

        //SwingUtilities.computeIntersection();  ?? moze to uzyc

        point();
        canDown();
    }

    @Override
    public void rotate() {

    }

    @Override
    public void canDown() {

        if (tmpY[0] >= 24 * height / number ) {  //at the bottom of the tunnel the box is stopped
            setSpeed(0);
            return;

        }
        if(pointH[0] == 0 && countPosition == 0)
        {
            for (int e = 0; e < 4; e++) {
                tmpX[e] = (width / number) * pointW[e];
            }
            countPosition = 1;
        }

        if (height / number != 0 && width / number != 0) {
            count = 0;
            for (int i = 0; i < 1; i++) {
                if (point[pointH[i] + 1][pointW[i]][0] == 1) {
                    ++count;

                }
            }
            if (count != 0) {
                setSpeed(0);


            } else {
                for (int e = 0; e < 1; e++)
                    tmpY[e] = tmpY[e] + height / 400; //move down
            }


        }

    }


    private Predicate<TetrisBox> hasCollisionWithCurrent() {
        return box -> (y + 30) >= box.getY(); //condition of collision for a single box
    }
}

