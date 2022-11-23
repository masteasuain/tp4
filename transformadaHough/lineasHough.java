import java.awt.*; 
import java.awt.image.BufferedImage; 
 
public class lineasHough { 

    double r; 
    double t; 
 
    public lineasHough(double t, double r) { 
        this.t = t; 
        this.r = r;
    } 
 
    public BufferedImage dibujar(BufferedImage image, int color) { 
 
        Graphics2D g = (Graphics2D) image.getGraphics();
        
        int a = (int) Math.cos(t * Math.PI / 180);
        int b = (int) Math.sin(t * Math.PI / 180);

        int x0 = (int) ((int) a * r);
        int y0 = (int) ((int) b * r);

        int x1 = (int)(x0 + 1000*(-b));
        int y1 = (int)(y0 + 1000*(a)); 
        int x2 = (int)(x0 - 1000*(-b)); 
        int y2 = (int)(y0 - 1000*(a));
        
        g.setColor(Color.RED);
        g.drawLine(x1,y1,x2,y2);
  
        return image;
        
    }     
} 