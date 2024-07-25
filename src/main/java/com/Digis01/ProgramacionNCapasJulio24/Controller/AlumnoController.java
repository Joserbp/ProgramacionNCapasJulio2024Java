/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.Controller;

import com.Digis01.ProgramacionNCapasJulio24.DAO.AlumnoDAOImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author digis
 */
@Controller 
@RequestMapping("/Alumno")
public class AlumnoController {
    
    @Autowired
    private AlumnoDAOImplementation alumnoDAOImplementation;
    
    @GetMapping("/GetAll")
    public String GetAll(){
        Object datos = alumnoDAOImplementation.GetAll();
        return "GetAll";
    }
    
}
