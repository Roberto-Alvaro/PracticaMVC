package Modelacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Imagen implements Cloneable {

    private File archivoImagen;
    private String nombre;
    public String formato;
    private int filas;
    private int columnas;
    private int nivelIntensidad;
    private short matrizGris[][];
    private short matrizR[][];
    private short matrizG[][];
    private short matrizB[][];
    private short matrizCMY[][];
    private short matrizC[][];
    private short matrizM[][];
    private short matrizY[][];
    private BufferedImage bufferImagen;
    private final float Alfa = 0.299f;
    private final float Beta = 0.587f;
    private final float Gama = 0.114f;

    FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("JPG & GIF & BMP & PNG & TIF", "jpg", "gif", "bmp", "png", "tif");
    BufferedImage imagenActual;
    private Imagen objImagen;

    public Imagen(File rutaImagen) throws IOException {
        this.archivoImagen = rutaImagen;
        this.bufferImagen = ImageIO.read(archivoImagen);
        this.columnas = bufferImagen.getHeight();
        this.filas = bufferImagen.getHeight();
        this.nombre = rutaImagen.getName();
        this.nivelIntensidad = 255;
        inicializaMatrices(bufferImagen);
    }

    public String getNombre() {
        return nombre;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }
// se generaron los set de filas y columnas

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public void inicializaMatrices(BufferedImage bufferImagen) {
        this.matrizR = new short[filas][columnas];
        this.matrizB = new short[filas][columnas];
        this.matrizG = new short[filas][columnas];
        this.matrizGris = new short[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Color auxColor = new Color(bufferImagen.getRGB(j, i));
                this.matrizR[i][j] = (short) auxColor.getRed();
                this.matrizG[i][j] = (short) auxColor.getGreen();
                this.matrizB[i][j] = (short) auxColor.getBlue();
                this.matrizGris[i][j] = (short) ((this.Alfa * auxColor.getRed()) + (this.Beta * auxColor.getGreen()) + (this.Gama * auxColor.getBlue()));
                //Ayuda a transormar la imagen a escala de grises 
            }
        }
    }

    public File abrirImagen() {
        BufferedImage bmp = null;
        File imagenSeleccionada = null;
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccione una imagen");
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("JPG & GIF & BMP & PNG & TIF", "jpg", "gif", "bmp", "png", "tif");
        selector.setFileFilter(filtroImagen);
        int flag = selector.showOpenDialog(null);
        if (flag == JFileChooser.APPROVE_OPTION) {
            try {
                imagenSeleccionada = selector.getSelectedFile();
            } catch (Exception e) {
            }

        }
        return imagenSeleccionada;
    }

    public BufferedImage escalaGrises() {
        int mediaPixel, colorSRGB;
        Color colorAux;
        for (int i = 0; i < imagenActual.getWidth(); i++) {
            for (int j = 0; j < imagenActual.getHeight(); j++) {
                colorAux = new Color(this.imagenActual.getRGB(i, j));
                mediaPixel = (int) ((colorAux.getRed() + colorAux.getGreen() + colorAux.getBlue()) / 3);
                colorSRGB = (mediaPixel << 16) | (mediaPixel << 8) | mediaPixel;
                imagenActual.setRGB(i, j, colorSRGB);
            }
        }
        return imagenActual;
    }

    public File getArchivoImagen() {
        return archivoImagen;
    }

    public void setArchivoImagen(File archivoImagen) {
        this.archivoImagen = archivoImagen;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public int getNivelIntensidad() {
        return nivelIntensidad;
    }

    public void setNivelIntensidad(int nivelIntensidad) {
        this.nivelIntensidad = nivelIntensidad;
    }

    public short[][] getMatrizGris() {
        return matrizGris;
    }

    public void setMatrizGris(short[][] matrizGris) {
        this.matrizGris = matrizGris;
    }

    public short[][] getMatrizR() {
        return matrizR;
    }

    public void setMatrizR(short[][] matrizR) {
        this.matrizR = matrizR;
    }

    public short[][] getMatrizG() {
        return matrizG;
    }

    public void setMatrizG(short[][] matrizG) {
        this.matrizG = matrizG;
    }

    public short[][] getMatrizB() {
        return matrizB;
    }

    public void setMatrizB(short[][] matrizB) {
        this.matrizB = matrizB;
    }

    public BufferedImage getBufferImagen() {
        return bufferImagen;
    }

    public void setBufferImagen(BufferedImage bufferImagen) {
        this.bufferImagen = bufferImagen;
    }

    public Imagen() {

    }

    public void ImprimirMatriz(short[][] matriz) {
        System.out.print("Matriz impresa");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(matriz[i][j]);
            }
            System.out.println("");
        }
    }

    public BufferedImage convierteMatrizEnBufferedImage(short matriz[][]) {
        BufferedImage imagen = new BufferedImage(matriz.length, matriz.length, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < matriz.length; y++) {
            for (int x = 0; x < matriz.length; x++) {
                int pixelSRGB = matriz[y][x] << 16 | matriz[y][x] << 8 | matriz[y][x];
                imagen.setRGB(x, y, pixelSRGB);
            }
        }
        return imagen;
    }

    @Override
    public Imagen clone() throws CloneNotSupportedException {
        return (Imagen) super.clone();
    }

    public BufferedImage convertRGBAToIndexed(BufferedImage src) {
        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = dest.getGraphics();
        g.setColor(new Color(231, 20, 189));
        g.fillRect(0, 0, dest.getWidth(), dest.getHeight());
        dest = makeTransparent(dest, 0, 0);
        dest.createGraphics().drawImage(src, 0, 0, null);
        return dest;
    }

    public static BufferedImage makeTransparent(BufferedImage image, int x, int y) {
        ColorModel cm = image.getColorModel();
        if (!(cm instanceof IndexColorModel)) {
            return image;
        }
        IndexColorModel icm = (IndexColorModel) cm;
        WritableRaster raster = image.getRaster();
        int pixel = raster.getSample(x, y, 0);
        int size = icm.getMapSize();
        byte[] reds = new byte[size];
        byte[] greens = new byte[size];
        byte[] blues = new byte[size];
        icm.getReds(reds);
        icm.getGreens(greens);
        icm.getBlues(blues);
        IndexColorModel icm2 = new IndexColorModel(8, size, reds, greens, blues, pixel);
        return new BufferedImage(icm2, raster, image.isAlphaPremultiplied(), null);
    }

    public BufferedImage convertRGBToCMY(short[][] matrizR, short[][] matrizG, short[][] matrizB) {
        BufferedImage img = new BufferedImage(matrizR.length, matrizR[0].length, BufferedImage.TYPE_INT_RGB);
        this.matrizC = new short[filas][columnas];
        this.matrizM = new short[filas][columnas];
        this.matrizY = new short[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                this.matrizC[i][j] = (short) (255 - matrizR[i][j]);
                this.matrizM[i][j] = (short) (255 - matrizG[i][j]);
                this.matrizY[i][j] = (short) (255 - matrizB[i][j]);
                short SRGB = (short) ((matrizC[i][j] << 16) | (matrizM[i][j] << 8) | matrizY[i][j]);
                img.setRGB(j, i, SRGB);
            }
        }
        return img;
    }

    public short[][] getconvertRGBToIlumniacion(short[][] matrizR, short[][] matrizG, short[][] matrizB) {
        short[][] matrizI = new short[matrizR.length][matrizR[0].length];
        for (int i = 0; i < bufferImagen.getWidth(); i++) {
            for (int j = 0; j < bufferImagen.getHeight(); j++) {
                double sum = matrizR[i][j] + matrizG[i][j] + matrizB[i][j];
                double total = sum / 3;
                matrizI[i][j] = (short) total;
            }
        }
        return matrizI;
    }

    public short[][] getconvertRGBToSaturacion(short[][] matrizR, short[][] matrizG, short[][] matrizB) {
        short[] A = new short[3];
        short[][] matrizS = new short[matrizR.length][matrizR[0].length];
        double max = 255;
        double min = 1;
        for (int i = 0; i < bufferImagen.getWidth(); i++) {
            for (int j = 0; j < bufferImagen.getHeight(); j++) {
                A[0] = matrizR[i][j];
                A[1] = matrizG[i][j];
                A[2] = matrizB[i][j];
                Arrays.sort(A);
                double min1 = A[0];
                double sumRGB = matrizR[i][j] + matrizG[i][j] + matrizB[i][j];
                double operacion = (min1 / sumRGB) * 3;
                double resultado = (operacion * max) / min;
                matrizS[i][j] = (short) (255 - resultado);
            }
        }
        return matrizS;
    }

    public short[][] getconvertRGBToMatiz(short[][] matrizR, short[][] matrizG, short[][] matrizB) {
        /*double h = 0;
        short[][] matrizM = new short[matrizR.length][matrizR[0].length];
        for (int i = 0; i < bufferImagen.getWidth(); i++) {
            for (int j = 0; j < bufferImagen.getHeight(); j++) {
                h = ((1 / Math.cos((0.5 * (2 * matrizR[i][j] - matrizG[i][j] - matrizB[i][j])) / (Math.sqrt(((matrizR[i][j] - matrizG[i][j]) * (matrizR[i][j] - matrizG[i][j]) + (matrizR[i][j] - matrizB[i][j]) * (matrizG[i][j] - matrizB[i][j])))))));
                double h1 = (double) (h * (180 / Math.PI));
                double suma = (matrizR[i][j]  - matrizG[i][j]) + (matrizR[i][j] - matrizB[i][j]);
                double opr = suma * 1/2;
                double exp = (matrizR[i][j] - matrizG[i][j]) * (matrizR[i][j] - matrizG[i][j]);
                double multi = (matrizR[i][j] - matrizB[i][j]) * (matrizG[i][j] - matrizB[i][j]);
                double suma2 = exp + multi;
                double raiz = Math.sqrt(suma2);
                double division = opr / raiz;
                double cos = Math.acos(raiz);
                double conv = Math.toDegrees(cos);
                
                matrizM[i][j] = (short) h1;
            }
        }
        return matrizM;*/
        short[][] matrizM = new short[this.filas][this.columnas];
        float r = 0, g = 0, b = 0;
        float suma_RGB = 0, h = 0, total = 0, division = 0, arriba = 0, abajo = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                suma_RGB = matrizR[i][j] + matrizG[i][j] + matrizB[i][j];
                if (suma_RGB == 0) {
                    r = 0;
                    g = 0;
                    b = 0;
                } else {
                    r = matrizR[i][j] / suma_RGB;
                    g = matrizG[i][j] / suma_RGB;
                    b = matrizB[i][j] / suma_RGB;
                }
                arriba = (float) (0.5 * ((r - g) + (r - b)));
                abajo = (float) Math.sqrt((((r - g) * (r - g)) + ((r - b) * (g - b))));
                if (abajo == 0) {
                    division = 0;
                    //System.out.println("No hace nada");
                } else {
                    division = (arriba / abajo);
                }
                total = (float) (Math.acos(division));

                h = (float) Math.toDegrees(total);

                if (b > g) {
                    h = 360 - h;
                    if ((short) h == 90) {
                        h = 0;
                    } else {
                        h = h;
                    }
                } else {
                    h = h;
                    if ((short) h == 90) {
                        h = 0;
                    } else {
                        h = h;
                    }
                }

                matrizM[i][j] = (short) h;
            }
        }
        return matrizM;
    }
}
