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
    public static int waitFrames = 10;
    public static int maxWaitFrames = 10;

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

    public static int grassResource = 0;
    public static int woodRescource = 0;
    public static int dirtResource = 0;
    public static int stoneResource = 0;
    public static int metalResource = 0;
    
    //GAMEPLAY VARS:

    //IMAGES:
    Image bottMenu;
    Image cancelBttn;
    Image moveBttn;
    Image digBttn;
    Image menuBttn;
    Image digAdjBttn;
    Image bigMenu;


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

    
    Color sky = new Color(84,206,255);
    Color grass = new Color(0,167,0);
    Color dirt  = new Color(86,34,0);
    Color stone = new Color(128,128,128);
    Color metal = new Color(40,40,40);
    Color wood = new Color(114,45,0);
    Color leaves = new Color(10,200,10);
    Color water = new Color(0,0,255);
    Color black  = new Color(0,0,0);
    Color red = new Color(255,0,0);
    
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
				else{
					gg.setColor(black);
				}
                if(land[i][ii]!=0){
				    gg.fillRect(ii*blockSize-screenX, i*blockSize-screenY, blockSize+1, blockSize+1);
                }
			}
		}
        gg.setColor(red);
    	gg.drawRect(mouseX*blockSize-screenX,mouseY*blockSize-screenY,blockSize,blockSize);
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
        }
        if(theMenuIsOpen){
            gg.drawImage(bigMenu,mouseMenuButtonLeft(1)-3,mouseMenuButtonUp(7)+9,this);
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
            case KeyEvent.VK_0:
                mouseCommand = 0;
                break;
            case KeyEvent.VK_1:
                mouseCommand = 1;
                break;
            case KeyEvent.VK_2:
                mouseCommand = 2;
                break;
            case KeyEvent.VK_3:
                mouseCommand = 3;
                break;
            case KeyEvent.VK_4:
                mouseCommand = 4;
                break;
            case KeyEvent.VK_5:
                mouseCommand = 5;
                break;
            case KeyEvent.VK_6:
                mouseCommand = 6;
                break;
            case KeyEvent.VK_7:
                mouseCommand = 7;
                break;
            case KeyEvent.VK_8:
                mouseCommand = 8;
                break;
            case KeyEvent.VK_9:
                mouseCommand = 9;
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
    	}
    }

    public void mousePressed(MouseEvent mk){
        switch(mk.getButton()){
            case MouseEvent.BUTTON1:
                mouseX = ((mk.getX()-(blockSize/2)+4)+screenX)/blockSize;
                mouseY = (((mk.getY()+(blockSize/2))+screenY)/blockSize)-2;
                System.out.println(mouseX + "," + mouseY + " is " + land[mouseY][mouseX]);
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
                    if(mouseX==gnomes.get(selectedGnome).blockX&&mouseY==gnomes.get(selectedGnome).blockY&&false){
                        System.out.println("You just deselected "+gnomes.get(selectedGnome).name);
                        gnomes.get(selectedGnome).imSelected = false;
                        aGnomeIsSelected = false;
                        selectedGnome = 0;
                    }
                    switch(mouseCommand){
                        case 0:
                            System.out.println("You just deselected "+gnomes.get(selectedGnome).name);
                            gnomes.get(selectedGnome).imSelected = false;
                            aGnomeIsSelected = false;
                            selectedGnome = 0;
                            break;
                        case 1:
                            if(mouseX!=gnomes.get(selectedGnome).blockX){
                                gnomes.get(selectedGnome).targetBlockX=mouseX;
                                gnomes.get(selectedGnome).targetBlockY=mouseY;
                                gnomes.get(selectedGnome).myCommand = 1;
                                System.out.println(gnomes.get(selectedGnome).name + " is now moving towards "+gnomes.get(selectedGnome).targetBlockX);
                                gnomes.get(selectedGnome).imSelected = false;
                                aGnomeIsSelected = false;
                                selectedGnome = 0;
                            }
                            else{
                                System.out.println("Selected something further away!");
                            }
                            break;
                        case 2:
                            if(mouseX!=gnomes.get(selectedGnome).blockX){
                                gnomes.get(selectedGnome).targetBlockX=mouseX;
                                gnomes.get(selectedGnome).myCommand = 2;
                                System.out.println(gnomes.get(selectedGnome).name + " is now digging horizontally towards "+gnomes.get(selectedGnome).targetBlockX);
                                gnomes.get(selectedGnome).imSelected = false;
                                aGnomeIsSelected = false;
                                selectedGnome = 0;
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
                                gnomes.get(selectedGnome).imSelected = false;
                                aGnomeIsSelected = false;
                                selectedGnome = 0;
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
                                gnomes.get(selectedGnome).imSelected = false;
                                aGnomeIsSelected = false;
                                selectedGnome = 0;
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
                                gnomes.get(selectedGnome).imSelected = false;
                                aGnomeIsSelected = false;
                                selectedGnome = 0;
                            }
                            break;
                    }
                }
                break;
            case MouseEvent.BUTTON3:
                System.out.println(mk.getX()+ " " + mk.getY());
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
                if(aGnomeIsSelected){
                    //if(mk.getX()>=40&&mk.getX()<=72&mk.getY()>=screenHeight-34&&mk.getY()<=screenHeight-2){
                    if(mouseIsInMenuPosition(mk.getX(),mk.getY(),2,1)){
                        System.out.println("Cancel!");
                        mouseCommand = 0;
                        gnomes.get(selectedGnome).imSelected = false;
                        aGnomeIsSelected = false;
                        selectedGnome = 0;
                    }
                    //if(mk.getX()>=76&&mk.getX()<=108&mk.getY()>=screenHeight-34&&mk.getY()<=screenHeight-2){
                    if(mouseIsInMenuPosition(mk.getX(),mk.getY(),3,1)){
                        System.out.println("Move!");
                        mouseCommand = 1;
                    }
                    //if(mk.getX()>=112&&mk.getX()<=144&mk.getY()>=screenHeight-34&&mk.getY()<=screenHeight-2){
                    if(mouseIsInMenuPosition(mk.getX(),mk.getY(),4,1)){
                        System.out.println("Dig!");
                        mouseCommand = 4;
                    }
                    if(mouseIsInMenuPosition(mk.getX(),mk.getY(),5,1)){
                        System.out.println("Dig Adjacent!");
                        mouseCommand = 5;
                    }
                }
                break;
            case MouseEvent.BUTTON2:
                break;
        }
    }

    //public void mouseMoved(MouseEvent mk){
        /*mouseX = (mk.getX()+screenX)/blockSize;
        mouseY = (mk.getY()+screenY)/blockSize;**/
    //}

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
        } catch (IOException e) {System.out.println("NOOOOO!");}

        //CREATE SOME VARS
		land = World.generate(worldHeightInBlocks, worldLengthInBlocks, 10, 10, 12, 400, 400);
		int testVar = 0;
        gnomes.add(new betterGnome(10,240,"Nobody"));
        gnomes.add(new betterGnome(20,240,"Josh"));
        gnomes.add(new betterGnome(21,240,"Bill"));
        gnomes.add(new betterGnome(22,200,"Kyre"));


		while(true){

            if(waitFrames<1){
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

			try  {
				Thread.sleep(1);
                //Thread.yield();
        	} catch(InterruptedException ex) {
        	}
			repaint();
		}
	}
    
    
    
}