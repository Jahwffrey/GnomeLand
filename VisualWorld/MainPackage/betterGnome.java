package MainPackage;

import java.math.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jack
 * Date: 10/17/13
 * Time: 1:27 PM
 */
public class betterGnome{
    int blockX;
    int blockY;
    int x;
    int y;

    int hspeed;
    int vspeed;

    int blockSize = Screen.blockSize;

    int myCommand;

    int targetBlockX;
    int targetBlockY;

    int digTimer = 0;

    String name;

    int dirMod = 0;

    int pickAxeLevel;

    boolean imSelected;

    //CONSTRUCTORS!
    public betterGnome(){
        blockX=0;
        blockY=0;
        x=0;
        y=0;
        hspeed=0;
        vspeed=0;
        name = "nobody";
        imSelected = false;
        myCommand = 0;
        pickAxeLevel=1;
    }

    public betterGnome(int gridX,int gridY,String namer){
        blockX=gridX;
        blockY=gridY;
        x=gridX*blockSize;
        y=gridY*blockSize;
        hspeed=0;
        vspeed=0;
        name = namer;
        imSelected = false;
        myCommand = 0;
        pickAxeLevel=10;
    }

    //HIGHER COMMANDS:

    //MOVE DIRECTION AVOIDING OBSTACLES
    public void moveHorizontal(){
        if(blockX<targetBlockX){
            dirMod=1;
        }
        else if(blockX>targetBlockX){
            dirMod=-1;
        }
        else{
            dirMod=0;
            System.out.println(name + " has gotten to target X!");
            myCommand = 0;
        }
        if(!isFree(blockX+dirMod,blockY)&&!isFree(blockX+dirMod,blockY-1)&&!isFree(blockX,blockY+1)&&vspeed==0){
            dirMod=0;
            System.out.println(name + " cannot continue!");
            myCommand = 0;
        }
        if(isFree(blockX+dirMod,blockY)){
            hspeed = 2*dirMod;
        }
        if(isFree(blockX+dirMod,blockY+1)){
            if((x-(blockX*16)>6)&&(x-(blockX*16)<10)){
                if(vspeed==0&&!isFree(blockX,blockY+1)){
                    vspeed=-8;
                }
            }
        }
        if(!isFree(blockX+dirMod,blockY)){
            hspeed = 2*dirMod;
            if(vspeed==0&&!isFree(blockX,blockY+1)){
                vspeed=-8;
            }
        }
    }

    //GO DIRECTION DIGGING OBSTACLES
    public void digHorizontal(){
        //DETERMINE DIRECTION:
        if(blockX<targetBlockX){
            dirMod=1;
        }
        else if(blockX>targetBlockX){
            dirMod=-1;
        }
        else{
            dirMod=0;
            System.out.println(name + " has gotten to target X!");
            myCommand = 0;
        }
        if(isFree(blockX+dirMod,blockY)){
            hspeed=2*dirMod;
        }
        else{
            digBlock(blockX+dirMod,blockY);
        }
    }

    //GO DOWN DIGGING OBSTACLES
    public void digVertical(){
        if(blockY<targetBlockY){
            if(!isFree(blockX,blockY+1)){
                if(blockY+1!=Screen.land.length){
                    digBlock(blockX,blockY+1);
                }
                else{
                    System.out.println(name + " has gotten to the bottom!");
                    myCommand = 0;
                }
            }
        }
        else{
            System.out.println(name + " has gotten to targetY!");
            myCommand = 0;
        }
    }

    //DIG DIAGONAL
    public void digDiagonal(){
        if(blockX<targetBlockX){
            dirMod=1;
        }
        else if(blockX>targetBlockX){
            dirMod=-1;
        }
        else{
            dirMod=0;
            digVertical();
        }

        //LOWER!
        if(blockY<targetBlockY){
            if(isFree(blockX+dirMod,blockY+1)&&isFree(blockX+dirMod,blockY)){
                hspeed=2*dirMod;
            }
            else if(!isFree(blockX+dirMod,blockY)){
                if(x-(blockX*16)<7||x-(blockX*16)>9){
                    hspeed = 2*dirMod;
                }
                else{
                    hspeed = 0;
                }
                digBlock(blockX+dirMod,blockY);
            }
            else if(isFree(blockX+dirMod,blockY)&&!isFree(blockX+dirMod,blockY+1)){
                if(x-(blockX*16)<7||x-(blockX*16)>9){
                    hspeed = 2*dirMod;
                }
                else{
                    hspeed = 0;
                }
                digBlock(blockX+dirMod,blockY+1);
            }
        }

        //UPPER!
        else if(blockY>targetBlockY){
            if(isFree(blockX,blockY-1)&&isFree(blockX+dirMod,blockY-1)){
                moveHorizontal();
            }
            else if(!isFree(blockX,blockY-1)){
                if(x-(blockX*16)<7||x-(blockX*16)>9){
                    hspeed = 2*dirMod;
                }
                else{
                    hspeed = 0;
                }
                digBlock(blockX,blockY-1);
            }
            else if(isFree(blockX,blockY-1)&&!isFree(blockX+dirMod,blockY-1)){
                if(x-(blockX*16)<7||x-(blockX*16)>9){
                    hspeed = 2*dirMod;
                }
                else{
                    hspeed = 0;
                }
                digBlock(blockX+dirMod,blockY-1);
            }
        }
        //HORIZONTALLY
        else{
            digHorizontal();
        }

        if(blockX==targetBlockX&&blockY==targetBlockY){
            System.out.println(name + " has gotten to target space!");
            if(x-(blockX*16)<7||x-(blockX*16)>9){
                hspeed = 2*dirMod;
            }
            else{
                hspeed = 0;
            }
            myCommand = 0;
        }

        if(blockX==targetBlockX&&blockY>targetBlockY){
            System.out.println(name + " cannot continue!");
            if(x-(blockX*16)<7||x-(blockX*16)>9){
                hspeed = 2*dirMod;
            }
            else{
                hspeed = 0;
            }
            myCommand = 0;
        }
    }






    //MOVEMENT COMMANDS!
    public void goSpeed(){
        if(hspeed!=0){
            if(collideCheck(hspeed,0)!=0){
                moveHorizontal(hspeed);
            }
        }
        if(vspeed!=0){
            if(collideCheck(0,vspeed)!=0){
                moveVertical(vspeed);
            }
        }
    }

    public void moveHorizontal(int distance){
        x+=distance;
    }

    public void moveVertical(int distance){
        y+=distance;
    }

    public void fall(int grav, int maxSpeed){
        if(vspeed<maxSpeed){
            vspeed+=grav;
        }
        else if(vspeed>maxSpeed){
            vspeed=maxSpeed;
        }
    }

    //OTHER COMMANDS!!
    public void updateBlock(){
        blockX=x/blockSize;
        blockY=y/blockSize;
    }

    public boolean isFree(int gridX,int gridY){
        if(Screen.land[gridY][gridX]==0||Screen.land[gridY][gridX]==5||Screen.land[gridY][gridX]==6||Screen.land[gridY][gridX]==7){
            return true;
        }
        else{
            return false;
        }
    }

    public void digBlock(int gridX,int gridY){
        digTimer+=1;
        if(Screen.land[gridY][gridX]==1){
            if(digTimer>70/pickAxeLevel){
                Screen.land[gridY][gridX]=0;
                Screen.grassResource+=1;
                digTimer = 0;
            }
        }
        if(Screen.land[gridY][gridX]==2){
            if(digTimer>200/pickAxeLevel){
                Screen.land[gridY][gridX]=0;
                Screen.dirtResource+=1;
                digTimer = 0;
            }
        }
        if(Screen.land[gridY][gridX]==3){
            if(digTimer>500/pickAxeLevel){
                Screen.land[gridY][gridX]=0;
                Screen.stoneResource+=1;
                digTimer = 0;
            }
        }
        if(Screen.land[gridY][gridX]==4){
            if(digTimer>2000/pickAxeLevel){
                Screen.land[gridY][gridX]=0;
                Screen.metalResource+=1;
                digTimer = 0;
            }
        }
    }

    public int collideCheck(int xDist, int yDist){
        //directly right
        if(((x+xDist)-(blockX*blockSize)>blockSize-1)&&((y+yDist)-(blockY*blockSize)<blockSize-1&&((y+yDist)-(blockY*blockSize)>-1))){
            if(isFree(blockX+1,blockY)){
                return 1;
            }
            else{
                return 0;
            }
        }
        //directly left
        else if(((x+xDist)-(blockX*blockSize)<0)&&((y+yDist)-(blockY*blockSize)<blockSize-1&&((y+yDist)-(blockY*blockSize)>-1))){
            if(isFree(blockX-1,blockY)){
                return 1;
            }
            else{
                return 0;
            }
        }
        //directly down
        else if(((y+yDist)-(blockY*blockSize)>blockSize-2)&&((x+xDist)-(blockX*blockSize)<blockSize&&((x+xDist)-(blockX*blockSize)>-1))){
            if(isFree(blockX,blockY+1)){
                return 1;
            }
            else{
                return 0;
            }
        }
        //directly up
        else if(((y+yDist)-(blockY*blockSize)<0)&&((x+xDist)-(blockX*blockSize)<blockSize&&((x+xDist)-(blockX*blockSize)>-1))){
            if(isFree(blockX,blockY-1)){
                return 1;
            }
            else{
                return 0;
            }
        }
        //lower right
        else if(((y+yDist)-(blockY*blockSize)>blockSize-2)&&((x+xDist)-(blockX*blockSize)>blockSize-1)){
            if(isFree(blockX+1,blockY+1)){
                return 1;
            }
            else{
                return 0;
            }
        }
        //lower left
        else if(((y+yDist)-(blockY*blockSize)>blockSize-2)&&((x+xDist)-(blockX*blockSize)<0)){
            if(isFree(blockX-1,blockY+1)){
                return 1;
            }
            else{
                return 0;
            }
        }
        //upper left
        else if(((y+yDist)-(blockY*blockSize)<0)&&((x+xDist)-(blockX*blockSize)<0)){
            if(isFree(blockX-1,blockY-1)){
                return 1;
            }
            else{
                return 0;
            }
        }
        //upper right
        else if(((y+yDist)-(blockY*blockSize)<0)&&((x+xDist)-(blockX*blockSize)>blockSize-1)){
            if(isFree(blockX+1,blockY-1)){
                return 1;
            }
            else{
                return 0;
            }
        }
        else{
            return 1;
        }
    }

    public void moveToContactDirection(int direction){
        //1 = right
        //2 = down
        //3 = left
        //4 = up
        if(direction==1){
            x=(blockX*blockSize)+(blockSize-1);
        }
        if(direction==2){
            y=(blockY*Screen.blockSize)+14;
        }
        if(direction==3){
            x=(blockX*blockSize)+1;
        }
        if(direction==2){
            y=(blockY*blockSize)+2;
        }
    }
}
