/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practica.pkg1.pkg4.guardar.el.dom.como.xml;

import java.io.File;


/**
 *
 * @author Juan
 */
public class Practica14GuardarElDOMComoXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        AccesoDOM a = new AccesoDOM();
        File f = new File("Libros.xml");
        a.abriXMLaDOM(f);
        a.recorreDOMyMuestra();
        a.insertLibroEnDOM("Yerma", "Lorca", "1935");
        a.deleteNode("Don Quijote");
        a.guardarDOMcomoArchivo("LibrosDeDom.xml");
    }
}