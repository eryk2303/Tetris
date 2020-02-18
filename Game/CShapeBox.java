import java.awt.*;
import java.util.List;
import java.util.function.Predicate;

public class CShapeBox extends TetrisBox {

    private int countY;

    int length=4;
    public CShapeBox(int x, int y, int width, int height, int[][][] point, int quantity) {

        super(Color.GREEN, x, y, width, height, point,quantity);
        for(int i =0; i<length; i++) {
            if(2>i){
                tmpX[i] = x + i * (width / number
                ) + ((number-4)/2)* width / number
                ;
                tmpY[i] = y;
                countY = 0;
            }
            else{
                tmpX[i] = x + (i-1) * (width / number
                ) +((number-4)/2)* width / number
                ;
                tmpY[i] = y + height / number
                ;
                countY = 0;
            }
        }
    }



    @Override
    public void drawShape(Graphics g, List<TetrisBox> tetrisBoxes) {
        for (int i = 0; i < length; i++) {
            g.setColor(Color.black);
            g.fillRect(tmpX[i], tmpY[i], (int) width / number
                    , (int) height / number
            );
            g.setColor(colour);
            g.fillRect(tmpX[i] + 2, tmpY[i] + 2, (int) width / number
                    - 4, (int) height / number
                    - 4);


        }

        point();
        canDown();
    }


    public void rotate()
    {
        if (height / number
                != 0 && width / number
                != 0) {
            countXP = 0;

            if (countY == 0) {

                if (point[pointH[0] + 1][pointW[0] + 2][0] == 1 || point[pointH[1]][pointW[1] + 1][0] == 1)
                    ++countXP;

                if (countXP == 0) {

                    tmpX[0] = tmpX[0] + (width / number
                    );
                    tmpY[0] = tmpY[0] + 2 * (height / number
                    );
                    tmpX[1] = tmpX[1] + (width / number
                    );
                } else
                    countY = -1;
            }


            if (countY == 1) {
                if (point[pointH[0]][pointW[0] + 1][0] == 1 || point[pointH[1] + 2][pointW[1] + 1][0] == 1 || pointW[1] == number - 1)
                    ++countXP;

                if (countXP == 0) {
                    tmpX[0] = tmpX[0] + (width / number
                    );
                    tmpX[1] = tmpX[1] + (width / number
                    );
                    tmpY[1] = tmpY[1] + 2 * (height / number
                    );

                } else
                    countY = 0;
            }
            if (countY == 2) {

                if (point[pointH[0]][pointW[0] - 1][0] == 1 || point[pointH[1] - 2][pointW[1] - 1][0] == 1)
                    ++countXP;


                if (countXP == 0) {

                    tmpX[0] = tmpX[0] - (width /number
                    );
                    tmpX[1] = tmpX[1] - (width / number
                    );
                    tmpY[1] = tmpY[1] - 2 * (height / number
                    );
                } else
                    countY = 1;
            }
            if (countY == 3) {


                    if (point[pointH[0] - 2][pointW[0] - 1][0] == 1 || point[pointH[1]][pointW[1] - 1][0] == 1 || pointH[0] >= number - 2)
                        ++countXP;


                if (countXP == 0) {
                    tmpX[0] = tmpX[0] - (width / number
                    );
                    tmpY[0] = tmpY[0] - 2 * (height / number
                    );
                    tmpX[1] = tmpX[1] - (width / number
                    );


                countY = -1;

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



