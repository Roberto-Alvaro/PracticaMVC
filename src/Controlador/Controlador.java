package Controlador;

import Modelacion.DibujarHistograma;
import Modelacion.FFTException;
import Modelacion.Filtros_Restauracion;
import Modelacion.Histograma;
import java.awt.event.ActionListener;
import Modelacion.Imagen;
import Modelacion.Operadores;
import Modelacion.Umbral;
import Modelacion.ImageFFT;
import Modelacion.Operaciones_Morfologicas;
import Vista.Frame;
import Vista.Frame_HSI;
import Vista.Frame_Histograma;
import Vista.Panel_Covolucion;
import Vista.Umbral1;
import Vista.Umbral2;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Controlador implements ActionListener {

    private Frame view;
    private Imagen modelo1;
    private Umbral modelo2;
    private Operadores operador1;
    private Filtros_Restauracion FR;
    private Umbral1 u1;
    private Umbral2 u2;
    private ImageFFT fft;
    private Frame_HSI hsi1;
    private Frame_Histograma FH1;
    private Operaciones_Morfologicas OPM11;

    Frame ventanaPrincipal = new Frame();
    Frame_Histograma FH = new Frame_Histograma();
    Frame_HSI HSI = new Frame_HSI();
    Imagen img = new Imagen();
    Imagen img1 = new Imagen();
    Umbral model = new Umbral();
    Operadores opr = new Operadores();
    Filtros_Restauracion FDR = new Filtros_Restauracion();
    Panel_Covolucion panel = new Panel_Covolucion();
    Umbral1 panelUmbral1 = new Umbral1();
    Umbral2 panelUmbral2 = new Umbral2();
    Operaciones_Morfologicas OPM = new Operaciones_Morfologicas();

    public Controlador(Frame view, Imagen modelo1) {

        this.ventanaPrincipal = ventanaPrincipal;
        this.view = view;
        this.modelo1 = modelo1;

        //Botones Principales
        this.view.EscalaGris.addActionListener(this);
        this.view.Buscar.addActionListener(this);
        this.view.Regresar.addActionListener(this);
        this.view.CMY.addActionListener(this);

        //Histograma
        this.view.Histograma.addActionListener(this);
        this.FH = FH;

        //RGB A HSI
        this.view.HSI_Botton.addActionListener(this);
        this.HSI = HSI;

        //Menu Bar
        this.view.Umbralizacion.addActionListener(this);
        this.view.Operaciones.addActionListener(this);
        this.view.Filtros_Espaciales.addActionListener(this);
        this.view.Filtros_Restauracion.addActionListener(this);

        //Umbrales
        this.view.jMenuItem1_Inverso.addActionListener(this);
        this.view.jMenuItem2_Operador_Umbral.addActionListener(this);
        this.view.jMenuItem3_Operador_Umbral_Inverso.addActionListener(this);
        this.view.jMenuItem4_Binario.addActionListener(this);
        this.view.jMenuItem5_Binario_Inverso.addActionListener(this);
        this.view.jMenuItem6_Umbral_Gris.addActionListener(this);
        this.view.jMenuItem7_Umbral_Gris_Inverso.addActionListener(this);
        this.view.jMenuItem8_Operador_Extencion.addActionListener(this);
        this.view.jMenuItem9_Reduccion_Niveles_Gris.addActionListener(this);
        this.panelUmbral1.jButton1_Aplicar_Umbral.addActionListener(this);
        this.panelUmbral2.jButton1_Aplicar_Umbrales.addActionListener(this);

        //Operaciones Basicas
        this.view.jMenuItem10_Suma.addActionListener(this);
        this.view.jMenuItem11_Resta.addActionListener(this);
        this.view.jMenuItem12_And.addActionListener(this);
        this.view.jMenuItem13_Or.addActionListener(this);
        this.view.jMenuItem14_Xort.addActionListener(this);
        this.view.jMenuItem15_Rotacion90.addActionListener(this);
        this.view.jMenuItem16_Rotacion180.addActionListener(this);
        this.view.jMenuItem17_Rotacion270.addActionListener(this);

        //Filtros espaciales
        this.view.jMenuItem1_Interpolacion.addActionListener(this);
        this.view.jMenuItem2_Convolucion.addActionListener(this);
        this.panel.Aplicar_Covolucion.addActionListener(this);

        //Filtros de restauracion
        this.view.jMenuItem1_Media_Aritmetica.addActionListener(this);
        this.view.jMenuItem2_Media_Geometrica.addActionListener(this);
        this.view.jMenuItem3_Mediana.addActionListener(this);
        this.view.jMenuItem4_Maximo.addActionListener(this);
        this.view.jMenuItem5_Minimo.addActionListener(this);
        this.view.jMenuItem6_Punto_Medio.addActionListener(this);
        this.view.jMenuItem7_Media_Seccion.addActionListener(this);
        this.view.jMenuItem1_Media_Adaptativo.addActionListener(this);

        //Filtros de frecuencia
        this.view.jMenuItem1_Espectro.addActionListener(this);
        this.view.jMenuItem1_idealLowPassFilter.addActionListener(this);
        this.view.jMenuItem1_idealHighPassFilter.addActionListener(this);
        this.view.jMenuItem1_idealBandPassFilter.addActionListener(this);
        this.view.jMenuItem1_idealBandStopFilter.addActionListener(this);
        this.view.jMenuItem1_butterworthLowPassFilter.addActionListener(this);
        this.view.jMenuItem1_butterworthHighPassFilter.addActionListener(this);
        this.view.jMenuItem1_butterworthBandPassFilter.addActionListener(this);
        this.view.jMenuItem1_butterworthBandStopFilter.addActionListener(this);

        //Operaciones Morfologicas
        this.view.Morfologicas_Erosion.addActionListener(this);
        this.view.Morfologicas_Dilatacion.addActionListener(this);
        this.view.Morfologicas_Apertura.addActionListener(this);
        this.view.Morfologicas_Cierre.addActionListener(this);
    }

    public void Iniciar() {
        view.setTitle("MVC Imagen");
        view.setLocation(0, 0);
    }

    //@Override
    public void actionPerformed(ActionEvent e) {

        //Aplicar Gris
        if (e.getSource() == view.EscalaGris) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(img.getMatrizGris());
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }
        //Buscar Imagen
        if (e.getSource() == view.Buscar) {
            try {
                img = new Imagen(img.abrirImagen());
            } catch (IOException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                img1 = img.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            ImageIcon imagen1 = new ImageIcon(img.getBufferImagen());
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
            view.jLabel5_Name.setText(img.getNombre());
            view.jLabel6_Altura.setText("" + img.getFilas());
            view.jLabel7_Ancho.setText("" + img.getColumnas());
        }
        if (e.getSource() == view.Regresar) {
            //Regresar a normal
            ImageIcon imagen1 = new ImageIcon(img.getBufferImagen());
            Icon icon1 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(icon1);
        }
        //RGB A CMY
        if (e.getSource() == view.CMY) {
            BufferedImage imagenactual2 = img.convertRGBToCMY(img.getMatrizR(), img.getMatrizG(), img.getMatrizB());
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);

            //RGB A ILUMINICIDAD
        }
        if (e.getSource() == view.HSI_Botton) {
            HSI.setVisible(true);
            HSI.setDefaultCloseOperation(FH.DISPOSE_ON_CLOSE);

            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(img.getconvertRGBToIlumniacion(img.getMatrizR(), img.getMatrizG(), img.getMatrizB()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(HSI.Iluminacion.getWidth(), HSI.Iluminacion.getHeight(), Image.SCALE_DEFAULT));
            HSI.Iluminacion.setIcon(imagen2);

            BufferedImage imagenactual3 = img.convierteMatrizEnBufferedImage(img.getconvertRGBToSaturacion(img.getMatrizR(), img.getMatrizG(), img.getMatrizB()));
            ImageIcon imagen4 = new ImageIcon(imagenactual3);
            Icon imagen3 = new ImageIcon(imagen4.getImage().getScaledInstance(HSI.Saturacion.getWidth(), HSI.Saturacion.getHeight(), Image.SCALE_DEFAULT));
            HSI.Saturacion.setIcon(imagen3);

            BufferedImage imagenactual5 = img.convierteMatrizEnBufferedImage(img.getconvertRGBToMatiz(img.getMatrizR(), img.getMatrizG(), img.getMatrizB()));
            ImageIcon imagen6 = new ImageIcon(imagenactual5);
            Icon imagen7 = new ImageIcon(imagen6.getImage().getScaledInstance(HSI.Saturacion.getWidth(), HSI.Saturacion.getHeight(), Image.SCALE_DEFAULT));
            HSI.Matiz.setIcon(imagen7);
        }

        //Histograma
        if (e.getSource() == view.Histograma) {
            FH.setVisible(true);
            FH.setDefaultCloseOperation(FH.DISPOSE_ON_CLOSE);
            //try {
            //LEEMOS LA IMAGEN
            //Image imagen = ImageIO.read(jTextField1.getText()));
            //establecemos la imagen como imagen en el jLabel
            //jLabel1_Imagen.setIcon(new ImageIcon(imagen));
            //CREAMOS EL HISTOGRAMAS
            Histograma ObjHistograma = new Histograma();
            //establecemos la imagen como imagen en el jLabel
            int[][] histograma = ObjHistograma.histograma((BufferedImage) img.getBufferImagen());
            //DIBUJAMOS LOS HISTOGRAMAS
            DibujarHistograma ObjDibujaHisto = new DibujarHistograma();
            for (int i = 0; i < 5; i++) {
                //extraemos un canal del histograma 
                int[] histogramaCanal = new int[256];
                System.arraycopy(histograma[i], 0, histogramaCanal, 0, histograma[i].length);
                //Dibujamos en el panel
                /*ObjDibujaHisto.crearHistograma(histogramaCanal, view.jPanel_alfa, Color.red);
                ObjDibujaHisto.crearHistograma(histogramaCanal, view.jPanel_alfa, Color.green);
                ObjDibujaHisto.crearHistograma(histogramaCanal, view.jPanel_alfa, Color.blue);
                ObjDibujaHisto.crearHistograma(histogramaCanal, view.jPanel_alfa, Color.black);
                ObjDibujaHisto.crearHistograma(histogramaCanal, view.jPanel_alfa, Color.gray);
                 */
                switch (i) {
                    case 0:
                        ObjDibujaHisto.crearHistograma(histogramaCanal, FH.jPanel_rojo, Color.red);
                        break;
                    case 1:
                        ObjDibujaHisto.crearHistograma(histogramaCanal, FH.jPanel_verde, Color.green);
                        break;
                    case 2:
                        ObjDibujaHisto.crearHistograma(histogramaCanal, FH.jPanel_azul, Color.blue);
                        break;
                    case 3:
                        ObjDibujaHisto.crearHistograma(histogramaCanal, FH.jPanel_alfa, Color.black);
                        break;
                    case 4:
                        ObjDibujaHisto.crearHistograma(histogramaCanal, FH.jPanel_Gris, Color.gray);
                        break;
                }
            }
        }

        //Aplicacion de los umbrales
        if (e.getSource()
                == view.jMenuItem1_Inverso) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(model.calculaInverso(img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem2_Operador_Umbral) {
            panelUmbral1.setSize(view.Contenedor.getWidth(), view.Contenedor.getHeight());
            panelUmbral1.setLocation(0, 0);
            view.Contenedor.removeAll();
            view.Contenedor.add(panelUmbral1, BorderLayout.CENTER);
            view.Contenedor.revalidate();
            view.Contenedor.repaint();
            /*JOptionPane.showMessageDialog(null, "Ingrese los o el Umbral que desea");
            u1 = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingresa el umbral"));
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(model.operadorUmbral(img.getMatrizGris(), u1));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.jLabel1.getWidth(), view.jLabel1.getHeight(), Image.SCALE_DEFAULT));
            view.jLabel1.setIcon(imagen2);*/
        }

        if (e.getSource()
                == panelUmbral1.jButton1_Aplicar_Umbral) {
            int u1 = Integer.parseInt(panelUmbral1.jTextField1_Umbral.getText());
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(model.operadorUmbral(img.getMatrizGris(), u1));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem3_Operador_Umbral_Inverso) {
            panelUmbral1.setSize(view.Contenedor.getWidth(), view.Contenedor.getHeight());
            panelUmbral1.setLocation(0, 0);
            view.Contenedor.removeAll();
            view.Contenedor.add(panelUmbral1, BorderLayout.CENTER);
            view.Contenedor.revalidate();
            view.Contenedor.repaint();
            /*JOptionPane.showMessageDialog(null, "Ingrese los o el Umbral que desea");
            int u1 = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingresa el umbral"));
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(model.operadorUmbralInverso(img.getMatrizGris(), u1));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.jLabel1.getWidth(), view.jLabel1.getHeight(), Image.SCALE_DEFAULT));
            view.jLabel1.setIcon(imagen2);*/
        }

        if (e.getSource()
                == panelUmbral1.jButton1_Aplicar_Umbral) {
            int u1 = Integer.parseInt(panelUmbral1.jTextField1_Umbral.getText());
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(model.operadorUmbralInverso(img.getMatrizGris(), u1));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem4_Binario) {
            panelUmbral2.setSize(view.Contenedor.getWidth(), view.Contenedor.getHeight());
            panelUmbral2.setLocation(0, 0);
            view.Contenedor.removeAll();
            view.Contenedor.add(panelUmbral2, BorderLayout.CENTER);
            view.Contenedor.revalidate();
            view.Contenedor.repaint();
            /*JOptionPane.showMessageDialog(null, "Ingrese los o el Umbral que desea");
            u1 = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingresa el umbral"));
            u2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingresa el umbral 2"));
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(model.UmbralBinario(img.getMatrizGris(), u1, u2));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.jLabel1.getWidth(), view.jLabel1.getHeight(), Image.SCALE_DEFAULT));
            view.jLabel1.setIcon(imagen2);*/
        }

        if (e.getSource()
                == panelUmbral2.jButton1_Aplicar_Umbrales) {
            int u1 = Integer.parseInt(panelUmbral2.jTextField1_Umbral1.getText());
            int u2 = Integer.parseInt(panelUmbral2.jTextField2_Umbral2.getText());
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(model.UmbralBinario(img.getMatrizGris(), u1, u2));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem5_Binario_Inverso) {
            panelUmbral2.setSize(view.Contenedor.getWidth(), view.Contenedor.getHeight());
            panelUmbral2.setLocation(0, 0);
            view.Contenedor.removeAll();
            view.Contenedor.add(panelUmbral2, BorderLayout.CENTER);
            view.Contenedor.revalidate();
            view.Contenedor.repaint();
            /*JOptionPane.showMessageDialog(null, "Ingrese los o el Umbral que desea");
            u1 = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingresa el umbral"));
            u2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingresa el umbral 2"));
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(model.UmbralBinarioInverso(img.getMatrizGris(), u1, u2));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.jLabel1.getWidth(), view.jLabel1.getHeight(), Image.SCALE_DEFAULT));
            view.jLabel1.setIcon(imagen2);*/
        }

        if (e.getSource()
                == panelUmbral2.jButton1_Aplicar_Umbrales) {
            int u1 = Integer.parseInt(panelUmbral2.jTextField1_Umbral1.getText());
            int u2 = Integer.parseInt(panelUmbral2.jTextField2_Umbral2.getText());
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(model.UmbralBinarioInverso(img.getMatrizGris(), u1, u2));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem6_Umbral_Gris) {
            panelUmbral2.setSize(view.Contenedor.getWidth(), view.Contenedor.getHeight());
            panelUmbral2.setLocation(0, 0);
            view.Contenedor.removeAll();
            view.Contenedor.add(panelUmbral2, BorderLayout.CENTER);
            view.Contenedor.revalidate();
            view.Contenedor.repaint();
            /*JOptionPane.showMessageDialog(null, "Ingrese los o el Umbral que desea");
            u1 = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingresa el umbral"));
            u2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingresa el umbral 2"));
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(model.UmbralEnGris(img.getMatrizGris(), u1, u2));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.jLabel1.getWidth(), view.jLabel1.getHeight(), Image.SCALE_DEFAULT));
            view.jLabel1.setIcon(imagen2);*/
        }

        if (e.getSource()
                == panelUmbral2.jButton1_Aplicar_Umbrales) {
            int u1 = Integer.parseInt(panelUmbral2.jTextField1_Umbral1.getText());
            int u2 = Integer.parseInt(panelUmbral2.jTextField2_Umbral2.getText());
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(model.UmbralEnGris(img.getMatrizGris(), u1, u2));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem7_Umbral_Gris_Inverso) {
            panelUmbral2.setSize(view.Contenedor.getWidth(), view.Contenedor.getHeight());
            panelUmbral2.setLocation(0, 0);
            view.Contenedor.removeAll();
            view.Contenedor.add(panelUmbral2, BorderLayout.CENTER);
            view.Contenedor.revalidate();
            view.Contenedor.repaint();
            /*JOptionPane.showMessageDialog(null, "Ingrese los o el Umbral que desea");
            u1 = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingresa el umbral"));
            u2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingresa el umbral 2"));
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(model.UmbralEnGrisInverso(img.getMatrizGris(), u1, u2));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.jLabel1.getWidth(), view.jLabel1.getHeight(), Image.SCALE_DEFAULT));
            view.jLabel1.setIcon(imagen2);*/
        }

        if (e.getSource()
                == panelUmbral2.jButton1_Aplicar_Umbrales) {
            int u1 = Integer.parseInt(panelUmbral2.jTextField1_Umbral1.getText());
            int u2 = Integer.parseInt(panelUmbral2.jTextField2_Umbral2.getText());
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(model.UmbralEnGrisInverso(img.getMatrizGris(), u1, u2));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem8_Operador_Extencion) {
            panelUmbral2.setSize(view.Contenedor.getWidth(), view.Contenedor.getHeight());
            panelUmbral2.setLocation(0, 0);
            view.Contenedor.removeAll();
            view.Contenedor.add(panelUmbral2, BorderLayout.CENTER);
            view.Contenedor.revalidate();
            view.Contenedor.repaint();
            /*JOptionPane.showMessageDialog(null, "Ingrese los o el Umbral que desea");
            u1 = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingresa el umbral"));
            u2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingresa el umbral 2"));
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(model.OperadordeExtension(img.getMatrizGris(), u1, u2));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.jLabel1.getWidth(), view.jLabel1.getHeight(), Image.SCALE_DEFAULT));
            view.jLabel1.setIcon(imagen2);*/
        }

        if (e.getSource()
                == panelUmbral2.jButton1_Aplicar_Umbrales) {
            int u1 = Integer.parseInt(panelUmbral2.jTextField1_Umbral1.getText());
            int u2 = Integer.parseInt(panelUmbral2.jTextField2_Umbral2.getText());
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(model.OperadordeExtension(img.getMatrizGris(), u1, u2));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem9_Reduccion_Niveles_Gris) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(model.reduccionDeGrises(img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }
        // }
        //Operaciones
        //if (e.getSource() == view.OperacionAplicar) {

        if (e.getSource()
                == view.jMenuItem10_Suma) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(opr.operadorSuma(img.getMatrizGris(), img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem11_Resta) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(opr.operadorResta(img.getMatrizGris(), img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem12_And) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(opr.operadorAnd(img.getMatrizGris(), img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem13_Or) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(opr.operadorOr(img.getMatrizGris(), img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem14_Xort) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(opr.operadorXort(img.getMatrizGris(), img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem15_Rotacion90) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(opr.Rotar90(img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem16_Rotacion180) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(opr.Rotar180(img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem17_Rotacion270) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(opr.Rotar270(img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem1_Interpolacion) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(opr.Interpolar(img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage());
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem2_Convolucion) {
            panel.setSize(view.Contenedor.getWidth(), view.Contenedor.getHeight());
            panel.setLocation(0, 0);
            view.Contenedor.removeAll();
            view.Contenedor.add(panel, BorderLayout.CENTER);
            view.Contenedor.revalidate();
            view.Contenedor.repaint();

            //short [][] Kernel = {{0,1,0},{1,-4,1},{0,1,0}};
            /*short nMatriz = Short.parseShort(JOptionPane.showInputDialog("Digite el tamaño de la matriz siempre y cuando sea impar: "));
            if (nMatriz % 2 == 0) {
                System.out.println("Vuelve a intentarlo");
            } else {
                short[][] Kernel = new short[nMatriz][nMatriz];
                for (int i = 0; i < nMatriz; i++) {
                    for (int j = 0; j < nMatriz; j++) {
                        Kernel[i][j] = Short.parseShort(JOptionPane.showInputDialog("Digite el numero del indice : " + "[" + i + "]" + "[" + j + "]"));
                    }
                }
                //short[][] Kernel = {{0, -1, 0}, {0, 1, 0}, {0, 0, 0}};
                BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(opr.calculoConvolucion(img.getMatrizGris(), Kernel));
                ImageIcon imagen1 = new ImageIcon(imagenactual2);
                Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.jLabel1.getWidth(), view.jLabel1.getHeight(), Image.SCALE_DEFAULT));
                view.jLabel1.setIcon(imagen2);
            }*/
        }

        if (e.getSource()
                == panel.Aplicar_Covolucion) {
            short v1, v2, v3, v4, v5, v6, v7, v8, v9;
            v1 = Short.parseShort(panel.Valor1.getText());
            v2 = Short.parseShort(panel.Valor2.getText());
            v3 = Short.parseShort(panel.Valor3.getText());
            v4 = Short.parseShort(panel.Valor4.getText());
            v5 = Short.parseShort(panel.Valor5.getText());
            v6 = Short.parseShort(panel.Valor6.getText());
            v7 = Short.parseShort(panel.Valor7.getText());
            v8 = Short.parseShort(panel.Valor8.getText());
            v9 = Short.parseShort(panel.Valor9.getText());
            short[][] Kernel = {{v1, v2, v3}, {v4, v5, v6}, {v7, v8, v9}};
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(opr.calculoConvolucion(img.getMatrizGris(), Kernel));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem1_Media_Aritmetica) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(FDR.filtroMediaAritmetica(img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem2_Media_Geometrica) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(FDR.filtroMediaGeometrica(img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem3_Mediana) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(FDR.filtroMediana(img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem4_Maximo) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(FDR.filtroMaximo(img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem5_Minimo) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(FDR.filtroMinimo(img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem6_Punto_Medio) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(FDR.filtroPuntoMedio(img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem7_Media_Seccion) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(FDR.mediaSeccion(img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource()
                == view.jMenuItem1_Media_Adaptativo) {
            BufferedImage imagenactual2 = img.convierteMatrizEnBufferedImage(FDR.mediaAdaptivo(img.getMatrizGris()));
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        //Frecuencia Filtros
        if (e.getSource()
                == view.jMenuItem1_Espectro) {
            ImageFFT fft = null;
            try {
                fft = new ImageFFT(img.convertRGBAToIndexed(img.getBufferImagen()));
            } catch (FFTException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            fft.transform();
            BufferedImage imagenactual2 = null;
            try {
                imagenactual2 = fft.getSpectrum();
            } catch (FFTException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            ImageIcon imagen1 = new ImageIcon(imagenactual2);
            Icon imagen2 = new ImageIcon(imagen1.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(imagen2);
        }

        if (e.getSource() == view.jMenuItem1_idealLowPassFilter) {
            try {
                double radio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese Radio"));
                BufferedImage imagen = img.convertRGBAToIndexed(img.getBufferImagen());
                ImageFFT fft = new ImageFFT(imagen);
                fft.transform();
                fft.idealLowPassFilter(radio);
                fft.transform();
                //BufferedImage nuevaImagen = null;
                //nuevaImagen = fft.toImage(nuevaImagen);
                ImageIcon img12 = new ImageIcon(fft.toImage(null));
                //Icon icono2 = new ImageIcon(img12.getImage());
                Icon icono2 = new ImageIcon(img12.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
                view.Contenedor_Imagen.setIcon(icono2);
            } catch (FFTException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource() == view.jMenuItem1_idealHighPassFilter) {
            try {
                double radio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese Radio"));
                BufferedImage imagen = img.convertRGBAToIndexed(img.getBufferImagen());
                ImageFFT fft = new ImageFFT(imagen);
                fft.transform();
                fft.idealHighPassFilter(radio);
                fft.transform();
                //BufferedImage nuevaImagen = null;
                //nuevaImagen = fft.toImage(nuevaImagen);
                ImageIcon img12 = new ImageIcon(fft.toImage(null));
                Icon iconoI2 = new ImageIcon(img12.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
                view.Contenedor_Imagen.setIcon(iconoI2);
            } catch (FFTException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == view.jMenuItem1_idealBandPassFilter) {
            try {
                double radio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese Radio"));
                double delta = Double.parseDouble(JOptionPane.showInputDialog("Ingrese Delta"));
                BufferedImage imagen = img.convertRGBAToIndexed(img.getBufferImagen());
                ImageFFT fft = new ImageFFT(imagen);
                fft.transform();
                fft.idealBandPassFilter(radio, delta);//entre 0 y 1.0
                fft.transform();
                //BufferedImage nuevaImagen = null;
                //nuevaImagen = fft.toImage(nuevaImagen);
                ImageIcon img12 = new ImageIcon(fft.toImage(null));
                Icon iconoI2 = new ImageIcon(img12.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
                view.Contenedor_Imagen.setIcon(iconoI2);
            } catch (FFTException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == view.jMenuItem1_idealBandStopFilter) {
            try {
                double radio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese Radio"));
                double delta = Double.parseDouble(JOptionPane.showInputDialog("Ingrese Delta"));
                BufferedImage imagen = img.convertRGBAToIndexed(img.getBufferImagen());
                ImageFFT fft = new ImageFFT(imagen);
                fft.transform();
                fft.idealBandStopFilter(radio, delta);//entre 0 y 1.0
                fft.transform();
                //BufferedImage nuevaImagen = null;
                //nuevaImagen = fft.toImage(nuevaImagen);
                ImageIcon img12 = new ImageIcon(fft.toImage(null));
                Icon iconoI2 = new ImageIcon(img12.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
                view.Contenedor_Imagen.setIcon(iconoI2);
            } catch (FFTException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == view.jMenuItem1_butterworthLowPassFilter) {
            try {
                double radio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese Radio"));
                double delta = Double.parseDouble(JOptionPane.showInputDialog("Ingrese Delta"));
                BufferedImage imagen = img.convertRGBAToIndexed(img.getBufferImagen());
                ImageFFT fft = new ImageFFT(imagen);
                fft.transform();
                fft.butterworthBandPassFilter(radio, delta);//entre 0 y 1.0
                fft.transform();
                //BufferedImage nuevaImagen = null;
                //nuevaImagen = fft.toImage(nuevaImagen);
                ImageIcon img12 = new ImageIcon(fft.toImage(null));
                Icon iconoI2 = new ImageIcon(img12.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
                view.Contenedor_Imagen.setIcon(iconoI2);
            } catch (FFTException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == view.jMenuItem1_butterworthHighPassFilter) {
            try {
                double radio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese Radio"));
                BufferedImage imagen = img.convertRGBAToIndexed(img.getBufferImagen());
                ImageFFT fft = new ImageFFT(imagen);
                fft.transform();
                fft.butterworthHighPassFilter(radio);//entre 0 y 1.0
                fft.transform();
                //BufferedImage nuevaImagen = null;
                //nuevaImagen = fft.toImage(nuevaImagen);
                ImageIcon img12 = new ImageIcon(fft.toImage(null));
                Icon iconoI2 = new ImageIcon(img12.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
                view.Contenedor_Imagen.setIcon(iconoI2);
            } catch (FFTException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == view.jMenuItem1_butterworthBandPassFilter) {
            try {
                double radio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese Radio"));
                double delta = Double.parseDouble(JOptionPane.showInputDialog("Ingrese Delta"));
                BufferedImage imagen = img.convertRGBAToIndexed(img.getBufferImagen());
                ImageFFT fft = new ImageFFT(imagen);
                fft.transform();
                fft.butterworthBandPassFilter(radio, delta);//entre 0 y 1.0
                fft.transform();
                //BufferedImage nuevaImagen = null;
                //nuevaImagen = fft.toImage(nuevaImagen);
                ImageIcon img12 = new ImageIcon(fft.toImage(null));
                Icon iconoI2 = new ImageIcon(img12.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
                view.Contenedor_Imagen.setIcon(iconoI2);
            } catch (FFTException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == view.jMenuItem1_butterworthBandStopFilter) {
            try {
                double radio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese Radio"));
                double delta = Double.parseDouble(JOptionPane.showInputDialog("Ingrese Delta"));
                BufferedImage imagen = img.convertRGBAToIndexed(img.getBufferImagen());
                ImageFFT fft = new ImageFFT(imagen);
                fft.transform();
                fft.butterworthBandStopFilter(radio, delta);//entre 0 y 1.0
                fft.transform();
                //BufferedImage nuevaImagen = null;
                //nuevaImagen = fft.toImage(nuevaImagen);
                ImageIcon img12 = new ImageIcon(fft.toImage(null));
                Icon iconoI2 = new ImageIcon(img12.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
                view.Contenedor_Imagen.setIcon(iconoI2);
            } catch (FFTException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == view.Morfologicas_Erosion) {
            int[] mascara = {1, 1, 1, 1, 1, 1, 1, 1, 1};
            ImageIcon img13 = new ImageIcon(OPM.erosion(img.getBufferImagen(), mascara, mascara.length));
            Icon iconoI3 = new ImageIcon(img13.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(iconoI3);
        }
        if (e.getSource() == view.Morfologicas_Dilatacion) {
            int[] mascara = {0, 1, 0, 1, 1, 1, 0, 1, 0};
            ImageIcon img13 = new ImageIcon(OPM.dilatacion(img.getBufferImagen(), mascara, mascara.length));
            Icon iconoI3 = new ImageIcon(img13.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(iconoI3);
        }
        if (e.getSource() == view.Morfologicas_Apertura) {
            int[] mascaraErosion = {1, 1, 1, 1, 1, 1, 1, 1, 1};
            int[] mascaraDilatacion = {0, 1, 0, 1, 1, 1, 0, 1, 0};
            BufferedImage ero = OPM.erosion(img.getBufferImagen(), mascaraErosion, mascaraErosion.length);
            BufferedImage dila = OPM.dilatacion(ero, mascaraDilatacion, mascaraDilatacion.length);
            ImageIcon img13 = new ImageIcon(dila);
            Icon iconoI3 = new ImageIcon(img13.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(iconoI3);
        }
        if (e.getSource() == view.Morfologicas_Cierre) {
            int[] mascaraErosion = {1, 1, 1, 1, 1, 1, 1, 1, 1};
            int[] mascaraDilatacion = {0, 1, 0, 1, 1, 1, 0, 1, 0};
            BufferedImage dila = OPM.dilatacion(img.getBufferImagen(), mascaraDilatacion, mascaraDilatacion.length);
            BufferedImage ero = OPM.erosion(dila, mascaraErosion, mascaraErosion.length);
            ImageIcon img13 = new ImageIcon(ero);
            Icon iconoI3 = new ImageIcon(img13.getImage().getScaledInstance(view.Contenedor_Imagen.getWidth(), view.Contenedor_Imagen.getHeight(), Image.SCALE_DEFAULT));
            view.Contenedor_Imagen.setIcon(iconoI3);
        }
        //} 
    }
}
