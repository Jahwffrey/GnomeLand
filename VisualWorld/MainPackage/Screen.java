package MainPackage;

//THE IMPORTS:
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import java.math.*;
import java.awt.image.*;
import java.net.URL;
import java.io.*;
import javax.imageio.*;

public class Screen extends JPanel implements Runnable{

    Thread thisThread;
    //SYSTEM VARS:

    public static int screenLength = 800;
    public static int screenHeight = 400;
    public static int screenX = 0;
    public static int screenY = 230*16;
    public static int worldLengthInBlocks = 1000;
    public static int worldHeightInBlocks = 500;

    public static int viewX=0;
    public static int viewY=230*16;

    public static int leftBorder = 0;
    public static int topBorder = 0;
    public static int blockSize = 16;
    public static int viewWidth = 51;//screenLength/blockSize;   IN BLOCKS!
    public static int viewHeight = 26;//IN BLOCKS!

    public static int upPress = 0;
    public static int downPress = 0;
    public static int leftPress = 0;
    public static int rightPress = 0;

    public static int mouseX = 0;
    public static int mouseY = 0;
    public static int superMouseX = 0;
    public static int superMouseY = 0;

    public static int menuItemToBuild = 25;

    public static int craftMenuToRightVal = 0;

    pathFinder pathy = new pathFinder(10,240);
    
    //GAMEPLAY VARS:
    public static menu inventory = new menu(5,5);

    //IMAGES:
    Image bottMenu;
    Image cancelBttn;
    Image moveBttn;
    Image digBttn;
    Image menuBttn;
    Image digAdjBttn;
    Image bigMenu;
    Image buildBttn;
    Image trashBttn;
    Image craftMenu;
    Image hardMudCraft;
    Image ggrass;
    Image ddirt;
    Image hhardmud;
    Image sstone;
    Image wwood;
    Image mmetal;
    Image ladderCraft;
    Image lladder;


    public static int land[][] = new int [worldHeightInBlocks][worldLengthInBlocks];
    public static boolean theMenuIsOpen = false;

    //THINGS ABOUT MOUSES AND GNOMES AND SELECTING AND SUCH!
    public static int selectedGnome;
    public static boolean aGnomeIsSelected = false;
    public static int mouseCommand = 0;
    //0=no command
    //1=itelligentMove
    //2=digging horizontal
    //3=down digging
    //4=diagonal digging
    //5=dig adjacent





    ArrayList<betterGnome> gnomes = new ArrayList<betterGnome>();
    ArrayList<craftingRecipe> craftables = new ArrayList<craftingRecipe>();
    ArrayList<craftingRecipe> canCraft = new ArrayList<craftingRecipe>();

    
    Color sky = new Color(84,206,255);
    Color grass = new Color(0,167,0);
    Color dirt  = new Color(86,34,0);
    Color darkdirt  = new Color(46,0,0);
    Color stone = new Color(128,128,128);
    Color metal = new Color(40,40,40);
    Color wood = new Color(114,45,0);
    Color leaves = new Color(10,200,10);
    Color water = new Color(0,0,255);
    Color black  = new Color(0,0,0);
    Color red = new Color(255,0,0);

    public boolean doPath=false;

    //STAGE PARTS:
    public Screen(){
    	thisThread=new Thread(this);
    	thisThread.start();
    }
    
    public void paintComponent (Graphics gg){

    	setOpaque(false);
    	super.paintComponent(gg);
    	
		/*for(int i = 0;i<land.length;i++){
			for(int ii = 0;ii<land[1].length;ii++){**/
        gg.setColor(sky);
        gg.fillRect(0,0,screenLength,screenHeight);
		for(int i = topBorder;i<topBorder+viewHeight;i++){
			for(int ii = leftBorder;ii<leftBorder+viewWidth;ii++){
				//if(land[i][ii]==0){
					//gg.setColor(sky);
				//}
				if(land[i][ii]==1){
					gg.setColor(grass);
				}
				else if(land[i][ii]==2){
					gg.setColor(dirt);
				}
				else if(land[i][ii]==3){
					gg.setColor(stone);
				}
				else if(land[i][ii]==4){
					gg.setColor(metal);
				}
				else if(land[i][ii]==5){
					gg.setColor(wood);
				}
				else if(land[i][ii]==6){
					gg.setColor(leaves);
				}
				else if(land[i][ii]==7){
					gg.setColor(water);
				}
                else if(land[i][ii]==8){
                    gg.setColor(darkdirt);
                }
				else{
					gg.setColor(sky);
				}
                if(land[i][ii]!=0){
				    gg.fillRect(ii*blockSize-screenX, i*blockSize-screenY, blockSize+1, blockSize+1);
                    if(land[i][ii]==9){
                        gg.drawImage(lladder,ii*blockSize-screenX,i*blockSize-screenY,this);
                    }
                }
			}
		}
        gg.setColor(red);
    	gg.drawRect(mouseX*blockSize-screenX,mouseY*blockSize-screenY,blockSize,blockSize);
        gg.fillRect(pathy.x*blockSize-screenX,pathy.y*blockSize-screenY,blockSize,blockSize);
        if(pathy.openList.size()>0){
            for(int i = 0;i<pathy.openList.size();i++){
                gg.setColor(black);
                gg.fillRect(pathy.openList.get(i).x*blockSize-screenX,pathy.openList.get(i).y*blockSize-screenY,blockSize,blockSize);
                gg.setColor(Color.white);
            }
        }
        gg.setColor(grass);
        if(pathy.closedList.size()>0){
            for(int i = 0; i<pathy.closedList.size();i++){
                if(pathy.closedList.get(i).prev!=null){
                    gg.drawLine(pathy.closedList.get(i).x*blockSize-screenX+8,pathy.closedList.get(i).y*blockSize-screenY+8,pathy.closedList.get(i).prev.x*blockSize-screenX+8,pathy.closedList.get(i).prev.y*blockSize-screenY+8);
                }
            }
        }
        gg.setColor(red);
        if(pathy.finalPath.size()>0){
            for(int i = 0; i<pathy.finalPath.size();i++){
                if(pathy.finalPath.get(i).prev!=null){
                    gg.drawLine(pathy.finalPath.get(i).x*blockSize-screenX+8,pathy.finalPath.get(i).y*blockSize-screenY+8,pathy.finalPath.get(i).prev.x*blockSize-screenX+8,pathy.finalPath.get(i).prev.y*blockSize-screenY+8);
                }
            }
        }
        for(int i = 1;i<gnomes.size();i++){
            gg.setColor(red);
            gg.fillRect((gnomes.get(i).x-screenX)-4,(gnomes.get(i).y-screenY)-4,8,8);
            gg.setColor(Color.WHITE);
            gg.drawString(gnomes.get(i).name,(gnomes.get(i).x-screenX)-(gnomes.get(i).name.length()*3),(gnomes.get(i).y-screenY)+15);
            //gg.drawRect((gnomes.get(i).x-screenX),(gnomes.get(i).y-screenY),0,0);
            //gg.drawRect((gnomes.get(i).blockX*16)-screenX,(gnomes.get(i).blockY*16)-screenY,blockSize,blockSize);
        }
        //gg.drawImage(bottMenu,0,screenHeight-64,this);
        gg.drawImage(menuBttn,mouseMenuButtonLeft(1)-3,mouseMenuButtonUp(2)+9,this);
        if(aGnomeIsSelected){
            gg.drawImage(cancelBttn,mouseMenuButtonLeft(2)-3,mouseMenuButtonUp(2)+9,this);
            gg.drawImage(moveBttn,mouseMenuButtonLeft(3)-3,mouseMenuButtonUp(2)+9,this);
            gg.drawImage(digBttn,mouseMenuButtonLeft(4)-3,mouseMenuButtonUp(2)+9,this);
            gg.drawImage(digAdjBttn,mouseMenuButtonLeft(5)-3,mouseMenuButtonUp(2)+9,this);
            gg.drawImage(buildBttn,mouseMenuButtonLeft(6)-3,mouseMenuButtonUp(2)+9,this);
        }
        if(!inventory.queue.type.equals("nothing")){
            gg.drawString(inventory.queue.type,superMouseX,superMouseY);
        }
        if(theMenuIsOpen){
            gg.setColor(black);
            gg.drawImage(bigMenu,mouseMenuButtonLeft(1)-3,mouseMenuButtonUp(7)+9,this);
            gg.drawImage(trashBttn,mouseMenuButtonLeft(6)-3,mouseMenuButtonUp(3)+9,this);
            gg.drawImage(craftMenu,mouseMenuButtonLeft(1)-3,mouseMenuButtonUp(8)+9,this);
            try{
                if(canCraft.size()>0){
                    for(int i = craftMenuToRightVal;i<4+craftMenuToRightVal;i++){
                        if(i<canCraft.size()&&canCraft.get(i)!=null){
                            gg.drawImage(canCraft.get(i).img,mouseMenuButtonLeft(2+(i-craftMenuToRightVal))-3,mouseMenuButtonUp(8)+9,this);
                        }
                    }
                }
            }catch(IndexOutOfBoundsException a){
                System.out.println("Out of Bounds!");
            }catch(NullPointerException r){
                System.out.println("Null Point!");
            }
            int r = 0;
            int a = 0;
            for(int i = 0;i<inventory.fullCount;i++){
                if(a==5){
                    r++;
                    a=0;
                }
                a++;
                if(inventory.items.get(i).type!="nothing"){
                    if(inventory.items.get(i).type=="grass"){
                        gg.drawImage(ggrass,mouseMenuButtonLeft(a)+5,mouseMenuButtonUp(10-(3+r))+9+2,this);
                    }
                    else if(inventory.items.get(i).type=="dirt"){
                        gg.drawImage(ddirt,mouseMenuButtonLeft(a)+5,mouseMenuButtonUp(10-(3+r))+9+2,this);
                    }
                    else if(inventory.items.get(i).type=="harddirt"){
                        gg.drawImage(hhardmud,mouseMenuButtonLeft(a)+5,mouseMenuButtonUp(10-(3+r))+9+2,this);
                    }
                    else if(inventory.items.get(i).type=="stone"){
                        gg.drawImage(sstone,mouseMenuButtonLeft(a)+5,mouseMenuButtonUp(10-(3+r))+9+2,this);
                    }
                    else if(inventory.items.get(i).type=="wood"){
                        gg.drawImage(wwood,mouseMenuButtonLeft(a)+5,mouseMenuButtonUp(10-(3+r))+9+2,this);
                    }
                    else if(inventory.items.get(i).type=="metal"){
                        gg.drawImage(mmetal,mouseMenuButtonLeft(a)+5,mouseMenuButtonUp(10-(3+r))+9+2,this);
                    }
                    else if(inventory.items.get(i).type=="ladder"){
                        gg.drawImage(lladder,mouseMenuButtonLeft(a)+5,mouseMenuButtonUp(10-(3+r))+9+2,this);
                    }
                    gg.drawString(Integer.toString(inventory.items.get(i).count),mouseMenuButtonLeft(a)+3,mouseMenuButtonUp(10-(3+r))+9+29);
                }
            }
        }
        gg.dispose();
    }

    public void keyPressed(KeyEvent evt){
    	switch(evt.getKeyCode()){
    	
    	case KeyEvent.VK_W:
    		upPress = 1;
    		break;
    	case KeyEvent.VK_S:
    		downPress = 1;
    		break;
    	case KeyEvent.VK_A:
    		leftPress = 1;
    		break;
    	case KeyEvent.VK_D:
    		rightPress = 1;
    		break;
            case KeyEvent.VK_1:
                System.out.println("Cancel!");
                //mouseCommand = 0;
                gnomes.get(selectedGnome).imSelected = false;
                aGnomeIsSelected = false;
                selectedGnome = 0;
                break;
            case KeyEvent.VK_2:
                mouseCommand = 1;
                break;
            case KeyEvent.VK_3:
                mouseCommand = 4;
                break;
            case KeyEvent.VK_4:
                mouseCommand = 5;
                break;
            case KeyEvent.VK_5:
                mouseCommand = 6;
                break;
            case KeyEvent.VK_P:
                mouseCommand = 10;
                break;
            case KeyEvent.VK_U:
                mouseCommand = 11;
                break;
            case KeyEvent.VK_O:
                pathy.pathFind(mouseX,mouseY);
                break;
            case KeyEvent.VK_I:
                doPath = true;
                break;
        }
    	
    }

    public void keyReleased(KeyEvent evt){
    	switch(evt.getKeyCode()){
    	
    	case KeyEvent.VK_W:
    		upPress = 0;
    		break;
    	case KeyEvent.VK_S:
    		downPress = 0;
    		break;
    	case KeyEvent.VK_A:
    		leftPress = 0;
    		break;
    	case KeyEvent.VK_D:
    		rightPress = 0;
    		break;
            case KeyEvent.VK_I:
                doPath = false;
                break;
    	}
    }

    public void mousePressed(MouseEvent mk){
        switch(mk.getButton()){
            case MouseEvent.BUTTON1:
                mouseX = ((mk.getX()-(blockSize/2)+4)+screenX)/blockSize;
                mouseY = (((mk.getY()+(blockSize/2))+screenY)/blockSize)-2;
                System.out.println(mouseX + "," + mouseY + " is " + land[mouseY][mouseX]);
                if(mouseIsInMenuPosition(mk.getX(),mk.getY(),1,1)){
                    if(!theMenuIsOpen){
                        theMenuIsOpen = true;
                        System.out.println(theMenuIsOpen);
                    }
                    else{
                        theMenuIsOpen = false;
                        System.out.println(theMenuIsOpen);
                    }
                }
                if(!aGnomeIsSelected){
                    for(int i = 1;i<gnomes.size();i++){
                        if(mouseX==gnomes.get(i).blockX&&mouseY==gnomes.get(i).blockY){
                            gnomes.get(i).imSelected = true;
                            aGnomeIsSelected=true;
                            selectedGnome = i;
                            System.out.println("Success!");
                            i=gnomes.size();
                        }
                    }
                    System.out.println("You have selected "+gnomes.get(selectedGnome).name);
                }
                else{
                    if(mouseIsInMenuPosition(mk.getX(),mk.getY(),1,1)){
                        System.out.println("Menu, bro");
                    }
                    else if(mouseIsInMenuPosition(mk.getX(),mk.getY(),2,1)){
                        System.out.println("Cancel!");
                        //mouseCommand = 0;
                        gnomes.get(selectedGnome).imSelected = false;
                        aGnomeIsSelected = false;
                        selectedGnome = 0;
                    }
                    //if(mk.getX()>=76&&mk.getX()<=108&mk.getY()>=screenHeight-34&&mk.getY()<=screenHeight-2){
                    else if(mouseIsInMenuPosition(mk.getX(),mk.getY(),3,1)){
                        System.out.println("Move!");
                        mouseCommand = 1;
                    }
                    //if(mk.getX()>=112&&mk.getX()<=144&mk.getY()>=screenHeight-34&&mk.getY()<=screenHeight-2){
                    else if(mouseIsInMenuPosition(mk.getX(),mk.getY(),4,1)){
                        System.out.println("Dig!");
                        mouseCommand = 4;
                    }
                    else if(mouseIsInMenuPosition(mk.getX(),mk.getY(),5,1)){
                        System.out.println("Dig Adjacent!");
                        mouseCommand = 5;
                    }
                    else if(mouseIsInMenuPosition(mk.getX(),mk.getY(),6,1)){
                        System.out.println("Building!");
                        theMenuIsOpen = true;
                        mouseCommand = 6;
                    }
                    else if(mouseX==gnomes.get(selectedGnome).blockX&&mouseY==gnomes.get(selectedGnome).blockY&&false){
                        System.out.println("You just deselected "+gnomes.get(selectedGnome).name);
                        gnomes.get(selectedGnome).imSelected = false;
                        aGnomeIsSelected = false;
                        selectedGnome = 0;
                    }
                    else{
                        switch(mouseCommand){
                            case 0:
                                System.out.println("You just deselected "+gnomes.get(selectedGnome).name);
                                gnomes.get(selectedGnome).imSelected = false;
                                aGnomeIsSelected = false;
                                selectedGnome = 0;
                                break;
                            case 1:
                                gnomes.get(selectedGnome).targetBlockX=mouseX;
                                gnomes.get(selectedGnome).targetBlockY=mouseY;
                                gnomes.get(selectedGnome).myCommand = 1;
                                System.out.println(gnomes.get(selectedGnome).name + " is now moving towards "+gnomes.get(selectedGnome).targetBlockX);
                                break;
                            case 2:
                                if(mouseX!=gnomes.get(selectedGnome).blockX){
                                    gnomes.get(selectedGnome).targetBlockX=mouseX;
                                    gnomes.get(selectedGnome).myCommand = 2;
                                    System.out.println(gnomes.get(selectedGnome).name + " is now digging horizontally towards "+gnomes.get(selectedGnome).targetBlockX);
                                }
                                else{
                                    System.out.println("Selected something further away!");
                                }
                                break;
                            case 3:
                                if(mouseY!=gnomes.get(selectedGnome).blockY){
                                    gnomes.get(selectedGnome).targetBlockY=mouseY;
                                    gnomes.get(selectedGnome).myCommand = 3;
                                    System.out.println(gnomes.get(selectedGnome).name + " is now digging vertically towards "+gnomes.get(selectedGnome).targetBlockY);
                                }
                                else{
                                    System.out.println("Selected something lower!!");
                                }
                                break;
                            case 4:
                                if(!(mouseY==gnomes.get(selectedGnome).blockY&&mouseX==gnomes.get(selectedGnome).blockX)){
                                    gnomes.get(selectedGnome).targetBlockY=mouseY;
                                    gnomes.get(selectedGnome).targetBlockX=mouseX;
                                    gnomes.get(selectedGnome).myCommand = 4;
                                    System.out.println(gnomes.get(selectedGnome).name + " is now digging smart towards ("+gnomes.get(selectedGnome).targetBlockX+","+gnomes.get(selectedGnome).targetBlockX+")");
                                }
                                else{
                                    System.out.println("Select and different block!");
                                }
                                break;
                            case 5:
                                if(Math.abs(mouseY-gnomes.get(selectedGnome).blockY)<=1&&Math.abs(mouseX-gnomes.get(selectedGnome).blockX)<=1){
                                    gnomes.get(selectedGnome).targetBlockY=mouseY;
                                    gnomes.get(selectedGnome).targetBlockX=mouseX;
                                    gnomes.get(selectedGnome).myCommand = 5;
                                    System.out.println(gnomes.get(selectedGnome).name + " is now digging!");
                                }
                                break;
                            case 6:
                                boolean canBuild = true;
                                if(Math.abs(mouseY-gnomes.get(selectedGnome).blockY)<=1&&Math.abs(mouseX-gnomes.get(selectedGnome).blockX)<=1){
                                    gnomes.get(selectedGnome).targetBlockY=mouseY;
                                    gnomes.get(selectedGnome).targetBlockX=mouseX;
                                    for(int i = 1;i<gnomes.size();i++){
                                        if(mouseX==gnomes.get(i).blockX&&mouseY==gnomes.get(i).blockY){
                                            canBuild = false;
                                        }
                                    }
                                    if(canBuild){
                                        if(land[mouseY][mouseX]==0){
                                            if(inventory.queue.type!="nothing"&&inventory.queue.count>0){
                                                gnomes.get(selectedGnome).buildAdjacent(mouseX,mouseY);
                                                System.out.println(gnomes.get(selectedGnome).name + " built a "+inventory.queue.type);
                                            }
                                        }
                                    }
                                    /*gnomes.get(selectedGnome).imSelected = false;
                                    aGnomeIsSelected = false;
                                    selectedGnome = 0;**/
                                }
                                break;
                        }
                    }
                    break;
                }
            case MouseEvent.BUTTON3:
                System.out.println(mk.getX()+ " " + mk.getY());
                //ACCESS INVENTORY:
                if(theMenuIsOpen){
                    for(int i = 6;i>1;i--){
                        for(int ii = 1;ii<6;ii++){
                            if(mouseIsInMenuPosition(mk.getX(),mk.getY(),ii,i)){
                                inventory.accessSpace(inventory.twoDToOneD((ii-1)+((6-i)*4),6-i),3);
                            }
                        }
                    }
                    if(mouseIsInMenuPosition(mk.getX(),mk.getY(),6,2)){
                        inventory.throwAwayQueue();
                    }
                    if(mouseIsInMenuPosition(mk.getX(),mk.getY(),1,7)){
                        if(craftMenuToRightVal>0)
                            craftMenuToRightVal-=1;
                    }
                    if(mouseIsInMenuPosition(mk.getX(),mk.getY(),6,7)){
                        if(canCraft.size()>craftMenuToRightVal+4)
                            craftMenuToRightVal+=1;
                    }
                    if(mouseIsInMenuPosition(mk.getX(),mk.getY(),2,7)){
                        if(canCraft.size()>0){
                            canCraft.get(craftMenuToRightVal).craft();
                        }
                    }
                    if(mouseIsInMenuPosition(mk.getX(),mk.getY(),3,7)){
                        if(canCraft.size()>1){
                            canCraft.get(craftMenuToRightVal+1).craft();
                        }
                    }
                    if(mouseIsInMenuPosition(mk.getX(),mk.getY(),4,7)){
                        if(canCraft.size()>2){
                            canCraft.get(craftMenuToRightVal+2).craft();
                        }
                    }
                    if(mouseIsInMenuPosition(mk.getX(),mk.getY(),5,7)){
                        if(canCraft.size()>3){
                            canCraft.get(craftMenuToRightVal+3).craft();
                        }
                    }
                }
                if(aGnomeIsSelected){
                    //if(mk.getX()>=40&&mk.getX()<=72&mk.getY()>=screenHeight-34&&mk.getY()<=screenHeight-2){
                }
                break;
            case MouseEvent.BUTTON2:
                //ACCESS INVENTORY:
                for(int i = 6;i>1;i--){
                    for(int ii = 1;ii<6;ii++){
                        if(mouseIsInMenuPosition(mk.getX(),mk.getY(),ii,i)){
                            inventory.accessSpace(inventory.twoDToOneD((ii-1)+((6-i)*4),6-i),1);
                        }
                    }
                }
                land[mouseY][mouseX]=9;
                break;
        }
    }

    public void mouseMoved(MouseEvent mk){
        mouseX = ((mk.getX()-(blockSize/2)+4)+screenX)/blockSize;
        mouseY = (((mk.getY()+(blockSize/2))+screenY)/blockSize)-2;
        superMouseX=mk.getX();
        superMouseY=mk.getY();
        if(mouseCommand==10){
            land[mouseY][mouseX]=0;
        }
        if(mouseCommand==11){
            land[mouseY][mouseX]=1;
        }
    }

    public void mouseReleased(MouseEvent mk){
        switch(mk.getButton()){

        }
    }

    public int mouseMenuButtonLeft(int xx){
        return (4*xx)+(32*(xx-1))+3;
    }
    public int mouseMenuButtonRight(int xx){
        return (4*xx)+(32*(xx-1))+32+3;
    }
    public int mouseMenuButtonUp(int yy){
        return (screenHeight-(2*yy))-(32*(yy-1))-32-3;
    }
    public int mouseMenuButtonDown(int yy){
        return (screenHeight-(2*yy))-(32*(yy-1))-3;
    }

    public boolean mouseIsInMenuPosition(int x, int y, int xx, int yy){
        if(x>=mouseMenuButtonLeft(xx)&&x<=mouseMenuButtonRight(xx)&&y<=mouseMenuButtonDown(yy)&&y>=mouseMenuButtonUp(yy)){
            return(true);
        }
        else{
            return(false);
        }
    }
    
    
	@Override
	public void run() {

        long now;
        int waitFrames = 10;
        int maxWaitFrames = 10;



        //LOAD IMAGES:
        URL imgPath = getClass().getResource("/MainPackage/pics/bottom_menu.png");
        try {
            bottMenu = ImageIO.read(imgPath);
            imgPath = getClass().getResource("/MainPackage/pics/cancel_button.png");
            cancelBttn = ImageIO.read(imgPath);
            imgPath = getClass().getResource("/MainPackage/pics/move_button.png");
            moveBttn = ImageIO.read(imgPath);
            imgPath = getClass().getResource("/MainPackage/pics/dig_button.png");
            digBttn = ImageIO.read(imgPath);
            imgPath = getClass().getResource("/MainPackage/pics/menu_button.png");
            menuBttn = ImageIO.read(imgPath);
            imgPath = getClass().getResource("/MainPackage/pics/dig_adjacent.png");
            digAdjBttn = ImageIO.read(imgPath);
            imgPath = getClass().getResource("/MainPackage/pics/big_menu.png");
            bigMenu = ImageIO.read(imgPath);
            imgPath = getClass().getResource("/MainPackage/pics/build_button.png");
            buildBttn = ImageIO.read(imgPath);
            imgPath = getClass().getResource("/MainPackage/pics/trash_button.png");
            trashBttn = ImageIO.read(imgPath);
            imgPath = getClass().getResource("/MainPackage/pics/craft_menu.png");
            craftMenu = ImageIO.read(imgPath);

            imgPath = getClass().getResource("/MainPackage/pics/hardMudCraft.png");
            hardMudCraft = ImageIO.read(imgPath);
            imgPath = getClass().getResource("/MainPackage/pics/ladderCraft.png");
            ladderCraft = ImageIO.read(imgPath);

            imgPath = getClass().getResource("/MainPackage/pics/dirt.png");
            ddirt = ImageIO.read(imgPath);
            imgPath = getClass().getResource("/MainPackage/pics/grass.png");
            ggrass = ImageIO.read(imgPath);
            imgPath = getClass().getResource("/MainPackage/pics/hardmud.png");
            hhardmud = ImageIO.read(imgPath);
            imgPath = getClass().getResource("/MainPackage/pics/stone.png");
            sstone = ImageIO.read(imgPath);
            imgPath = getClass().getResource("/MainPackage/pics/wood.png");
            wwood = ImageIO.read(imgPath);
            imgPath = getClass().getResource("/MainPackage/pics/metal.png");
            mmetal = ImageIO.read(imgPath);
            imgPath = getClass().getResource("/MainPackage/pics/ladder.png");
            lladder = ImageIO.read(imgPath);
        } catch (IOException e) {System.out.println("NOOOOO!");}

        //CREATE SOME VARS
		land = World.generate(worldHeightInBlocks, worldLengthInBlocks, 10, 10, 12, 400, 400);

        //pathy.pathFind(3,270);

		int testVar = 0;
        gnomes.add(new betterGnome(10,240,"Nobody"));
        gnomes.add(new betterGnome(20,240,"Josh"));
        gnomes.add(new betterGnome(21,240,"Bill"));
        gnomes.add(new betterGnome(22,200,"Kyre"));
        gnomes.add(new betterGnome(23,240,"Raxacoricofallapatorius"));
        gnomes.add(new betterGnome(24,240,"J"));


        //        ALL THE CRAFING RECIPES:
            //Hard Mud:
        craftables.add(new craftingRecipe("dirt",50,"harddirt",1,hardMudCraft));
        craftables.add(new craftingRecipe("wood",6,"ladder",3,ladderCraft));

        inventory.addItem("harddirt",500);
        inventory.addItem("ladder",200);


		while(true){

            if(waitFrames<1){

                if(doPath) pathy.traversePath();

                //WHERE ARE ALL GNOME VALUES?
                for(int i = 1;i<gnomes.size();i++){
                    //EACH GNOME:
                    gnomes.get(i).updateBlock();

                    switch(gnomes.get(i).myCommand){
                        case 1:
                            gnomes.get(i).moveHorizontal();
                            break;
                        case 2:
                            gnomes.get(i).digHorizontal();
                            break;
                        case 3:
                            gnomes.get(i).digVertical();
                            break;
                        case 4:
                            gnomes.get(i).digDiagonal();
                            break;
                        case 5:
                            gnomes.get(i).digAdjacent();
                            break;
                    }

                    //MAKE THEM FALL!
                    if(gnomes.get(i).collideCheck(0,5)!=0){
                        gnomes.get(i).fall(1,5);
                    }
                    else{
                        if(gnomes.get(i).vspeed>0){
                            gnomes.get(i).y=(gnomes.get(i).blockY*blockSize)+14;
                            gnomes.get(i).vspeed=0;
                        }
                    }

                    //MAKE EM' MOVE!
                    gnomes.get(i).goSpeed();
                }

                //BUILD THE CAN CRAFT RECIPE:
                if(theMenuIsOpen){
                    canCraft.clear();
                    for(int i = 0;i<craftables.size();i++){
                        if(craftables.get(i).canBeMade()){
                            canCraft.add(craftables.get(i));
                        }
                    }
                }

                waitFrames=maxWaitFrames;
            }
            else{
                waitFrames-=1;
            }

            leftBorder = (viewX/blockSize);
            topBorder = (viewY/blockSize);

            if(upPress == 1){
                viewY-=1;
                screenY-=1;
            }
            if(downPress == 1){
                viewY+=1;
                screenY+=1;
            }
            if(rightPress == 1){
                if(screenX+screenLength<(worldLengthInBlocks*16-32)){
                    viewX+=1;
                    screenX+=1;
                }
            }
            if(leftPress == 1){
                viewX-=1;
                screenX-=1;
            }

            if(leftBorder<0){
                leftBorder=0;
                viewX+=1;
            }
            else if((leftBorder+viewWidth)>=land[1].length){
                leftBorder=(land[1].length-viewWidth)-2;
                viewX-=1;
            }

            if(topBorder<0){
                topBorder=0;
                viewY+=1;
            }
            else if((topBorder+viewHeight)>=land.length){
                topBorder=(land.length-viewHeight)-2;
                viewY-=1;
            }

            if(screenX<0){
                screenX=0;
                viewX=screenX;
            }
            if(screenY<0){
                screenY=0;
                viewY=screenY;
            }
            if(screenX+screenLength>(worldLengthInBlocks*16-32)){
                screenX=(worldLengthInBlocks*16-32)-screenLength;
                screenX=screenY;
            }
            if(screenY+screenHeight>(worldHeightInBlocks*16-8)){
                screenY=(worldHeightInBlocks*16-8)-screenHeight;
                viewY=screenY;
            }

            now = System.currentTimeMillis();
            while((System.currentTimeMillis()-now)<1){
            }
			/*try  {
				Thread.sleep(1);
                //Thread.yield();
        	} catch(InterruptedException ex) {
        	}**/
			repaint();
		}
	}
    
    
    
}