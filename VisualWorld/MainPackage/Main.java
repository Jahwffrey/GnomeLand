package MainPackage;

import javax.swing.*; 
import java.awt.*; 
 
import java.awt.event.*; 

public class Main extends JFrame{
	
    private JPanel jContentPane = null; 
    private Screen panel = null;
    
    private Screen getPanel(){
        if (panel == null) { 
            panel = new Screen();
        } 
        return panel; 
    }
    
    public Main() { 
    	//super(); 

        initialize(); 

        this.addKeyListener(new KeyAdapter() { 
            public void keyPressed(KeyEvent evt) { //PRESSED
                    formKeyPressed(evt); 
            } 
            public void keyReleased(KeyEvent evt) { //RELEASED
                    formKeyReleased(evt); 
            } 

        });

        this.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent mk){
                panel.mousePressed(mk);
            }
            public void mouseReleased(MouseEvent mk){
                panel.mouseReleased(mk);
            }
            /*public void mouseMoved(MouseEvent mk){
                panel.mouseMoved(mk);
            }**/
        });
    }

    private void formKeyPressed(KeyEvent evt){
    	panel.keyPressed(evt);
    }
    
    private void formKeyReleased(KeyEvent evt){
    	panel.keyReleased(evt);
    }

    private void initialize(){
    	this.setResizable(false);
    	this.setBounds(200, 200, Screen.screenLength, Screen.screenHeight);
    	this.setMinimumSize(new Dimension(Screen.screenLength, Screen.screenHeight));
    	this.setMaximumSize(new Dimension(Screen.screenLength, Screen.screenHeight));
    	this.setContentPane(getJContentPane());
    	this.setTitle("An abundance of tiny humanoids.");
    }
    
    private JPanel getJContentPane(){
        if (jContentPane == null) { 
            jContentPane = new JPanel(); 
            jContentPane.setLayout(new BorderLayout()); 
            jContentPane.add(getPanel(), BorderLayout.CENTER); 
    } 
    return jContentPane; 
    }
    
    public static void main(String[] args) { 
    	
        SwingUtilities.invokeLater(new Runnable() { 
                public void run() { 
                        Main thisClass = new Main(); 
                        thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
                        thisClass.setVisible(true); 
                } 
        }); 

    } 
}//WELL DONE!

