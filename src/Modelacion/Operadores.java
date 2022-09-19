/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelacion;

public class Operadores {

    private short matrizGris[][];
    private short kernel[][];

    public short[][] Rotar90(short[][] matrizGris) {
        int w = matrizGris.length;
        int h = matrizGris[0].length;
        short[][] rot1 = new short[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                rot1[i][j] = matrizGris[w - j - 1][i];
            }
        }
        return rot1;
    }

    public short[][] Rotar180(short[][] matrizGris) {
        short[][] matrizRotate1 = Rotar90(matrizGris);
        int w = matrizGris.length;
        int h = matrizGris[0].length;
        short[][] rot1 = new short[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                rot1[i][j] = matrizRotate1[w - j - 1][i];
            }
        }
        return rot1;
    }

    public short[][] Rotar270(short[][] matrizGris) {
        short[][] matrizRotate1 = Rotar90(matrizGris);
        short[][] matrizRotate2 = Rotar180(matrizRotate1);
        return matrizRotate2;
    }

    //Suma de matrices
    public short[][] operadorSuma(short matrizGris[][], short matrizGris2[][]) {
        short k = 2;
        short matrizGrisNueva[][] = new short[matrizGris.length][matrizGris[0].length];
        for (int i = 0; i < matrizGris.length; i++) {
            for (int j = 0; j < matrizGris[i].length; j++) {
                matrizGrisNueva[i][j] = (short) (Math.abs(matrizGris[i][j]) + (matrizGris2[i][j]) / 2);
            }
        }
        return matrizGrisNueva;
    }

    //restando
    public short[][] operadorResta(short matrizGris[][], short matrizGris2[][]) {
        short k = 2;
        short matrizGrisNueva[][] = new short[matrizGris.length][matrizGris[0].length];
        for (int i = 0; i < matrizGris.length; i++) {
            for (int j = 0; j < matrizGris[i].length; j++) {
                matrizGrisNueva[i][j] = (short) (Math.abs(matrizGris[i][j]) - (matrizGris2[i][j]));
            }
        }
        return matrizGrisNueva;
    }

    public short[][] operadorAnd(short matrizGris[][], short matrizGris2[][]) {
        short k = 2;
        short matrizGrisNueva[][] = new short[matrizGris.length][matrizGris[0].length];
        for (int i = 0; i < matrizGris.length; i++) {
            for (int j = 0; j < matrizGris[i].length; j++) {
                matrizGrisNueva[i][j] = (short) (Math.abs(matrizGris[i][j]) * (matrizGris2[i][j]));
            }
        }
        return matrizGrisNueva;
    }

    public short[][] operadorOr(short[][] matrizGris, short[][] matrizGris2) {
        short[][] matrizNueva = new short[matrizGris.length][matrizGris[0].length];
        boolean bandera1, bandera2;
        for (int i = 0; i < matrizGris.length; i++) {
            for (int j = 0; j < matrizGris[i].length; j++) {
                bandera1 = (matrizGris[i][j] == 255) ? true : false;
                bandera2 = (matrizGris2[i][j] == 255) ? true : false;
                if (bandera1 == false && bandera2 == false) {
                    matrizNueva[i][j] = 0;
                } else {
                    matrizNueva[i][j] = 255;
                }
            }
        }
        return matrizNueva;
    }

    public short[][] operadorXort(short[][] matrizGris, short[][] matrizGris2) {
        short[][] matrizNueva = new short[matrizGris.length][matrizGris[0].length];
        boolean bandera1, bandera2;
        for (int i = 0; i < matrizGris.length; i++) {
            for (int j = 0; j < matrizGris[i].length; j++) {
                bandera1 = (matrizGris[i][j] == 255) ? true : false;
                bandera2 = (matrizGris2[i][j] == 255) ? true : false;
                if ((bandera1 == true && bandera2 == true) || (bandera1 == false && bandera2 == false)) {
                    matrizNueva[i][j] = 0;
                } else {
                    matrizNueva[i][j] = 255;
                }
            }
        }
        return matrizNueva;
    }

    //https://tech-algorithm.com/articles/nearest-neighbor-image-scaling/
    public short[][] Interpolar(short[][] matriz) {
        int numero = matriz.length * 2;
        int newWidth = numero, newHeight = numero;
        short[][] matrizNueva = new short[numero][numero];
        int width = matriz.length;
        int height = matriz[0].length;
        double jFactor = (double) width / (double) newWidth;
        double iFactor = (double) height / (double) newHeight;

        for (int i = 0; i < newHeight; i++) {

            int I = (int) (i * iFactor);
            int p;

            for (int j = 0; j < newWidth; j++) {

                int J = (int) (j * jFactor);
                p = matriz[I][J];
                matrizNueva[i][j] = (short) p;

            }
        }

        return matrizNueva;
    }

    public short[][] calculoConvolucion(short[][] matrizGris, short[][] kernel) {
        //this.matrizDireccion = imagen.getMatrizGris();
        //this.matrizMagnitud = imagen.getMatrizGris();
        short[][] matrizNueva = new short[matrizGris.length][matrizGris[0].length];
        //short [][] matrizGris = imagen.getMatrizGris();
        //short [][] kernel = tamanoKernel][tamanoKernel];
        int tope = kernel.length / 2;
        for (int i = tope; i < matrizNueva.length - tope; i++) {
            for (int j = tope; j < matrizNueva[0].length - tope; j++) {
                matrizNueva[i][j] = convolucionar(matrizGris, kernel, i, j);
                //System.out.print(matrizGris[i][j]+" ");
            }
            //System.out.println();
        }
        return matrizNueva;
    }

    public short convolucionar(short[][] matrizGris, short[][] kernel, int fila, int columna) {
        int tope = kernel.length / 2; //variable que sirve de control para evitar que se desborde la mascara de la matriz
        short pixel = 0;
        short factor = 0;
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[0].length; j++) {
                factor += kernel[i][j];
                pixel += (short) (kernel[i][j] * matrizGris[fila - tope + i][columna - tope + j]);
            }
        }
        if (factor > 0) {
            pixel /= factor;
        }
        return pixel;
    }

}
