/*
 * TP4 - Inteligencia Artificial
 * 
 * Transformada de Hough para detección de circulos con radio conocido
 * 
 * Asteasuain, Martin
 */

import java.awt.image.BufferedImage;
import java.util.Vector; 

public class transformadaHoughCirculosRadioConocido{

    int maxTheta = 360; 
    int width, height; 
    int radio;
 
    int[][] acumulador; 
 
    int numPuntos; 
 
    public transformadaHoughCirculosRadioConocido(int width, int height, int radio) { 
 
        this.width = width; 
        this.height = height; 
        this.radio = radio;
 
        initialise(); 
    } 
 
    /** 
     * Inicializa el acumulador de Hough
     */ 
    private void initialise() { 
         
        // Inicializo el acumulador
        acumulador = new int[width][height]; 
        
        numPuntos = 0; 
 
    } 
 
    /** 
     * Agrega los puntos de la imagen en el acumulador de Hough.
     * Se asume que la imagen que se procesa paso previamente por un algoritmo de deteccion de bordes y se encuentra en blanco y negro (no escala de grises).
     */ 
    public void prepararAcumulador(BufferedImage image) { 
 
        for (int x = 0; x < image.getWidth(); x++) { 
            for (int y = 0; y < image.getHeight(); y++) { 
                // Detecto los pixeles que no sean negros
                if ((image.getRGB(x, y) & 0x000000ff) != 0) { 
                    for (int t = 0; t < maxTheta; t++) { 
                        int a = (int) ((x) - radio * Math.cos(t * Math.PI / 180));                       
                        int b = (int) ((y) - radio * Math.sin(t * Math.PI / 180));                        
                 
                        // Descartamos los puntos por fuera de la dimension de la imagen
                        if (a < 0 || a >= height)  continue;
                        if (b < 0 || b >= width) continue;
                            
                        // Votacion por el punto 
                        acumulador[a][b]++; 
                    } 
             
                    numPuntos++; 
                } 
            } 
        } 
    } 
 
    
 
    /** 
     * Busca los picos dentro del acumulador de Hough y los puntos candidatos a formar parte de la figura que se busca
     * 
     * @param umbral Umbral de detección, esto es a partir de que valor un punto es considerado un pico 
     */ 
    public Vector<circlesHough> buscarPicos(int umbral) { 
 
        Vector<circlesHough> circulos = new Vector<circlesHough>(20); 
 
        // Chequeo que existan puntos
        if (numPuntos == 0) return circulos; 
 
        for (int a = 0; a < width; a++) { 
            
            for (int b = 1; b < height; b++) { 
 
                // Solo considerar los puntos por arriba del umbral
                if (acumulador[a][b] > umbral) {                     
    
                    // agregamos el punto a los posibles circulos
                    circulos.add(new circlesHough(a, b, radio)); 
    
                } 
            } 
        } 
 
        return circulos; 
    } 
      
}