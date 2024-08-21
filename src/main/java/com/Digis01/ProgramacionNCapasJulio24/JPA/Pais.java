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

/**
 *
 * @author digis
 */
@Entity
public class Pais {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    @Column(name = "idpais")
    private int IdPais;
    
    @Column(name = "nombre")
    private String Nombre;
}
