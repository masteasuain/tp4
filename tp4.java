/*
 * TP4 - Inteligencia Artificial
 * 
 * Asteasuain, Martin
 */

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*; 
import java.awt.image.BufferedImage;
import java.util.Vector;

public class tp4{

    public static void main(String[] args){
        try{
            BufferedImage imagen = ImageIO.read(new File("imagen_de_prueba.png"));
            BufferedImage imagen_procesada;

            // TODO: Aplicar filtros de detección de bordes, como Canny y procesos de limpieza de ruido.
            // La imagen utilizada en el prototipo es intencionalmente "limpia"

            Boolean exit = false;
            System.out.println("Detección de lineas y circunferencias de Hough");
            while (!exit){                            
                
                System.out.println();
                System.out.println("1. Detección de lineas.");
                System.out.println("2. Detección de circulos con radio conocido.");
                System.out.println("3. Detección de circulos con radio desconocido.");
                System.out.println("4. Salir.");
                System.out.println();
                System.out.print("Seleccione una opción: ");

                
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));            
                String opcion = br.readLine();
                int numOpcion = Integer.parseInt(opcion);
                
                switch(numOpcion){
                    
                    // Detección de lineas
                    case 1:{
                        imagen_procesada = detectarLineas(imagen);
                        ImageIO.write(imagen_procesada, "png", new File("resultados_deteccion_lineas.png"));
                    }
                    break;

                    // Detección de circulos con radio conocido
                    case 2:{
                        System.out.print("Ingrese el radio: ");
                        String valorRadio = br.readLine();
                        int radio = Integer.parseInt(valorRadio);
                        imagen_procesada = detectarCirculosRadioConocido(imagen, radio);
                        ImageIO.write(imagen_procesada, "png", new File("resultados_deteccion_circulos_radio_conocido.png"));
                    }
                    break;

                    // Detección de circulos con radio desconocido
                    case 3:{
                        imagen_procesada = detectarCirculos(imagen);
                        ImageIO.write(imagen_procesada, "png", new File("resultados_deteccion_circulos_radio_desconocido.png"));
                    }

                    case 4:{
                        exit = true;
                    }
                    break;

                }

            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }       
        
    }

    public static void mostrarOpciones(){

    }


    public static BufferedImage detectarLineas(BufferedImage imagen){
    
        try{
            System.out.println("Procesando imagen...");
            // Inicializar el acumulador de la transformada de Hough
            transformadaHoughLineas h = new transformadaHoughLineas(imagen.getWidth(), imagen.getHeight()); 
    
            // Almacena los puntos de la imagen dentro del acumulador 
            h.prepararAcumulador(imagen); 
    
            // Busco los picos dentro del acumulador
            Vector<lineasHough> lineas = h.buscarPicos(140); 

            // Dibujo las figuras encontradas
            for (int j = 0; j < lineas.size(); j++) { 
                lineasHough l = lineas.elementAt(j); 
                imagen = l.dibujar(imagen, Color.RED.getRGB()); 
            } 
            

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        System.out.println("Proceso finalizado.");
        return imagen;       
    }

    public static BufferedImage detectarCirculos(BufferedImage imagen){
    
        try{
            System.out.println("Procesando imagen...");
            // Inicializar el acumulador de la transformada de Hough
            transformadaHoughCirculos h = new transformadaHoughCirculos(imagen.getWidth(), imagen.getHeight()); 
    
            // Almacena los puntos de la imagen dentro del acumulador 
            h.prepararAcumulador(imagen); 
    
            // Busco los picos dentro del acumulador
            Vector<circlesHough> circles = h.buscarPicos(250); 

            // Dibujo las figuras encontradas
            for (int j = 0; j < circles.size(); j++) { 
                circlesHough c = circles.elementAt(j); 
                imagen = c.dibujar(imagen, Color.RED.getRGB()); 
            } 


        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        System.out.println("Proceso finalizado.");            
        return imagen;

    }

    public static BufferedImage detectarCirculosRadioConocido(BufferedImage imagen, int radio){
    
        try{
            System.out.println("Procesando imagen...");
            // Inicializar el acumulador de la transformada de Hough
            transformadaHoughCirculosRadioConocido h = new transformadaHoughCirculosRadioConocido(imagen.getWidth(), imagen.getHeight(), radio); 
    
            // Almacena los puntos de la imagen dentro del acumulador 
            h.prepararAcumulador(imagen); 
    
            // Busco los picos dentro del acumulador
            Vector<circlesHough> circles = h.buscarPicos(250); 

            // Dibujo las figuras encontradas
            for (int j = 0; j < circles.size(); j++) { 
                circlesHough c = circles.elementAt(j); 
                imagen = c.dibujar(imagen, Color.RED.getRGB()); 
            } 
            
 
 
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        System.out.println("Proceso finalizado.");
        return imagen;
    }
   
}