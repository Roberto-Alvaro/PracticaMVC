package Ejecutable;

import Controlador.Controlador;
import Vista.Frame;
import Modelacion.Imagen;

public class Main {
    public static void main(String[] args) {
        Imagen img = new Imagen();
        Frame vent = new Frame();
        Controlador control;
        control = new Controlador(vent, img);
        control.Iniciar();
        vent.setVisible(true);
        
    }
}



// https://github.com/sebaxtian/AxpherPicture/tree/master/src/imagen
// link de clases de algunos metodods
