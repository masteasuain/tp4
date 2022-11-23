/*
 * TP4 - Inteligencia Artificial
 * 
 * Transformada de Hough para detección de lineas rectas
 * 
 * Asteasuain, Martin
 */

import java.awt.image.BufferedImage;
import java.util.Vector; 

public class transformadaHoughLineas{

    int maxTheta = 180; 
    int width, height; 
    int radio;
    int maxHeight;
 
    int[][] acumulador; 
 
    int numPuntos; 
 
    public transformadaHoughLineas(int width, int height) { 
 
        this.width = width; 
        this.height = height; 
 
        initialise(); 
    } 
 
    /** 
     * Inicializa el acumulador de Hough
     */ 
    private void initialise() { 
         
        int houghHeight = (int) (Math.sqrt(2) * Math.max(height, width)) / 2; 
        maxHeight = 2 * houghHeight; 

        // Creo el acumulador 
        acumulador = new int[maxTheta][maxHeight];  
        
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

                        int r = (int) (x * Math.cos(t * Math.PI / 180) + y * Math.sin(t * Math.PI / 180));
                 
                        // Descartamos los puntos por fuera de la dimension de la imagen
                        if (r < 0 || r >= maxHeight)  continue;
                            
                        // Votacion por el punto 
                        acumulador[t][r]++; 

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
    public Vector<lineasHough> buscarPicos(int umbral) { 
 
        Vector<lineasHough> lineas = new Vector<lineasHough>(20); 
 
        // Chequeo que existan puntos
        if (numPuntos == 0) return lineas; 
 
        for (int t = 0; t < maxTheta; t++) { 
            
            for (int r = 1; r < maxHeight; r++) { 
 
                // Solo considerar los puntos por arriba del umbral
                if (acumulador[t][r] > umbral) {                     
 
                    // agregamos el punto a las posibles lineas
                    lineas.add(new lineasHough(t, r)); 
 
                } 
            } 
        } 
 
        return lineas; 
    } 
      
}