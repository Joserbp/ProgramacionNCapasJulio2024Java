/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.ML;

/**
 *
 * @author digis
 */
public class ErrorExcel {
    private int Fila;
    private String Mensaje;

    public int getFila() {
        return Fila;
    }

    public void setFila(int Fila) {
        this.Fila = Fila;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String Mensaje) {
        this.Mensaje = Mensaje;
    }
    
//    public ErrorExcel(){
//        
//    }
    
    public ErrorExcel(int Fila, String Mensaje){
        this.Fila = Fila;
        this.Mensaje = Mensaje;
    }
    
}
