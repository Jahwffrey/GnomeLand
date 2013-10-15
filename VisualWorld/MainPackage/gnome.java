package MainPackage;

/**
 * Created with IntelliJ IDEA.
 * User: Jack
 * Date: 10/14/13
 * Time: 6:15 PM
 */
public class gnome {
    int blockX;
    int blockY;
    int footX;
    int footY;
    int headX;
    int headY;

    int height;
    int width;

    int goingX;
    int goingY;

    int facingRight = 0;

    int tool;
    //0 = no tool

    int vspeed = 0;


    public gnome(){
        height = 10;
        width = 10;
        blockX = 0;
        blockY = 0;
        headX = 10;
        headY = 0;
        footX =headX;
        footY = headY+10;
        tool = 0;
    }

    public gnome(int heit,int widt,int XX, int YY,int tol){
        height = heit;
        width = widt;
        footX = XX;
        footY = YY;
        headX = XX;
        headY = YY-height;
        tool = tol;
        blockX = footX/Screen.blockSize;
        blockY = footY/Screen.blockSize;
    }

    public void fall(int grav, int maxSpeed){
        if(Screen.land[(footY+vspeed)/Screen.blockSize][footX/Screen.blockSize]==0||Screen.land[(footY+vspeed)/Screen.blockSize][footX/Screen.blockSize]==5||Screen.land[(footY+vspeed)/Screen.blockSize][footX/Screen.blockSize]==6){
            footY+=vspeed;
            vspeed+=grav;
            if(vspeed>maxSpeed){
                vspeed=maxSpeed;
            }
        }
        else{
            footY=((blockY+1)*Screen.blockSize)-1;
        }
    }

    public void jump(int speed){
        vspeed=-speed;
    }
}
