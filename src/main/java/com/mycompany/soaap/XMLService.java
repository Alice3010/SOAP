/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.soaap;


import classes.Student;
import classes.Students;
import java.io.File;
import java.net.InetAddress;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

//import Students.class;

/**
 *
 * @author macbook
 */
public class XMLService {
    
   
    /**
     * Возвращает объект Document, который является объектным представлением XML
     * документа.
     */
    public static Document getDocument(String filePath) throws Exception {
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setValidating(false);
            DocumentBuilder builder = f.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));
            removeWhitespaceNodes(document.getDocumentElement());
            return document;
        } catch (Exception exception) {
            String message = "XML parsing error!";
            throw new Exception(message);
        }
    }
    
    public static Students unmarshal(File xmlFile) throws JAXBException {
        JAXBContext jaxbContext = null;
        Students student = null;
        try {
        jaxbContext = JAXBContext.newInstance(Students.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<Students> stud = jaxbUnmarshaller.unmarshal(new StreamSource(xmlFile), Students.class);
        student = stud.getValue();
            return student;
        } catch (JAXBException e) {
            System.out.print("Не обработано");
            return null;
        }
        
        
    }
    public static Student find(Students studd, String name, String secondname) {
        for (Student std: studd.getStudents()) {
            if ((std.getName().equals(name)) && (std.getSecondname().equals(secondname))) {
            return std;
            }
        }
        return null;
    }

    /**
     * Очистка Node от пробелов и табуляций; 
     */
    public static void removeWhitespaceNodes(Element e) {
        NodeList children = e.getChildNodes();
        for (int i = children.getLength() - 1; i >= 0; i--) {
            Node child = children.item(i);
            if (child instanceof Text && ((Text) child).getData().trim().length() == 0) {
                e.removeChild(child);
            } else if (child instanceof Element) {
                removeWhitespaceNodes((Element) child);
             }
        }
    }
    //печать всех экзаменов и средней оценки по ним
    public static String printSubjects(Document doc, String name, String secondName) throws Exception {
       StringBuilder result = new StringBuilder();
         result.append("Student name: " + name + "\n");  
         result.append("Student secondname: " + secondName + "\n");  
        //Начало печати//Выполняем XPath запрос
        String strXPath = "//student[name=\"" + name + "\"][secondname=\"" + secondName + "\"]//subject";
        NodeList nodes = (NodeList) execXPath(doc, strXPath);
        float sum = 0;
        float kol = 0;
        //Выполняем печать экзаменов и зачётов
        for (int i = 0; i < nodes.getLength(); i++) {
            Node curnode = nodes.item(i);
            NodeList curnode1 = curnode.getChildNodes();
            if (curnode.getNodeType() == Node.ELEMENT_NODE) {
                result.append(curnode.getNodeName()+ "\n");
                if (curnode1.getLength() == 2) { //если она конечная
                    result.append(", Data: "+ "\n");
                    result.append(curnode.getTextContent()+ "\n"); 
                }
                Element element = (Element) curnode;
                sum +=Integer.valueOf( element.getElementsByTagName("score").item(0).getTextContent());
                kol+=1;
            }
            
        }
        float res = -1;
        if (kol!=0) { 
            res = sum/kol;
        }
        String rez =Float.toString(res); 
        result.append("Student average mark: \"" + rez.substring(0,  Float.toString(res).indexOf(".")+2) + "\n");  
        return result.toString();
    }
   
   
    //Выполнить XPath запрос
    public static Object execXPath(Document doc, String strXPath) throws XPathExpressionException {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        XPathExpression expr = xpath.compile(strXPath);
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        //NodeList nodes = (NodeList) result;
        return result;
    }
    
    
}
