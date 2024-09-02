/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author digis
 */
//@Controller //MVC WEB
@RestController
@RequestMapping("/api/Operacion")
public class OperacionController {
    
    
    @GetMapping("/Saludar/{Nombre}") //ENDPOINT
    public String Saludar(@PathVariable("Nombre") String Nombre){
        return "Hola " + Nombre;
        //HASHMAP  MAP
    }
}
