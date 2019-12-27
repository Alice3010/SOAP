/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.soaap.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 *
 * @author macbook
 */
public class Student {
    private String name;
    private String secondName;
    private List<Course> courses = new ArrayList(); 
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    
    @Override
    public String toString() {
        return "Student{" + "name=" + name + ", secondName=" + secondName + ", courses=" + courses + '}';
    }
}
