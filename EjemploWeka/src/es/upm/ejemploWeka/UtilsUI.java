package es.upm.ejemploWeka;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;


public class UtilsUI {

		public static File abrirFichero(String ruta)
		{
	        Scanner entrada = null;
	        File f = new File(ruta);

	        try 
	        {
	            JFileChooser fileChooser = new JFileChooser();
	            fileChooser.showOpenDialog(fileChooser);   
	            
	            String ruta2 = fileChooser.getSelectedFile().getAbsolutePath();
	            f = new File(ruta2);                                                  
	            entrada = new Scanner(f);
	            while (entrada.hasNext()) 
	            {
	                System.out.println(entrada.nextLine());
	            }
	        } catch (FileNotFoundException e) 
	          {
	            System.out.println(e.getMessage());
	          } 
	          catch (NullPointerException e) 
	          {
	            System.out.println("No se ha seleccionado ning�n fichero");
	          } 
	          catch (Exception e) 
	          {
	            System.out.println(e.getMessage());
	          } 
	          finally 
	          {
	            if (entrada != null) 
	            {
	       //     	return entrada;
	       //         entrada.close();
	            	System.out.println("Fichero enviado");
	            }
	        }
	       return f; 
		}
	        
}