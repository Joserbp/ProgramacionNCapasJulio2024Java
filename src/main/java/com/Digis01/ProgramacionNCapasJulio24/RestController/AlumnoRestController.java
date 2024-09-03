/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.RestController;

import com.Digis01.ProgramacionNCapasJulio24.DAO.AlumnoDAOImplementation;
import com.Digis01.ProgramacionNCapasJulio24.ML.Alumno;
import com.Digis01.ProgramacionNCapasJulio24.ML.Result;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author digis
 */
@RestController
@RequestMapping("/api/Alumno")
public class AlumnoRestController {
    
    @Autowired
    private AlumnoDAOImplementation alumnoDAOImplementation;


    @PostMapping("/Add")
    public ResponseEntity Add(@RequestBody Alumno alumno){
       Result result = alumnoDAOImplementation.AddJPA(alumno);
       if(result.correct){
           return new ResponseEntity(result, HttpStatus.CREATED);
       }else{
           return new ResponseEntity(result, HttpStatusCode.valueOf(400));
       }
    }
    
    @GetMapping("/GetAll")
    public ResponseEntity GetAll(){
       Result result = alumnoDAOImplementation.GetAllSP();
       if(result.correct){
           return new ResponseEntity(result, HttpStatus.OK);
       }else{
           return new ResponseEntity(result, HttpStatusCode.valueOf(400));
       }
    }
    
}
