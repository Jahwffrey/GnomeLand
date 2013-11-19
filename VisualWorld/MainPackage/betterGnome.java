package MainPackage;
import java.util.ArrayList;

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
    ArrayList<node> validPath = new ArrayList<node>();
    int whereInPath;


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
        x=gridX*blockSize+8;
        y=gridY*blockSize+8;
        hspeed=0;
        vspeed=0;
        name = namer;
        imSelected = false;
        myCommand = 0;
        pickAxeLevel=10;
    }

    public void findPath(int targX,int targY){
        pathFinder me = new pathFinder(blockX,blockY);
        validPath.clear();
        validPath=me.giveValidPath(targX,targY);
        if(validPath.size()>0){
            whereInPath=0;
        }
    }

    public void followPath(){
        int sept = 0;
        if(validPath.size()>0){
            if(whereInPath<validPath.size()){

                if(validPath.get(whereInPath).x>blockX&&validPath.get(whereInPath).y>=blockY){
                    hspeed=2;
                }
                if(validPath.get(whereInPath).x<blockX&&validPath.get(whereInPath).y>=blockY){
                    hspeed=-2;
                }
                if(validPath.get(whereInPath).x>blockX&&validPath.get(whereInPath).y<blockY){
                    if(vspeed>=0&&blockY>validPath.get(whereInPath).y){
                        if(x-(blockX*16)>10){
                            if(x-(blockX*16)<12||vspeed!=0){
                                hspeed=2;
                                vspeed=-5*(blockY-validPath.get(whereInPath).y);
                            }
                            else{
                                hspeed=-1;
                            }
                        }
                        else{
                            hspeed=1;
                        }
                    }
                }
                if(validPath.get(whereInPath).x<blockX&&validPath.get(whereInPath).y<blockY){
                    if(vspeed>=0&&blockY>validPath.get(whereInPath).y){
                        if(x-(blockX*16)<6){
                            if(x-(blockX*16)>4||vspeed!=0){
                                hspeed=-2;
                                vspeed=-5*(blockY-validPath.get(whereInPath).y);
                            }
                            else{
                                hspeed=1;
                            }
                        }
                        else{
                            hspeed=-1;
                        }
                    }
                }

                if(blockX==validPath.get(whereInPath).x){
                    if(blockY==validPath.get(whereInPath).y){
                        whereInPath++;
                    }
                    else if(blockY<validPath.get(whereInPath).y){
                        hspeed=0;
                    }
                    else if(blockY>validPath.get(whereInPath).y){
                        if(Screen.land[blockY][blockX]==9){
                            vspeed=-4;
                            if(x-(blockX*16)<10||x-(blockX*16)>6){
                                hspeed=0;
                            }
                            else{
                                if(x-(blockX*16)>10){
                                    hspeed=-1;
                                }
                                else{
                                    hspeed=1;
                                }
                            }
                        }
                        else{
                            whereInPath++;
                        }
                    }
                }



            }
            else{
                hspeed=0;
                whereInPath=0;
                validPath.clear();
                myCommand = 0;
            }
        }
    }

    //WILL BE EDITED OFTEN COMMANDS:
    public boolean isFree(int gridX,int gridY){
        switch(Screen.land[gridY][gridX]){
            //all the solids:
            case 1:
            case 2:
            case 3:
            case 4:
            case 8:
                return false;
        }
        return true;
    }

    public void buildAdjacent(int xxx, int yyy){
        if(Screen.inventory.queue.type.equals("harddirt")){
            Screen.land[yyy][xxx]=8;
            Screen.inventory.queue.count-=1;
        }
        else if(Screen.inventory.queue.type.equals("ladder")){
            Screen.land[yyy][xxx]=9;
            Screen.inventory.queue.count-=1;
        }

        if(Screen.inventory.queue.count==0){
            Screen.inventory.queue.type="nothing";
            Screen.inventory.queue.maxCount=0;
        }
    }

    //DIG!
    public void digBlock(int gridX,int gridY){
        digTimer+=1;
        if(Screen.land[gridY][gridX]==1){//grass
            if(digTimer>70/pickAxeLevel){
                Screen.land[gridY][gridX]=0;
                //Screen.grassResource+=1;
                Screen.inventory.addItem("grass", 1);
                digTimer = 0;
            }
        }
        if(Screen.land[gridY][gridX]==8){//harddirt
            if(digTimer>300/pickAxeLevel){
                Screen.land[gridY][gridX]=0;
                //Screen.grassResource+=1;
                Screen.inventory.addItem("harddirt", 1);
                digTimer = 0;
            }
        }
        if(Screen.land[gridY][gridX]==2){//dirt
            if(digTimer>200/pickAxeLevel){
                Screen.land[gridY][gridX]=0;
                //Screen.dirtResource+=1;
                Screen.inventory.addItem("dirt", 1);
                digTimer = 0;
            }
        }
        if(Screen.land[gridY][gridX]==3){//stone
            if(digTimer>500/pickAxeLevel){
                Screen.land[gridY][gridX]=0;
                //Screen.stoneResource+=1;
                Screen.inventory.addItem("stone", 1);
                digTimer = 0;
            }
        }
        if(Screen.land[gridY][gridX]==4){//metal
            if(digTimer>2000/pickAxeLevel){
                Screen.land[gridY][gridX]=0;
                //Screen.metalResource+=1;
                Screen.inventory.addItem("metal", 1);
                digTimer = 0;
            }
        }
        if(Screen.land[gridY][gridX]==6){//leaves
            if(digTimer>2/pickAxeLevel){
                Screen.land[gridY][gridX]=0;
                digTimer = 0;
            }
        }
        if(Screen.land[gridY][gridX]==9){//ladder
            if(digTimer>20/pickAxeLevel){
                Screen.land[gridY][gridX]=0;
                //Screen.metalResource+=1;
                Screen.inventory.addItem("ladder", 1);
                digTimer = 0;
            }
        }
        if(Screen.land[gridY][gridX]==5){//wood!
            if(digTimer>120/pickAxeLevel){
                Screen.land[gridY][gridX]=0;
                //Screen.woodRescource+=1;
                Screen.inventory.addItem("wood", 1);
                for(int yy = gridY-1;yy>10;yy--){
                    if(Screen.land[yy][gridX]==5){
                        Screen.land[yy][gridX]=0;
                        //Screen.woodRescource+=1;
                        Screen.inventory.addItem("wood", 1);
                    }
                    else{
                        if(Screen.land[yy][gridX]==6){
                            Screen.land[yy+1][gridX]=0;
                            Screen.land[yy][gridX]=0;
                            Screen.land[yy-1][gridX]=0;
                            Screen.land[yy][gridX-1]=0;
                            Screen.land[yy][gridX+1]=0;
                            Screen.land[yy+1][gridX-1]=0;
                            Screen.land[yy+1][gridX+1]=0;
                            Screen.land[yy+1][gridX+2]=0;
                            Screen.land[yy+1][gridX-2]=0;
                            Screen.land[yy][gridX-1]=0;
                            Screen.land[yy][gridX+1]=0;
                        }
                        break;
                    }
                }
                digTimer = 0;
            }
        }
    }

    public void moveHorizontal(){
        if(blockX<targetBlockX){
            dirMod=1;
        }
        else if(blockX>targetBlockX){
            dirMod=-1;
        }
        else{
            if(Screen.land[blockY][blockX]==9&&isFree(blockX,blockY-1)&&blockY>targetBlockY){
                vspeed=-4;
                goSpeed();
            }
            else{
                if(x-(blockX*16)<7||x-(blockX*16)>9){
                    hspeed = 7*dirMod;
                    goSpeed();
                }
                dirMod=0;
                System.out.println(name + " has gotten to target X!");
                myCommand = 0;
            }
        }

        if(Screen.land[blockY][blockX]==9){
            if(blockY>targetBlockY){
                vspeed=-4;
            }
        }

        if(!isFree(blockX+dirMod,blockY)&&!isFree(blockX+dirMod,blockY-1)&&!isFree(blockX,blockY+1)&&vspeed==0){
            if(x-(blockX*16)<7||x-(blockX*16)>9){
                hspeed = 7*dirMod;
                goSpeed();
            }
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
            if(x-(blockX*16)<7||x-(blockX*16)>9){
                hspeed = 7*dirMod;
                goSpeed();
            }
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

    //DIG ADJACENT
    public void digAdjacent(){
        if(Math.abs(targetBlockY-blockY)<=1&&Math.abs(targetBlockX-blockX)<=1){
            if(Screen.land[targetBlockY][targetBlockX]!=0){
                digBlock(targetBlockX,targetBlockY);
            }
            else{
                System.out.println(name + " has dug the block!");
                myCommand = 0;
            }
        }
        else{
            System.out.println(name +"'s block is too far away!");
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
            if(x-(blockX*16)<7||x-(blockX*16)>9){
                hspeed = 7*dirMod;
                goSpeed();
            }
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
                hspeed = 7*dirMod;
            }
            else{
                hspeed = 0;
            }
            myCommand = 0;
        }

        if(blockX==targetBlockX&&blockY>targetBlockY){
            System.out.println(name + " cannot continue!");
            if(x-(blockX*16)<7||x-(blockX*16)>9){
                hspeed = 7*dirMod;
            }
            else{
                hspeed = 0;
            }
            myCommand = 0;
        }
    }


    //LOWER COMMANDS

    //MOVEMENT COMMANDS!
    public void goSpeed(){
        if(hspeed!=0){
            if(collideCheck(hspeed,0)!=0){
                moveHorizontal(hspeed);
            }
        }
        if(vspeed!=0){
            if(!(Screen.land[blockY][blockX]==9&&targetBlockY<blockY&&vspeed>0)){
                if(collideCheck(0,vspeed)!=0){
                    moveVertical(vspeed);
                }
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
