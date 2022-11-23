import java.awt.*; 
import java.awt.image.BufferedImage; 
 
public class circlesHough { 
 
    double a; 
    double b; 
    int r; 
 
    public circlesHough(double a, double b, int r) { 
        this.a = a; 
        this.b = b; 
        this.r = r;
    } 
 
    public BufferedImage dibujar(BufferedImage image, int color) { 
 
        Graphics2D g = (Graphics2D) image.getGraphics();
        
        int x = (int) (a - r * Math.cos(0 * Math.PI / 180));
        int y = (int) (b - r * Math.sin(90 * Math.PI / 180));

        g.setColor(Color.RED);
        g.drawOval(x,y,r * 2,r * 2);

        return image;
        
    } 
} 