/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author digis
 */
@Entity
public class Alumno {
    
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    @Column(name = "idalumno")
    private int IdAlumno;
    
    @Column(name = "nombre")
    private String Nombre;
    
    @Column(name = "idsemestre")
    @ManyToOne
    private Semestre Semestre; 
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fechanacimiento")
    private Date FechaNacimiento;
    
    @Lob
    @Column(name = "imagen")
    private String Imagen;
}
