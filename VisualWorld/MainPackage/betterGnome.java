package MainPackage;

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

    int tempX;
    int tempY;

    int blockSize = Screen.blockSize;

    public betterGnome(){
        blockX=0;
        blockY=0;
        x=0;
        y=0;
        tempX=0;
        tempY=0;
    }

    public betterGnome(int gridX,int gridY){
        blockX=gridX;
        blockY=gridY;
        x=gridX*blockSize;
        y=gridY*blockSize;
        tempX=x;
        tempY=y;
    }

    public void moveHorizontal(int distance){
        x+=distance;
    }

    public void moveVertical(int distance){
        y+=distance;
    }

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
}
