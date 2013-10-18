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

    int willMove = 0;

    int goingX;
    int goingY;

    int facingRight = 0;

    int tool;
    //0 = no tool

    int vspeed = 0;
    int hspeed = 0;

    boolean onGround = false;


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
            onGround = false;
        }
        else if(Screen.land[(footY+vspeed)/Screen.blockSize][(footX+willMove)/Screen.blockSize]==7){
            footY+=vspeed/2;
            vspeed+=grav/2;
            if(vspeed>(maxSpeed/2)){
                vspeed=maxSpeed/2;
            }
            onGround = false;
        }
        else{
            footY=((blockY+1)*Screen.blockSize)-1;
            onGround = true;
        }
        if(Screen.land[blockX][blockY]!=0&&Screen.land[blockX][blockY]!=5&&Screen.land[blockX][blockY]!=6&&Screen.land[blockX][blockY]!=7){
            footY-=16;
            blockY-=1;
        }
    }

    public void jump(int speed){
        if(onGround){
            vspeed=-speed;
        }
    }

    public void move(int speed){
        willMove = speed;
        if(Screen.land[(footY-1)/Screen.blockSize][(footX+willMove)/Screen.blockSize]==0||Screen.land[(footY-1)/Screen.blockSize][(footX+willMove)/Screen.blockSize]==5||Screen.land[(footY-1)/Screen.blockSize][(footX+willMove)/Screen.blockSize]==6||Screen.land[(footY-1)/Screen.blockSize][(footX+willMove)/Screen.blockSize]==7){
            footX+=willMove;
        }
        if(Screen.land[(footY-1)/Screen.blockSize][(footX+willMove)/Screen.blockSize]!=0&&Screen.land[(footY-1)/Screen.blockSize][(footX+willMove)/Screen.blockSize]!=5&&Screen.land[(footY-1)/Screen.blockSize][(footX+willMove)/Screen.blockSize]!=6&&Screen.land[(footY-1)/Screen.blockSize][(footX+willMove)/Screen.blockSize]!=7){

            //if colliding with walls a block away, this is why
            if(willMove>0){
                footX=((blockX/*+(willMove/Screen.blockSize)**/)*Screen.blockSize)+Screen.blockSize-1;
                if(Screen.land[(footY-1)/Screen.blockSize][blockX+1]==0||Screen.land[(footY-1)/Screen.blockSize][blockX+1]==5||Screen.land[(footY-1)/Screen.blockSize][blockX+1]==6||Screen.land[(footY-1)/Screen.blockSize][blockX+1]==7){
                    footX+=16;
                }
            }
            if(willMove<0){
                footX=((blockX+(willMove/Screen.blockSize))*Screen.blockSize)+1;
                if(Screen.land[(footY-1)/Screen.blockSize][blockX-1]==0||Screen.land[(footY-1)/Screen.blockSize][blockX-1]==5||Screen.land[(footY-1)/Screen.blockSize][blockX-1]==6||Screen.land[(footY-1)/Screen.blockSize][blockX-1]==7){
                    footX-=16;
                }
            }

        }
        willMove = 0;
    }

    public void setHspeed(int speed){
        hspeed = speed;
    }

    public void moveHspeed(){
        move(hspeed);
    }
}
