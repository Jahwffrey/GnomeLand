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

    public static int rPressed = 0;
    public static int lPressed = 0;
    public static int uPressed = 0;
    public static int dPressed = 0;

    betterGnome Josh = new betterGnome(20,240);
    
    //GAMEPLAY VARS:
    //IMAGES:
    public static ImageIcon Character;
    public static Image CharNow;
    
    public static int land[][];

    
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
        gg.fillRect((Josh.x-screenX)-4,(Josh.y-screenY)-4,8,8);
        gg.setColor(black);
        gg.drawRect((Josh.x-screenX),(Josh.y-screenY),0,0);
        gg.drawRect((Josh.blockX*16)-screenX,(Josh.blockY*16)-screenY,blockSize,blockSize);
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
        case KeyEvent.VK_DOWN:
            dPressed = 1;
            break;
        case KeyEvent.VK_UP:
                uPressed = 1;
                break;
            case KeyEvent.VK_LEFT:
                lPressed = 1;
                break;
            case KeyEvent.VK_RIGHT:
                rPressed = 1;
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
            case KeyEvent.VK_DOWN:
                dPressed = 0;
                break;
            case KeyEvent.VK_UP:
                uPressed = 0;
                break;
            case KeyEvent.VK_LEFT:
                lPressed = 0;
                break;
            case KeyEvent.VK_RIGHT:
                rPressed = 0;
                break;
    		
    	}
    }

    public void mousePressed(MouseEvent mk){
        switch(mk.getButton()){
            case MouseEvent.BUTTON1:
                mouseX = ((mk.getX()-(blockSize/2)+4)+screenX)/blockSize;
                mouseY = (((mk.getY()+(blockSize/2))+screenY)/blockSize)-2;
                System.out.println(mouseX + "," + mouseY + " is " + land[mouseY][mouseX]);
                break;
            case MouseEvent.BUTTON3:
                break;
            case MouseEvent.BUTTON2:
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
                Josh.updateBlock();
                if(rPressed==1){
                    if(Josh.collideCheck(5,0)==1){
                         Josh.moveHorizontal(5);
                    }
                }
                if(lPressed==1){
                    if(Josh.collideCheck(-5,0)==1){
                        Josh.moveHorizontal(-5);
                    }
                }
                if(uPressed==1){
                    if(Josh.collideCheck(0,-5)==1){
                        Josh.moveVertical(-5);
                    }
                }
                if(dPressed==1){
                    if(Josh.collideCheck(0,5)==1){
                        Josh.moveVertical(5);
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

			try  { 
				Thread.sleep(1);
        	} catch(InterruptedException ex) { 
        	}
			repaint();
		}
	}
    
    
    
}