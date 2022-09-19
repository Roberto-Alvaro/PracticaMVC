/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelacion;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author espar
 */
public class Operaciones_Morfologicas {

    /**
     * Este método realizará la operación de erosión en la imagen binaria img.
     * Una imagen binaria tiene dos tipos de píxeles: blanco y negro. El píxel
     * BLANCO tiene el valor ARGB (255,255,255,255) El píxel NEGRO tiene el
     * valor ARGB (255,0,0,0)
     *
     * Para la erosión generalmente consideramos el píxel en primer plano. Por
     * lo tanto, erodeForegroundPixel - true
     *
     * @param img La imagen en la que se realiza la operación de erosión
     * @param erodeForegroundPixel Si se establece en TRUE realizará la erosión
     * en píxeles BLANCOs en píxeles NEGROS.
     */
    public BufferedImage erosion(BufferedImage img, int mask[], int maskSize) {   //ESTE ES EL BUENO
        /**
         * Dimension of the image img.
         */
        BufferedImage imagenGris = img;
        int width = img.getWidth();
        int height = img.getHeight();

        //buff
        int buff[];

        //output of erosion
        int output[] = new int[width * height];

        //perform erosion
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                buff = new int[maskSize * maskSize];
                int i = 0;
                for (int ty = y - maskSize / 2, mr = 0; ty <= y + maskSize / 2; ty++, mr++) {
                    for (int tx = x - maskSize / 2, mc = 0; tx <= x + maskSize / 2; tx++, mc++) {

                        /**
                         * Sample 3x3 mask [kernel or structuring element] [0,
                         * 1, 0 1, 1, 1 0, 1, 0]
                         *
                         * Only those pixels of the image img that are under the
                         * mask element 1 are considered.
                         */
                        if (ty >= 0 && ty < height && tx >= 0 && tx < width) {
                            Color auxColor = new Color(img.getRGB(tx, ty));
                            //pixel under the mask
                            // System.out.println(mc);
                            // System.out.println(mr);
                            //System.out.println(mc+mr*maskSize);
                            if (mask[mr + mc / maskSize] != 1) {
                                continue;
                            }

                            buff[i] = auxColor.getRGB();
                            i++;

                        }
                    }
                }

                //sort buff
                java.util.Arrays.sort(buff);
                // System.out.println(i);
                //save lowest value
                output[x + y * width] = buff[(maskSize * maskSize) - i];
            }
        }

        /**
         * Save the erosion value in image img.
         */
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int v = output[x + y * width];
                imagenGris.setRGB(x, y, v);
            }
        }
        return imagenGris;
    }

    public BufferedImage dilatacion(BufferedImage img, int mask[], int maskSize) {   //ESTE ES EL BUENO
        /**
         * Dimension of the image img.
         */
        BufferedImage imagenGris = img;
        int width = img.getWidth();
        int height = img.getHeight();

        //buff
        int buff[];

        //output of erosion
        int output[] = new int[width * height];

        //perform erosion
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                buff = new int[maskSize * maskSize];
                int i = 0;
                for (int ty = y - maskSize / 2, mr = 0; ty <= y + maskSize / 2; ty++, mr++) {
                    for (int tx = x - maskSize / 2, mc = 0; tx <= x + maskSize / 2; tx++, mc++) {

                        /**
                         * Sample 3x3 mask [kernel or structuring element] [0,
                         * 1, 0 1, 1, 1 0, 1, 0]
                         *
                         * Only those pixels of the image img that are under the
                         * mask element 1 are considered.
                         */
                        if (ty >= 0 && ty < height && tx >= 0 && tx < width) {
                            Color auxColor = new Color(img.getRGB(tx, ty));
                            //pixel under the mask
                            // System.out.println(mc);
                            // System.out.println(mr);
                            //System.out.println(mc+mr*maskSize);
                            if (mask[mr + mc / maskSize] != 1) {
                                continue;
                            }

                            buff[i] = auxColor.getRGB();
                            i++;

                        }
                    }
                }

                //sort buff
                java.util.Arrays.sort(buff);
                // System.out.println(i);
                //save lowest value
                output[x + y * width] = buff[(maskSize * maskSize) - i];
            }
        }

        /**
         * Save the erosion value in image img.
         */
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int v = output[x + y * width];
                imagenGris.setRGB(x, y, v);
            }
        }
        return imagenGris;
    }

    /*Este es con matrices
    Diferencia que se haga con BufferedImage
    es que se guarda en la imagen actual la imagen nueva que se trata, 
    otra diferencia es que con el BufferedImage se puede aplicar en imagenes a 
    escala de grises, tambien otra diferencia es que al realizar una de las operaciones
    morfologicas en una imagen con ruido Gaussiano no te la restaura, existe cierto error con ello,
    cosa que con el metodo que trabaja con la matriz binaria si restaura la imagen*/
    public short[][] erosionImagenBinaria(boolean erodeForegroundPixel, short[][] imagenBinaria) {
        /**
         * Dimension of the image img.
         */

        short[][] matrizErosion = new short[imagenBinaria.length][imagenBinaria[0].length];
        /**
         * This will hold the erosion result which will be copied to image img.
         */
        int output[] = new int[imagenBinaria.length * imagenBinaria[0].length];

        /**
         * If erosion is to be performed on BLACK pixels then targetValue = 0
         * else targetValue = 255; //for WHITE pixels
         */
        int targetValue = (erodeForegroundPixel == true) ? 0 : 255;

        /**
         * If the target pixel value is WHITE (255) then the reverse pixel value
         * will be BLACK (0) and vice-versa.
         */
        int reverseValue = (targetValue == 255) ? 0 : 255;

        //perform erosion
        for (int y = 0; y < imagenBinaria.length; y++) {
            for (int x = 0; x < imagenBinaria[y].length; x++) {
                //For BLACK pixel RGB all are set to 0 and for WHITE pixel all are set to 255.
                if (imagenBinaria[y][x] == targetValue) {
                    /**
                     * We are using a 3x3 kernel [1, 1, 1 1, 1, 1 1, 1, 1]
                     */
                    boolean flag = false;   //this will be set if a pixel of reverse value is found in the mask
                    for (int ty = y - 1; ty <= y + 1 && flag == false; ty++) {
                        for (int tx = x - 1; tx <= x + 1 && flag == false; tx++) {
                            if (ty >= 0 && ty < imagenBinaria[0].length && tx >= 0 && tx < imagenBinaria.length) {
                                //origin of the mask is on the image pixels
                                if (imagenBinaria[ty][tx] != targetValue) {
                                    flag = true;
                                    output[x + y * imagenBinaria.length] = reverseValue;
                                }
                            }
                        }
                    }
                    if (flag == false) {
                        //all pixels inside the mask [i.e., kernel] were of targetValue
                        output[x + y * imagenBinaria.length] = targetValue;
                    }
                } else {
                    output[x + y * imagenBinaria.length] = reverseValue;
                }
            }
        }

        /**
         * Save the erosion value in image img.
         */
        for (int y = 0; y < imagenBinaria.length; y++) {
            for (int x = 0; x < imagenBinaria[0].length; x++) {
                short s = (short) output[x + y * imagenBinaria.length];
                matrizErosion[y][x] = s;
            }
        }
        return matrizErosion;
    }
}
