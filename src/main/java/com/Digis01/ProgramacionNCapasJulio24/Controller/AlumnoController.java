/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.Controller;

import com.Digis01.ProgramacionNCapasJulio24.DAO.AlumnoDAOImplementation;
import com.Digis01.ProgramacionNCapasJulio24.ML.Alumno;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String GetAll(Model model){
        Object datos = alumnoDAOImplementation.GetAll();
        model.addAttribute("Alumnos", (List<Alumno>)datos);
        return "AlumnoGetAll";
    }
    
    @GetMapping("/Form")
    public String Form(Model model){
        model.addAttribute("Alumno", new Alumno());
        return "AlumnoForm";
    }
    
    @PostMapping("/Form")
    public String Form(@ModelAttribute Alumno alumno){
        int rowAffetted = alumnoDAOImplementation.Add(alumno);
        return "tore GetAll"; // toRedirec
    }
    //Recibir la informacion de la vista
    
    
}
