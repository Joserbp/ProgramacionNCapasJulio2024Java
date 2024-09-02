/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.RestController;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Map<String,Object> Saludar(@PathVariable("Nombre") String Nombre){
        //HASHMAP --- (Array) Estructurado Mapeo de llave valor
        //JSON javaScript object notation
        Map<String,Object> response = new HashMap<>();
        response.put("Mensaje", "Hola " + Nombre);
        response.put("Nombre" , Nombre);
        return response;
    }
    
    @GetMapping("/Saludar2/{Nombre}") //ENDPOINT
    public ResponseEntity Saludar2(@PathVariable("Nombre") String Nombre){
        Map<String,Object> response = new HashMap<>();
        
        //ResponseEntity -- Informacion + StatusCode
        if(Nombre.equals(" ")){
            response.put("Mensaje", "El nombre esta vacio");
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }else{
          response.put("Mensaje", "Hola " + Nombre);
         response.put("Nombre" , Nombre);
        //ResponseEntity -- Informacion + StatusCode
        return new ResponseEntity(response, HttpStatus.OK);
        }

    }
}
