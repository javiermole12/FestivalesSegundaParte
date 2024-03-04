package festivales.io;

import festivales.modelo.AgendaFestivales;
import festivales.modelo.Estilo;
import festivales.modelo.Festival;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * La clase contiene méodos estáticos que permiten
 * cargar la agenda de festivales leyendo los datos desde
 * un fichero
 */
public class FestivalesIO {

    
    public static void cargarFestivales(AgendaFestivales agenda) {
        Scanner sc = null;
        try {
            sc = new Scanner(FestivalesIO.class.
                    getResourceAsStream("/festivales.csv"));
            while (sc.hasNextLine()) {
                String lineaFestival = sc.nextLine();
                Festival festival = parsearLinea(lineaFestival);
                agenda.addFestival(festival);
                
            }
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
        
    }

    /**
     * se parsea la línea extrayendo sus datos y creando y
     * devolviendo un objeto festivales.modelo.Festival
     * @param lineaFestival los datos de un festival
     * @return el festival creado
     */
    public static Festival parsearLinea(String lineaFestival) {
        lineaFestival = lineaFestival.trim();
        String[] partes = lineaFestival.split(":");
        LocalDate fechaInicio = null;

        String nombre = obtenerNombre(partes[0]);
        String lugar = partes[1].trim().toUpperCase();
        fechaInicio = LocalDate.parse(partes[2].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        int duracion = Integer.parseInt(partes[3].trim());
        HashSet<Estilo> estilos = obtenerEstilos(Arrays.copyOfRange(partes, 4, partes.length));

        return new Festival(nombre, lugar, fechaInicio, duracion, estilos);
    }

    private static String obtenerNombre(String nombre) {
        String[] palabras = nombre.split(" ");
        StringBuilder nombreCapitalizado = new StringBuilder();
        for (String palabra : palabras) {
            nombreCapitalizado.append(Character.toUpperCase(palabra.charAt(0)))
                    .append(palabra.substring(1).toLowerCase()).append(" ");
        }
        return nombreCapitalizado.toString().trim();
    }

    private static HashSet<Estilo> obtenerEstilos(String[] estilosString) {
        HashSet<Estilo> estilos = new HashSet<>();
        for (String estiloString : estilosString) {
            estilos.add(Estilo.valueOf(estiloString.trim().toUpperCase()));
        }
        return estilos;
    }
}
