import java.awt.*;
import java.util.List;
import java.util.function.Predicate;

public class AShapeBox extends TetrisBox {

    private int countY;
    private  int tmpXTmp[] = new int[4];

    public AShapeBox(int x, int y, int width, int height, int[][][] point, int quantity) {

        super(Color.CYAN, x, y, width, height, point, quantity);
        for(int i =0; i<4; i++) {
            tmpX[i] = x  + i * (width /number) + ((number-4)/2)* width /number;
            tmpY[i] = y;
            countY = 0;
        }
    }




    @Override
    public void drawShape(Graphics g, List<TetrisBox> tetrisBoxes) {
        for (int i = 0; i < 4; i++) {
            g.setColor(Color.black);
            g.fillRect(tmpX[i], tmpY[i], (int) width /number, (int) height /number);

            g.setColor(colour);
            g.fillRect(tmpX[i] + 2, tmpY[i] + 2, (int) width /number - 4, (int) height /number - 4);


        }

        //SwingUtilities.computeIntersection();  ?? moze to uzyc

        point();
        canDown();
    }


    public void rotate()
    {
        if (height /number != 0 && width /number != 0) {
            countXP = 0;

            if (countY == 0) {

                for (int e = 0; e < 4; e++)
                    if (point[pointH[e] - e][pointW[e]][0] == 1 ||  pointH[3] <= 4)
                        ++countXP;


                if (countXP == 0) {

                    for (int i = 0; i < 4; i++) {

                        tmpX[i] = tmpX[3];
                        tmpY[i] = tmpY[i] - i * (height /number);
                    }

                }
                else
                    countY = -1;

            }

            if (countY == 1) {

                for (int e = 0; e < 4; e++)
                    if (point[pointH[e]][pointW[e] + e][0] == 1 || pointW[e] >= number - 3)
                        ++countXP;

                if (countXP == 0) {

                    for (int i = 0; i < 4; i++) {

                        tmpY[i] = tmpY[0];
                        tmpX[i] = tmpX[i] + i * (width /number);
                    }
                } else
                    countY = 0;
            }
            if (countY == 2) {


                for (int e = 0; e < 4; e++)
                    if (point[pointH[e] + e][pointW[e]][0] == 1 || pointH[3] >= number - 3)
                        ++countXP;


                if (countXP == 0) {

                    for (int i = 0; i < 4; i++) {

                        tmpX[i] = tmpX[0];
                        tmpY[i] = tmpY[i] + i * (height /number);
                    }
                } else
                    countY = 1;
            }

            if (countY == 3) {

                for (int e = 0; e < 4; e++)
                    if (point[pointH[e]][pointW[e] - e][0] == 1 ||tmpX[3] <= 3 )
                        ++countXP;


                if (countXP == 0) {

                    if (tmpX[0] > 7 + 3 * width /number) {
                        for (int i = 0; i < 4; i++) {

                            tmpY[i] = tmpY[0];
                            tmpX[i] = tmpX[i] - i * (width /number);

                        }
                        for (int i = 0; i < 4; i++)
                            tmpXTmp[i] = tmpX[i];

                        int h = 3;
                        for (int i = 0; i < 4; i++) {
                            tmpX[i] = tmpXTmp[h];
                            h--;
                        }
                        countY = -1;
                    }
                } else
                    countY = 2;
            }
                ++countY;


        }
    }
    private Predicate<TetrisBox> hasCollisionWithCurrent() {
        return box -> (y + 30) >= box.getY(); //condition of collision for a single box
    }
}

