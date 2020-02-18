import java.awt.*;
import java.util.List;
/**
 * class representing a box
 */
public abstract class TetrisBox {
    protected int countH = 0;
    protected  int coutD = 1;
    protected int hFree = 29;
    protected int wFree = 29;
    protected int number = 15;
    protected int  step = 1;
    /**
     * table to write whether the field on the board is occupied
     */
    protected int[][][] point;
    /**
     * variable need to set colour
     */
    protected Color colour ;
    protected int speed;

    public void setX(int x) {
        this.x = x;
    }

    protected int x;

    public void setY(int y) {
        this.y = y;
    }

    protected int y;
    protected int width;
    protected int height;
    //protected int widthTmp;
    //protected int heightTmp;
    /**
     * count to set proper direction
     */
    protected int countXP;

    /**
     * table to return occupied places by box
     */
    protected int pointH[] = new int[4];
    protected int h[] = new int[4];
    protected int pointW[] = new int[4];
    protected int w[] = new int[4];
    /**
     * table with actual position
     */
    protected  int tmpX[] = new int[4];
    protected int tmpY[] = new int[4];
    /**
     * count to help good change x position
     */
    protected int count;
    protected int quabtity;
    /**
     *
     * class constructor setting values
     * @param colour - colour of boxes
     * @param x - x position of box
     * @param y - y position of box
     * @param width - actual frame width
     * @param height - actual frame height
     * @param point - table to write whether the field on the board is occupied
     */
    public TetrisBox(Color colour, int x, int y, int width, int height, int[][][] point, int quabtity){
        this.colour=colour;
        this.x=x;
        this.y=y;
        this.width = width;
        this.height = height;
        this.point = point;
        this.number = quabtity;
    }

    public void setStep(int step)
    {
        this.step = step;
    }

    /**
     * method to set speed
     * @param speed - box speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * method to set occupied places by box
     * @param h - table occupied places by box
     */
    public void setPointH(int[] h) {
        this.h = h;
    }

    /**
     * method to set accupied places by box
     * @param w- table occupied places by box
     */
    public void setPointW(int[] w) {
        this.w = w;
    }

    /**
     * method to calibration and set actual width
     * @param width - actual widh
     */
    public void setWidth(int width) {

        if(this.width != width)
        {


            for(int i =0; i<4; i++)
                tmpX[i] = width/number * pointW[i];


            this.width = width;

        }
    }

    /**
     * method to calibration and set actual height
     * @param height - actual height
     */
    public void setHeight(int height) {

        if(this.height != height) {
            step = height/400;
            this.height = height;
            for(int i =0; i<4; i++)
                tmpY[i] = height/number* pointH[i];

        }
    }
    /**
     * method responsible for drawing a box and stopping it at the bottom of the tunnel
     * @param g
     * @param tetrisBoxes - list with tetris box
     */
    public abstract void drawShape(Graphics g, List<TetrisBox> tetrisBoxes);


    /**
     * method to get y position
     * @return - y position
     */
    public int getY() {
        return y;
    }

    /**
     * method to get actual speed
     * @return - actual speed
     */

    public int getSpeed() {
        return speed;
    }

    /**
     * method rotation box
     */
    public abstract  void rotate();

    /**
     * method to get to occupied places by box
     * @return - occupied places by box
     */
    public int[] getPointH() {
        return h;
    }

    /**
     * method to get to occupied places by box
     * @return - occupied places by box
     */
    public int[] getPointW() {
        return w;
    }


    /**
     * method to disappearance boxes and do "tetris" action
     * @param numberDelete - block row to be removed
     */
    public void disappear(int numberDelete) {
        int tmp = 0;
        for (int i = 0; i < 4; i++) {
            if (pointH[i] == numberDelete)
                tmpY[i] = height + 2 * height / number;


            if (pointH[i] < numberDelete && pointH[i] > 1) {
                tmpY[i] = tmpY[i] + height / number;
            }

        }

    }

    /**
     * method to change x position
     * @param direction - x direction
     */
    public void moveInDirection(String direction)
    {
        if(direction == "right" ) {


            if (tmpX[3] < width -2* width/number && tmpX[0] < width-2* width/number  && tmpX[1] < width  - 2*width/number && tmpX[2] < width - 2*width/number)
                if (height / number != 0 && width / number != 0) {
                    countXP = 0;

                    for (int e = 0; e < 4; e++)
                        if (point[pointH[e] + 1][pointW[e] + 1][0] == 1 || point[pointH[e]][pointW[e] + 1][0] == 1)
                            ++countXP;

                    if (countXP == 0)

                        for (int i = 0; i < 4; i++)
                            tmpX[i] = tmpX[i] + width / number;


                }


        }
        if(direction == "left")
        {
            if (height / number != 0 && width / number != 0) {
                countXP = 0;
                if(width/number != 0) {
                    for (int e = 0; e < 4; e++)
                        if (point[pointH[e] + 1][pointW[e] - 1][0] == 1 || point[pointH[e]][pointW[e] - 1][0] == 1)
                            ++countXP;

                    if (countXP == 0)
                        if (tmpX[0] >= width / number && tmpX[3] >= width / number && tmpX[1] >= width / number && tmpX[2] >= width / number)
                            for (int i = 0; i < 4; i++)
                                tmpX[i] = tmpX[i] - width / number;

                }
            }
           
        }

    }

    /**
     * method to find occupied places by box
     */
    public void point()
    {
        if (height / number != 0 && width / number != 0)
        {
            for (int i = 0; i < 4; i++) {
                pointH[i] = tmpY[i] / (height / number);
                pointW[i] = tmpX[i] / (width / number);

            }
            setPointH(pointH);
            setPointW(pointW);
        }



    }
int countPosition = 0;
    /**
     * method to control y movement
     */
    public void canDown() {

        if (tmpY[0] >= 24 * height / number || tmpY[3] >= 24 * height / number) {  //at the bottom of the tunnel the box is stopped

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
            for (int i = 0; i < 4; i++) {
                if (point[pointH[i] + 1][pointW[i]][0] == 1) {
                    ++count;

                }
            }
            if (count != 0) {
                setSpeed(0);


            } else {
                for (int e = 0; e < 4; e++)
                    tmpY[e] = tmpY[e] + step; //move down
            }


        }

    }

    /**
     * method to set position when tetris goes to a new level
     */
    public void end()
    {
        for (int i = 0; i < 4; i++) {
            tmpY[i] = height + 2 * height / number;
        }
    }
    public void pointfree()
    {
        if (height / 25 != 0)
        {
            for (int i = 0; i < 4; i++)
                if(pointH[i] != tmpY[i] / (height / number)) {
                    setPointHFree(pointH[i]);
                    setPointWFree(pointW[i]);
                }

        }

    }

    public void setPointHFree(int hFree) {
        this.hFree = hFree;
    }


    public void setPointWFree(int  wFree) {
        this.wFree = wFree;
    }
    public int getWFree(){
        return this.wFree;
    }
    public int getHFree(){
        return this.hFree;
    }

}
