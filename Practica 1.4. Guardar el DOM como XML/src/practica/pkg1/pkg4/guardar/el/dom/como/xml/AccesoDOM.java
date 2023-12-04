/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package practica.pkg1.pkg4.guardar.el.dom.como.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;//for Document
import org.w3c.dom.Document;
import java.util.*;
import java.io.*;//clase File
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 *
 * @author Juan
 */
public class AccesoDOM {

    Document doc;

    public int abriXMLaDOM(File f) {
        try {
            System.out.println("Abriendo archivo XML file y generando DOM");
            //creamos nuevo objeto DocumentBuilder al que apunta la variable

            DocumentBuilderFactory factory
                    = DocumentBuilderFactory.newInstance();
            //ignorar comentarios y espacios blancos

            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            //DocumentBuilder tiene el método parse que es el que genera DOM en memoria

            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(f);
            // ahora doc apunta al arbol DOM y podemos recorrerlo

            System.out.println("DOM creado con éxito.\n");
            return 0;//si el método funciona
        } catch (Exception e) {
            System.out.println(e);
            return -1;//if the method aborta en algún punto
        }
    }

    //añade el nuevo método
    public void recorreDOMyMuestra() {
        String[] datos = new String[3];//lo usamos para la información de cada libro
        Node nodo = null;
        Node root = doc.getFirstChild();
        NodeList nodelist = root.getChildNodes(); //(1)Ver dibujo del árbol
        //recorrer el árbol DOM. El 1er nivel de nodos hijos del raíz
        for (int i = 0; i < nodelist.getLength(); i++) {
            nodo = nodelist.item(i);//node toma el valor de los hijos de raíz
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {//miramos nodos de tipo Element

                Node ntemp = null;
                int contador = 1;
                //sacamos el valor del atributo publicado
                datos[0] = nodo.getAttributes().item(0).getNodeValue();
                //sacamos los valores de los hijos de nodo, Titulo y Autor
                NodeList nl2 = nodo.getChildNodes();//obtenemos lista de hijos (2)
                for (int j = 0; j < nl2.getLength(); j++) {//iteramos en esa lista 
                    ntemp = nl2.item(j);
                    if (ntemp.getNodeType() == Node.ELEMENT_NODE) {
                        //para conseguir el texto de titulo y autor, se
                        //puedo hacer con getNodeValue(), también con 
                        //getTextContent() si es ELEMENT
                        datos[contador] = ntemp.getTextContent();
                        // también datos[contador] = ntemp.getChildNodes().item(0).getNodeValue();
                        contador++;
                    }
                }
                //el array de String datos[] tiene los valores que necesitamos

                System.out.println("El año de creación del libro es el " + datos[0] + " lo escribio " + datos[2] + " y el nombre del lirbo es " + datos[1] + "\n");
            }
        }
    }

    //creo el metodo de insertar un libro con una variable para cada cosa titulo, autor y fecha
    public int insertLibroEnDOM(String titulo, String autor, String fecha){
        try {
            System.out.println("Añadir libro al árbol DOM: " + titulo + ";" + autor + ";" + fecha);
            //Crea los nodos -> los añade al padre desde las hojas a la raíz
            //CREA TÍTULO con el texto en medio
            
            Node nTitulo = doc.createElement("Titulo"); //Crea etiquetas <Titulo>...</Titulo>
            Node nTitulo_text = doc.createTextNode(titulo); //Crea el nodo texto para el Titulo
            nTitulo.appendChild(nTitulo_text); //Añade el titulo a las etiquetas <Titulo>titulo</Titulo>            
            //CREA AUTOR
            //Otra manera de hacerlo
            //Node nAutor=doc.createElement("Autor").appendChild(doc.createTextNode(autor));
                
            Node nAutor = doc.createElement("Autor");
            Node nAutor_text = doc.createTextNode(autor);
            nAutor.appendChild(nAutor_text);
            
            //CREA LIBRO con atributo y nodos Titulo y Autor
            Node nLibro = doc.createElement("Libro");
            ((Element)nLibro).setAttribute("publicado", fecha);
            nLibro.appendChild(nTitulo);
            nLibro.appendChild(nAutor);

            nLibro.appendChild(doc.createTextNode("\n")); //Para insertar saltos de línea
            
            Node raiz = doc.getFirstChild(); //tb.doc.getChildNodes().item(0)
            raiz.appendChild(nLibro);
            System.out.println("Libro insertado en DOM");
            return 0;
        }catch (Exception e){
            System.out.println(e);
            return -1;
        }
    }

    //creo el metodo para borrar un libro segun su titulo
    public int deleteNode(String tit) {
        System.out.println("Buscando el Libro " + tit + " para borrarlo");
        try {
            //Nodo root=doc.getFirstChild();
            Node raiz = doc.getDocumentElement();
            NodeList nl1 = doc.getElementsByTagName("Titulo");
            Node n1;
            for (int i = 0; i < nl1.getLength(); i++) {
                n1 = nl1.item(i);
                if (n1.getNodeType() == Node.ELEMENT_NODE) {
                    if (n1.getChildNodes().item(0).getNodeValue().equals(tit)) {
                        System.out.println("Borrando el nodo <Libro> con titulo " + tit);
                        n1.getParentNode().getParentNode().removeChild(n1.getParentNode());
                    }
                }
            }
            System.out.println("Nodo borrado");
            return 0;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            return -1;
        }
    }

    //gurado el archivo actualizado 
    public void guardarDOMcomoArchivo(String nuevoArchivo) {
        try {
            Source src = new DOMSource(doc); // Definimos el origen
            StreamResult rst = new StreamResult(new File(nuevoArchivo));// Definimos el resultado
            // Declaramos el Transformer que tiene el método .transform() que necesitamos.
            Transformer transformer = TransformerFactory.newInstance().newTransformer();

            // Opción para indentar el archivo
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(src, (javax.xml.transform.Result) rst);
            System.out.println("Archivo creado del DOM con exito\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}//fin clase
