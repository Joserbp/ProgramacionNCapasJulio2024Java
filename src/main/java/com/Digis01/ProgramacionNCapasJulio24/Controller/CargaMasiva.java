/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.Controller;

import com.Digis01.ProgramacionNCapasJulio24.ML.Alumno;
import com.Digis01.ProgramacionNCapasJulio24.ML.Semestre;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author digis
 */
@Controller
@RequestMapping("/CargaMasiva")
public class CargaMasiva {
    
    @GetMapping("/Get")
    public String Get(){
        String path = "C:\\Users\\digis\\OneDrive\\Documentos\\Jose Ramon Becerra Perez\\Proyectos\\Java\\ProgramacionNCapasJulio24\\CargaMasiva.txt";
        try{
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        //FileReader   Caracter por caracter h
        //bufferedReader     Linea por Linea          
        //Reader - Todo el documento en una sola consulta LIST
        String linea;
        //Diego Alejandro|Saavedra|234567|34567|H|2|diego@gmail.com|2002-09-17
     
            while((linea = bufferedReader.readLine()) != null){
           String [] datos = linea.split("|",-1);
           Alumno alumno = new Alumno();
           alumno.setNombre(datos[0]);
           alumno.setApellido(datos[1]);
           alumno.setSemestre(new Semestre());
           alumno.getSemestre().setIdSemestre(Integer.parseInt(datos[5]));
           
           daoImplementacion.addSP(alumno);
         }
        }
        catch(IOException ex){
            
        }
        catch(Exception ex){
        
        }
        
        return "CargaMasivaGet";
    } 
    
}
