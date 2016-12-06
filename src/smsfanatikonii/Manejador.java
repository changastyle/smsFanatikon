package smsfanatikonii;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Manejador
{
    ventana v;
   
   public Manejador()
   {
        v = new ventana(this);
        v.getjTextArea1().setEditable(false);
        v.setVisible(true);
   }
   public String ejecutarFormateo()
   {
       String salida = "";
       try
       {
           //Libro de Excel:
            Workbook libro = Workbook.getWorkbook(new File("C:/smsfanatikon/1.xls"));
            Sheet hoja = libro.getSheet(0);

            int filasMax = hoja.getRows();
            int colMax = hoja.getColumns();
            String ultimoHorario = "";
            String fecha;
            
            System.out.println("filas = " + filasMax);
            System.out.println("columnas = " + colMax );


            int filaActual = 0 ;
            while(filaActual < filasMax)
            {
                //System.out.println("" + hoja.getCell(6,filaActual).getContents());
                boolean condicion1 = (hoja.getCell(6,filaActual).getContents() != "" && hoja.getCell(12,filaActual).getContents() != "");
                boolean condicion2 = (hoja.getCell(6,filaActual).getContents() != "HOME TEAM");
                if( ! hoja.getCell(6,filaActual).getContents().trim().equalsIgnoreCase("HOME TEAM")   && ! hoja.getCell(6,filaActual).getContents().trim().equalsIgnoreCase(""))
                {
                    String horario = hoja.getCell(2,filaActual).getContents();
                    if(horario.trim().equalsIgnoreCase(""))
                    {
                        horario = ultimoHorario;
                    }
                    else
                    {
                        ultimoHorario = horario;
                    }
                    String numeroPartido = hoja.getCell(3,filaActual).getContents();
                    String local = formateoNombreEquipos(hoja.getCell(6,filaActual).getContents());
                    String visitante = formateoNombreEquipos(hoja.getCell(12,filaActual).getContents());
                    String L = hoja.getCell(8,filaActual).getContents();
                    String E = hoja.getCell(9,filaActual).getContents();
                    String V = hoja.getCell(10,filaActual).getContents();
                    String LoE = hoja.getCell(14,filaActual).getContents();
                    String LoV = hoja.getCell(15,filaActual).getContents();
                    String EoV = hoja.getCell(16,filaActual).getContents();
                    String menosGoles = hoja.getCell(17,filaActual).getContents();
                    if (menosGoles.trim().equalsIgnoreCase("")){ menosGoles = " --" ;}
                    String masGoles = hoja.getCell(18,filaActual).getContents();
                    if (masGoles.trim().equalsIgnoreCase("")){ masGoles = " --" ;}
                    
                    
                    
                    salida += (horario +" - "+ numeroPartido +" - "+  local  +" VS " + visitante + ": \n");
                    salida += ("L: " + L + " | ");
                    salida += ("E: " + E + " | ");
                    salida += ("V: " + V + "\n");
                    salida += ("LoE: " + LoE + " | ");
                    salida += ("LoV: " + LoV + " | ");
                    salida += ("EoV: " + EoV + "\n");
                    salida += ("Menos Goles: " + menosGoles  + " | ");
                    salida += ("Mas Goles: " + masGoles  + "\n");
                    salida += ("------------------------------------\n");
                }
                else
                {
                    if(hoja.getCell(0,filaActual).getContents() != "")
                    {
                        fecha = hoja.getCell(0,filaActual).getContents();
                        fecha = formatoFecha(fecha);
                        salida +=  "\n" + fecha + "\n";
                        //System.out.println("fecha: " + fecha);
                    }
                    
                }
                filaActual++;
            }    
       }
       catch (Exception e)
       {
           JOptionPane.showMessageDialog(null, "Error de conversion de archivo, contactarse con Nicolas Grossi (294) 154 - 530851");
       }
       return salida;
   }
   public String formateoNombreEquipos(String equipo)
   {
       String salida = "";
       
       salida = equipo;
       /*String mayus = "" + equipo.charAt(0);
       mayus = mayus.toUpperCase();
       salida = mayus + equipo.substring(1, equipo.length()).toLowerCase();
       */
       return salida;
   }
    public String formatoFecha(String raw)
    {
        //String raw = "SATURDAY 03/12/2016";
        boolean pasoEspacio = false;
        String acumuladorPreEspacio = "";
        String acumuladorPostEspacio = "";
        
        
        for(int i = 0 ; i < raw.length() ; i++)
        {
            char caracter = raw.charAt(i);
            if(Character.isWhitespace(caracter))
            {
                //System.out.println("es espacio");
                pasoEspacio = true;
            }
            else
            {
                //System.out.println(caracter);
            }
            
            if(pasoEspacio)
            {
                acumuladorPostEspacio += caracter;
            }
            else
            {
                acumuladorPreEspacio += caracter;
            }
            
        }
              //System.out.println("ac post: "+ acumuladorPostEspacio);
              
              String diaDeLaSemana = "";
              
              if(acumuladorPreEspacio.startsWith("MON"))
              {
                  diaDeLaSemana = "Lunes";
              }
              if(acumuladorPreEspacio.startsWith("TUE"))
              {
                  diaDeLaSemana = "Martes";
              }
              if(acumuladorPreEspacio.startsWith("WED"))
              {
                  diaDeLaSemana = "Miercoles";
              }
              if(acumuladorPreEspacio.startsWith("THU"))
              {
                  diaDeLaSemana = "Jueves";
              }
              if(acumuladorPreEspacio.startsWith("FRI"))
              {
                  diaDeLaSemana = "Viernes";
              }
              if(acumuladorPreEspacio.startsWith("SAT"))
              {
                  diaDeLaSemana = "Sabado";
              }
              if(acumuladorPreEspacio.startsWith("SUN"))
              {
                  diaDeLaSemana = "Domingo";
              }
              //System.out.println("ac pre: "+ diaDeLaSemana);  
              
              return diaDeLaSemana + " " + acumuladorPostEspacio;
    }
}
