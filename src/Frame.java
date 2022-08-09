import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import java.io.IOException;

public class Frame extends JFrame{
      Frame() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    	  this.add(new Panel());
    	  this.setTitle("Snake");
    	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	  this.setResizable(false);
    	  this.pack();
    	  this.setVisible(true);
    	  this.setLocationRelativeTo(null);
      }
     
}
