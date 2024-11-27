/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Eventos;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Estudiante_MCA
 */
public class RegistroActividades {
    private StringBuilder registro; 
    private String nombreArchivo;
    private long idUsuario;
    private String direccionIp;
    
    public RegistroActividades(String direccionIp) {
        this.direccionIp = direccionIp;
        this.registro = new StringBuilder();
        String carpetaLogs = "logs";
        
        File directorio = new File(carpetaLogs);
        if (!directorio.exists()) {
            directorio.mkdirs(); 
        }
        
        String fechaHoraActual = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        this.nombreArchivo = "registro_" + direccionIp + "_" + fechaHoraActual + ".txt";
    }

    public void registrar(String actividad) {
        registro.append(actividad).append("\n");
    }
    
    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void guardar() {
        
        File archivo = new File("logs", nombreArchivo);
        try (FileWriter fileWriter = new FileWriter(archivo)) {
            
            fileWriter.write(registro.toString());
            System.out.println("Registro de actividades guardado en: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al guardar el registro de actividades: " + e.getMessage());
        }
            
        guardarEnBucket();
        
    }
    
    private void guardarEnBucket() {
        try {
            
            Storage storage = StorageOptions.getDefaultInstance().getService();
                  
            
            String rutaEnBucket = idUsuario + "/logs/" + nombreArchivo;
            
            if (registro.toString().isEmpty()) {
                System.err.println("El contenido del archivo está vacío. No se guardará en el bucket.");
                return;
            }
          
            BlobInfo blobInfo = BlobInfo.newBuilder("spring-boot-monitor", rutaEnBucket).build();
            
            storage.create(
                blobInfo,
                new ByteArrayInputStream(registro.toString().getBytes(StandardCharsets.UTF_8))
            );

            System.out.println("Archivo guardado en el bucket en: " + rutaEnBucket);
        } catch (Exception e) {
            System.err.println("Error al guardar el archivo en el bucket: " + e.getMessage());
        }
    }
    
 
}
