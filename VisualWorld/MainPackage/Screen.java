package MainPackage;

//THE IMPORTS:
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.*;

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
    
    //GAMEPLAY VARS:
    //IMAGES:
    public static ImageIcon Character;
    public static Image CharNow;
    
    public static int land[][];

    gnome Bill = new gnome(10,8,20,0,0);
    
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
		for(int i = topBorder;i<topBorder+viewHeight;i++){
			for(int ii = leftBorder;ii<leftBorder+viewWidth;ii++){
				if(land[i][ii]==0){
					gg.setColor(sky);
				}
				else if(land[i][ii]==1){
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
				gg.fillRect(ii*blockSize-screenX, i*blockSize-screenY, blockSize, blockSize);
			}
		}
        gg.setColor(red);
    	gg.drawRect(mouseX*blockSize-screenX,mouseY*blockSize-screenY,blockSize,blockSize);
        gg.fillRect(Bill.headX-screenX-(Bill.width/2),Bill.headY-screenY,Bill.width,Bill.height+1);
        gg.setColor(black);
        //gg.drawLine(Bill.headX-screenX,Bill.headY-screenY,Bill.footX-screenX,Bill.footY-screenY);
        //gg.drawRect((Bill.blockX*blockSize)-screenX,(Bill.blockY*blockSize)-screenY,blockSize,blockSize);
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
                Bill.jump(10);
                break;
        }
    }

    public void mouseMoved(MouseEvent mk){
        mouseX = (mk.getX()+screenX)/blockSize;
        mouseY = (mk.getY()+screenY)/blockSize;
    }

    public void mouseReleased(MouseEvent mk){
        switch(mk.getButton()){

        }
    }

    
    
	@Override
	public void run() {
		
		land = World.generate(worldHeightInBlocks, worldLengthInBlocks, 10, 10, 12, 400, 400);
		int testVar = 0;

		while(true){

            if(waitFrames<1){
                //WHERE ARE ALL GNOME VALUES?
                Bill.headY=Bill.footY-Bill.height;
                Bill.headX=Bill.footX;
                    //WHICH BLOCK?
                Bill.blockX=(Bill.footX/blockSize);
                Bill.blockY=(Bill.footY/blockSize);

                //WHAT NOW???
                Bill.fall(1,10);

                /*
                if(land[(Bill.footY+4)/16][Bill.footX/16]!=1){
                    Bill.footY+=6;
                }
                else{
                    Bill.footY=((Bill.blockY+1)*blockSize)-1;
                }**/

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
        	} catch(InterruptedException ex) { 
        	}
			repaint();
		}
	}
    
    
    
}