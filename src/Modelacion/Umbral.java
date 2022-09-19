package Modelacion;

public class Umbral {

    private short matrizGris[][];

    public Umbral() {
    }
    
    public short[][] OperadorIdentidad(short matriz[][]) {
        short matrizGrisNueva[][] = new short[matriz.length][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matrizGrisNueva[i][j] = (short) (matrizGris[i][j]);
            }
        }
        return matrizGrisNueva;
    }

    public short[][] calculaInverso(short[][] matrizGris) {
        short[][] matrizNueva = new short[matrizGris.length][matrizGris[0].length];
        for (int i = 0; i < matrizGris.length; i++) {
            for (int j = 0; j < matrizGris[i].length; j++) {
                matrizNueva[i][j] = (short) (255 - matrizGris[i][j]);
            }
        }
        return matrizNueva;
    }

    public short[][] operadorUmbral(short matrizGris[][], int umbral) {
        short[][] matrizGrisNueva = new short[matrizGris.length][matrizGris[0].length];
        for (int i = 0; i < matrizGris.length; i++) {
            for (int j = 0; j < matrizGris[i].length; j++) {
                if (matrizGris[i][j] < umbral) {
                    matrizGrisNueva[i][j] = 0;
                } else {
                    matrizGrisNueva[i][j] = 255;
                }
            }
        }
        return matrizGrisNueva;
    }

    public short[][] operadorUmbralInverso(short matrizGris[][], int umbral) {
        short[][] matrizGrisNueva = new short[matrizGris.length][matrizGris[0].length];
        for (int i = 0; i < matrizGris.length; i++) {
            for (int j = 0; j < matrizGris[i].length; j++) {
                if (matrizGris[i][j] < umbral) {
                    matrizGrisNueva[i][j] = 255;
                } else {
                    matrizGrisNueva[i][j] = 0;
                }
            }
        }
        return matrizGrisNueva;
    }

    public short[][] UmbralBinario(short matrizGris[][], int u1, int u2) {
        short[][] matrizGrisNueva = new short[matrizGris.length][matrizGris[0].length];
        for (int i = 0; i < matrizGris.length; i++) {
            for (int j = 0; j < matrizGris[i].length; j++) {
                if (matrizGris[i][j] < u1 || matrizGris[i][j] > u2) {
                    matrizGrisNueva[i][j] = 0;
                } else {
                    matrizGrisNueva[i][j] = 255;
                }
            }
        }
        return matrizGrisNueva;
    }

    public short[][] UmbralBinarioInverso(short matrizGris[][], int u1, int u2) {
        short[][] matrizGrisNueva = new short[matrizGris.length][matrizGris[0].length];
        for (int i = 0; i < matrizGris.length; i++) {
            for (int j = 0; j < matrizGris[i].length; j++) {
                if (matrizGris[i][j] < u1 || matrizGris[i][j] > u2) {
                    matrizGrisNueva[i][j] = 255;
                } else {
                    matrizGrisNueva[i][j] = 0;
                }
            }
        }
        return matrizGrisNueva;
    }

    public short[][] UmbralEnGris(short matrizGris[][], int u1, int u2) {
        short[][] matrizGrisNueva = new short[matrizGris.length][matrizGris[0].length];
        for (int i = 0; i < matrizGris.length; i++) {
            for (int j = 0; j < matrizGris[i].length; j++) {
                if (matrizGris[i][j] < u1 || matrizGris[i][j] > u2) {
                    matrizGrisNueva[i][j] = 255;
                } else {
                    matrizGrisNueva[i][j] = matrizGris[i][j];
                }
            }
        }
        return matrizGrisNueva;
    }

    public short[][] UmbralEnGrisInverso(short matrizGris[][], int u1, int u2) {
        short[][] matrizGrisNueva = new short[matrizGris.length][matrizGris[0].length];
        for (int i = 0; i < matrizGris.length; i++) {
            for (int j = 0; j < matrizGris[i].length; j++) {
                if (matrizGris[i][j] < u1 || matrizGris[i][j] > u2) {
                    matrizGrisNueva[i][j] = matrizGris[i][j];
                } else {
                    matrizGrisNueva[i][j] = 255;
                }
            }
        }
        return matrizGrisNueva;
    }

    public short[][] OperadordeExtension(short matrizGris[][], int u1, int u2) {
        short[][] matrizGrisNueva = new short[matrizGris.length][matrizGris[0].length];
        for (int i = 0; i < matrizGris.length; i++) {
            for (int j = 0; j < matrizGris[i].length; j++) {
                int pixelGris = matrizGris[i][j];
                if (matrizGris[i][j] < u1 || matrizGris[i][j] > u2) {
                    matrizGrisNueva[i][j] = 255;
                } else {
                    matrizGrisNueva[i][j] = (short) (255 * (pixelGris - u1) / (u2 - u1));
                }
            }
        }
        return matrizGrisNueva;
    }

    public short[][] reduccionDeGrises(short matrizGris[][]) {
        short n = 10;
        short u1 = 25, u2 = 50, u3 = 75, u4 = 100, u5 = 125, u6 = 150, u7 = 175, u8 = 200, u9 = 225;
        short matrizGrisNueva[][] = new short[matrizGris.length][matrizGris[0].length];
        for (int i = 0; i < matrizGris.length; i++) {
            for (int j = 0; j < matrizGris[i].length; j++) {
                //int pixelGris = matrizGris[i][j];
                if (matrizGris[i][j] < u1) {
                    matrizGrisNueva[i][j] = 0;
                }
                if (matrizGris[i][j] > u1 && matrizGris[i][j] < u2) {
                    matrizGrisNueva[i][j] = 37;
                }
                if (matrizGris[i][j] > u2 && matrizGris[i][j] < u3) {
                    matrizGrisNueva[i][j] = 62;
                }
                if (matrizGris[i][j] > u3 && matrizGris[i][j] < u4) {
                    matrizGrisNueva[i][j] = 87;
                }
                if (matrizGris[i][j] > u4 && matrizGris[i][j] < u5) {
                    matrizGrisNueva[i][j] = 112;
                }
                if (matrizGris[i][j] > u5 && matrizGris[i][j] < u6) {
                    matrizGrisNueva[i][j] = 137;
                }
                if (matrizGris[i][j] > u6 && matrizGris[i][j] < u7) {
                    matrizGrisNueva[i][j] = 162;
                }
                if (matrizGris[i][j] > u7 && matrizGris[i][j] < u8) {
                    matrizGrisNueva[i][j] = 187;
                }
                if (matrizGris[i][j] > u8 && matrizGris[i][j] < u9) {
                    matrizGrisNueva[i][j] = 212;
                } else {
                    matrizGrisNueva[i][j] = 255;
                }
            }
        }
        return matrizGrisNueva;
    }
}
