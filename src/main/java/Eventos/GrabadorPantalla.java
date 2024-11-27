/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Eventos;
        
        
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GrabadorPantalla {

    private FFmpegFrameRecorder recorder;
    private boolean grabando;
    private Java2DFrameConverter frameConverter;
    private Robot robot;
    private Rectangle pantalla;
    private int fpsOriginal;
    private long idUsuario;
    private String nombreVideo;
    
    private static final String BUCKET_NAME = "spring-boot-monitor";

    public GrabadorPantalla(String nombreVideo, int fpsOriginal, int fpsSalida, long idUsuario) throws Exception {
        
        this.nombreVideo = nombreVideo;
        this.idUsuario = idUsuario;
        
        pantalla = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        frameConverter = new Java2DFrameConverter();
        robot = new Robot();
        this.fpsOriginal = fpsOriginal;
        
        File directorio = new File("records");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        
        recorder = new FFmpegFrameRecorder("records/" +  nombreVideo, pantalla.width, pantalla.height);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setFormat("mp4");
        recorder.setFrameRate(fpsSalida); 
        recorder.setVideoBitrate(5000000);
        
        if (pantalla.width <= 0 || pantalla.height <= 0) {
            throw new Exception("Error: las dimensiones de la pantalla son inválidas.");
        }
    }

    public void iniciarGrabacion() throws Exception {
        recorder.start();
        grabando = true;
        comenzarCaptura();       
    }
    
     private void comenzarCaptura() {
        new Thread(() -> {
            try {
                while (grabando) {
                    BufferedImage captura = new BufferedImage(pantalla.width, pantalla.height, BufferedImage.TYPE_3BYTE_BGR);
                    Graphics2D g = captura.createGraphics();
                    g.drawImage(robot.createScreenCapture(pantalla), 0, 0, null);
                    g.dispose();

                    Frame frame = frameConverter.convert(captura);
                    recorder.record(frame); 
                    Thread.sleep(1000 / fpsOriginal); 
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void detenerGrabacion() throws Exception {
        grabando = false;
        recorder.stop();
        recorder.release();
        System.out.println("Video guardado correctamente.");
        guardarEnBucket();
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    private void guardarEnBucket() {
        try {
            // Inicializar cliente de Google Cloud Storage
            Storage storage = StorageOptions.getDefaultInstance().getService();
            
            String rutaArchivoLocal = "records/" + nombreVideo;

            String nombreArchivoEnBucket = idUsuario + "/videos/" + nombreVideo;

            // Leer el archivo local
            byte[] fileContent = Files.readAllBytes(Paths.get(rutaArchivoLocal));

            // Crear BlobInfo para el archivo
            BlobInfo blobInfo = BlobInfo.newBuilder(BUCKET_NAME, nombreArchivoEnBucket).build();

            // Subir archivo al bucket
            storage.create(blobInfo, new ByteArrayInputStream(fileContent));

            System.out.println("Video subido al bucket en: " + nombreArchivoEnBucket);
        } catch (Exception e) {
            System.err.println("Error al subir el video al bucket: " + e.getMessage());
            e.printStackTrace();
        }
    }
  
    
}