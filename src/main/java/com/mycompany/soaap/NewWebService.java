/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.soaap;

import classes.Student;
import classes.Students;
import java.io.File;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBException;
import org.w3c.dom.Document;
//import ru.rsatu.seryakova.jabgen.genclass.Student;
//import ru.rsatu.seryakova.jabgen.genclass.Students;



/**
 *
 * @author macbook
 */
@WebService()

public class NewWebService {

/**
* This is a sample web service operation
*/
@WebMethod
public String hello(@WebParam(name = "name") String txt) {
return "Hello " + txt + " !";
}

    /**
     *
     * @param valName
     * @param valSurname
     * @return
     */
    @WebMethod
    public String getStudentByName(@WebParam(name = "name") String valName, 
                               @WebParam(name = "secondname") String valSurname) throws Exception {
        File file = new File("/Users/macbook/Desktop/labs/SOAAP/dataBase.xml");
          if (file.exists()) {          
            Document doc = XMLService.getDocument(file.getAbsolutePath()); //Считываем документ 
            return XMLService.printSubjects(doc, valName, valSurname); //Печать всех экзаменов студента  
          } else {
              return "Please change path to XML File!";
          }
    }
    
    @WebMethod
    public Student getStudentByName2(@WebParam(name = "name") String valName, 
                               @WebParam(name = "secondname") String valSurname) throws JAXBException {
        
        Students studd = null;
        File file = new File("/Users/macbook/Desktop/labs/SOAAP/src/main/resources/dataBase.xml");
        if (file.exists()) {          
            studd = XMLService.unmarshal(file); //Считываем документ 
        } 
        return XMLService.find(studd, valName, valSurname);
             
            
    }
    
}
    



