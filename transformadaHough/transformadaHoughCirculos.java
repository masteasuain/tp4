/*
 * TP4 - Inteligencia Artificial
 * 
 * Transformada de Hough para detección de circulos con radio desconocido
 * 
 * Asteasuain, Martin
 */

import java.awt.image.BufferedImage;
import java.util.Vector; 

public class transformadaHoughCirculos{

    int maxTheta = 360; 
    int width, height; 
    int radio;
    int minRadio;
    int maxRadio;
 
    int[][][] acumulador; 
 
    int numPuntos; 
 
    public transformadaHoughCirculos(int width, int height) { 
 
        this.width = width; 
        this.height = height; 
 
        initialise(); 
    } 
 
    /** 
     * Inicializa el acumulador de Hough
     */ 
    private void initialise() { 
         
        // Asumo como radio máximo de mis posibles circulos, la mitad del ancho de la imagen 
        maxRadio = width / 2;

        // Necesitamos un radio minimo para que comience a considerarse un circulo, sino detectaria simples puntos como circulos
        minRadio = 20;

        // Inicializo el acumulador
        acumulador = new int[width][height][maxRadio]; 
        
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
                        for(int r = minRadio; r < maxRadio; r++){
                            int a = (int) ((x) - r * Math.cos(t * Math.PI / 180));                       
                            int b = (int) ((y) - r * Math.sin(t * Math.PI / 180));                        
                 
                            // Descartamos los puntos por fuera de la dimension de la imagen
                            if (a < 0 || a >= height)  continue;
                            if (b < 0 || b >= width) continue;
                            
                            // Votacion por el punto 
                            acumulador[a][b][r]++; 
                        }
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
                for (int r = 1; r < maxRadio; r++) { 
 
                    // Solo considerar los puntos por arriba del umbral
                    if (acumulador[a][b][r] > umbral) {                     
    
                        // agregamos el punto a los posibles circulos
                        circulos.add(new circlesHough(a, b, r)); 
    
                    } 
                }
            } 
        } 
 
        return circulos; 
    } 
      
}