package MainPackage;

//THE IMPORTS:
import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class Screen extends JPanel implements Runnable {

    Thread thisThread;
    //SYSTEM VARS:
    public static int screenLength = 800;
    public static int screenHeight = 400;
    public static int screenX = 0;
    public static int screenY = 200*16;
    
    public static int viewX=0;
    public static int viewY=200*16;
    
    public static int leftBorder = 0;
    public static int topBorder = 0;
    public static int blockSize = 16;
    public static int viewWidth = 51;//screenLength/blockSize;   IN BLOCKS!
    public static int viewHeight = 26;//IN BLOCKS!
    
    public static int upPress = 0;
    public static int downPress = 0;
    public static int leftPress = 0;
    public static int rightPress = 0;
    
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
    	
    }

    public void keyPressed(KeyEvent evt){
    	switch(evt.getKeyCode()){
    	
    	case KeyEvent.VK_UP:
    		upPress = 1;
    		break;
    	case KeyEvent.VK_DOWN:
    		downPress = 1;
    		break;
    	case KeyEvent.VK_LEFT:
    		leftPress = 1;
    		break;
    	case KeyEvent.VK_RIGHT:
    		rightPress = 1;
    		break;
    	
    	}
    	
    }

    public void keyReleased(KeyEvent evt){
    	switch(evt.getKeyCode()){
    	
    	case KeyEvent.VK_UP:
    		upPress = 0;
    		break;
    	case KeyEvent.VK_DOWN:
    		downPress = 0;
    		break;
    	case KeyEvent.VK_LEFT:
    		leftPress = 0;
    		break;
    	case KeyEvent.VK_RIGHT:
    		rightPress = 0;
    		break;
    		
    	}
    }
    
    
	@Override
	public void run() {
		
		land = World.generate(400,400,5,5,6,200,400);
		int testVar = 0;
		
		/*String showTime = "";
		for(int i = 0;i<land.length;i++){
			for(int ii = 0;ii<land[i].length;ii++){
				if(land[i][ii]!=3){
					showTime+=land[i][ii];
				}
				else{
					showTime+="�";
				}
			}
			showTime+="\n";
		}
		System.out.println(showTime);
		**/
		while(true){
			
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
                viewX+=1;
                screenX+=1;
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
			

			try  { 
				Thread.sleep(1); 
        	} catch(InterruptedException ex) { 
        	}
			repaint();
		}
	}
    
    
    
}