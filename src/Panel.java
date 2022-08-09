import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;  
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
public class Panel extends JPanel implements ActionListener{
	static final int SCREEN_WIDTH=600;
	static final int SCREEN_HEIGHT=600;
	static final int UNIT_SIZE=30;
	static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY=90;
	final int x[]= new int[GAME_UNITS];
	final int y[]= new int[GAME_UNITS];
	int body=5;
	int applesE;
	int appleX;
    int appleY;
	int bmbX;
	int bmbY;
	Image apple;
	Image grass;
	Image snake;
	Image snakeb;
	Image bomb;
    char directie='R';
    boolean running=false;
    Timer timer;
    Random random;
	Random random1;
	File sound1 = new File("Mario Game Over.wav");;
	AudioInputStream audio1 = AudioSystem.getAudioInputStream(sound1);
	Clip clip1 = AudioSystem.getClip();
       Panel() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		   random = new Random();
		   random1 = new Random();

		   apple = new ImageIcon("Red_apple.png").getImage();
		   grass = new ImageIcon("grass2.png").getImage();
		   snake = new ImageIcon("snakee.png").getImage();
		   snakeb = new ImageIcon("body.png").getImage();
		   bomb = new ImageIcon("bmb2.png").getImage();
		   this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		   this.setBackground(Color.black);
		   this.setFocusable(true);
		   this.addKeyListener(new myKeyAdapter());
		   startGame();
	   }
       public void startGame() {
    	   newApple();
		   newBmb();
    	   running=true;
    	   timer= new Timer(DELAY,this);
    	   timer.start();
       }
       public void paintComponent(Graphics g) {
      	 super.paintComponent(g);
      	 draw(g);
       }
       public void draw(Graphics g) {

    	   if(running) {
			   g.drawImage(apple,appleX,appleY,null);
			   g.drawImage(bomb,bmbX,bmbY,null);
      	 
      	 for(int i=0;i<body;i++) {
      		 if(i==0) { g.drawImage(snake,x[i],y[i],null);
				 }
      	 else { g.drawImage(snakeb,x[i],y[i],null);
      	 }
        }
      }
    	   else gameOver(g);
    }
       public void newApple() {
    	   appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
    	   appleY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
       }

		public void newBmb() {
			bmbX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
			bmbY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}

       public void move() {
    	   for(int i=body;i>0;i--) {
    		   x[i]=x[i-1];
    		   y[i]=y[i-1];
    	   }
    	 switch(directie) {
    	 case 'U':
    		 y[0]=y[0]-UNIT_SIZE;
    		 break;
    	 case 'D':
    		 y[0]=y[0]+UNIT_SIZE;
    		 break;
    	 case 'L':
    		 x[0]=x[0]-UNIT_SIZE;
    		 break;
    	 case 'R':
    		 x[0]=x[0]+UNIT_SIZE;
    		 break;
    	 }
    	   
       }
       public void verificMar() throws LineUnavailableException, IOException {
    	   if((x[0]==appleX) && (y[0]==appleY)) {
    		  body++;
    		  applesE++;
			  newBmb();
    		  newApple();
    	   }
       }

       public void verificColiziuni() throws LineUnavailableException, IOException {
    	   for(int i=body;i>0;i--) {
    		   if((x[0]==x[i])&&(y[0]==y[i])) {
    			   running=false;
				   clip1.open(audio1);
				   clip1.start();
    		   }
    	   }

		   if((x[0]==bmbX) && (y[0]==bmbY)) {
			   running = false;
			   clip1.open(audio1);
			   clip1.start();
		   }

    	   if(x[0]<0) {
    		   running=false;
			   clip1.open(audio1);
			   clip1.start();
    	   }
    	   if(x[0]>SCREEN_WIDTH) {
    		   running=false;
			   clip1.open(audio1);
			   clip1.start();
    	   }
    	   if(y[0]<0) {
    		   running=false;
			   clip1.open(audio1);
			   clip1.start();
    	   }
    	   if(y[0]>SCREEN_HEIGHT) {
    		   running=false;
			   clip1.open(audio1);
			   clip1.start();
           }
    	   if(!running) {
    		   timer.stop();
    	   }
       }
       public void gameOver(Graphics g) { 	 
    	   
    	   g.setColor(Color.blue);
    	   g.setFont(new Font("Standard",Font.BOLD,70));
    	   FontMetrics metrics1=getFontMetrics(g.getFont());
    	   g.drawString("Game Over",(SCREEN_WIDTH-metrics1.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
    	   
    	   g.setColor(Color.blue);
	 	   g.setFont(new Font("Standard",Font.BOLD,20));
	 	   FontMetrics metrics2=getFontMetrics(g.getFont());
	 	   g.drawString("Well done! Your score is: "+applesE,(SCREEN_WIDTH-metrics2.stringWidth("Score: "+applesE))/2,SCREEN_HEIGHT/2+30);
       }
       public void actionPerformed(ActionEvent e) {
    	   if(running) {
    		   move();
			   try {
				   verificMar();
			   } catch (LineUnavailableException ex) {
				   ex.printStackTrace();
			   } catch (IOException ex) {
				   ex.printStackTrace();
			   }
			   try {
				   verificColiziuni();
			   } catch (LineUnavailableException ex) {
				   ex.printStackTrace();
			   } catch (IOException ex) {
				   ex.printStackTrace();
			   }
		   }
    	   repaint();
       }

	public class myKeyAdapter extends KeyAdapter{
    	  public void keyPressed(KeyEvent e) {
    		  switch(e.getKeyCode()) {
    		  case KeyEvent.VK_LEFT:
    			  if(directie!='R') {
    				  directie='L';}
    			  break;
    		  case KeyEvent.VK_RIGHT:
    			  if(directie!='L') {
    				  directie='R';}
    			  break;
    		  case KeyEvent.VK_UP:
    			  if(directie!='D') {
    				  directie='U';}
    			  break;
    		  case KeyEvent.VK_DOWN:
    			  if(directie!='U') {
    				  directie='D';}
    			  break;
    			  }
    		  }
    	  }
    	   
       }