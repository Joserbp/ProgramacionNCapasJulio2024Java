/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.Controller;

import com.Digis01.ProgramacionNCapasJulio24.DAO.AlumnoDAOImplementation;
import com.Digis01.ProgramacionNCapasJulio24.DAO.SemestreDAOImplementation;
import com.Digis01.ProgramacionNCapasJulio24.ML.Alumno;
import com.Digis01.ProgramacionNCapasJulio24.ML.Result;
import com.Digis01.ProgramacionNCapasJulio24.ML.Semestre;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private SemestreDAOImplementation semestreDAOImplementation;
    
    @GetMapping("/GetAll")
    public String GetAll(Model model){
        //Object datos = alumnoDAOImplementation.GetAll();
        Result result = alumnoDAOImplementation.GetAllSP();
        model.addAttribute("Alumnos", (List<Alumno>)result.object);
        return "AlumnoGetAll";
    }
    
    @GetMapping("/Form/{idAlumno}") //Mostar el form
    public String Form(@PathVariable int idAlumno,Model model){
        
        if(idAlumno == 0){ //ADD
            Alumno alumno = new Alumno();
            alumno.setSemestre(new Semestre());      
            model.addAttribute("Alumno", alumno);     
        }else{  //Update
            Object alumnoActualizar = alumnoDAOImplementation.GetById(idAlumno);
            List<Alumno> alumnos = (List<Alumno>)alumnoActualizar;
            Alumno alumno = alumnos.get(0);         
            model.addAttribute("Alumno", alumno);
        }
        Object datosSemestre = semestreDAOImplementation.GetAll();
        model.addAttribute("Semestres", (List<Semestre>)datosSemestre);
        return "AlumnoForm";
    }
    
    
    @PostMapping("/Form")
    public String Form(@ModelAttribute Alumno alumno){
        //Validar el ID
        //Id = 0 ADD
        //ID != 0 UPDATE
        //int rowAffetted = alumnoDAOImplementation.Add(alumno);
        Result result = alumnoDAOImplementation.AddSP(alumno);
        int stop;
        return "redirect:/Alumno/GetAll";
    }
    //Recibir la informacion de la vista
    
    
}
