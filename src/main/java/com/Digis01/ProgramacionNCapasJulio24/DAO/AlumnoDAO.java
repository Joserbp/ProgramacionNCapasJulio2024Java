/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.DAO;

import com.Digis01.ProgramacionNCapasJulio24.ML.Alumno;

/**
 *
 * @author digis
 */
public interface AlumnoDAO {
    //FIRMA DE CONTRATOS
    //NOS OBLIGA A USAR LOS METODOS DE ESTA INTERFAZ EN LAS CLASES QUE LA IMPLEMENTEN
    // TIPO RETORNO + NOMBRE METODO + PARAMETROS
    // SIN IMPLEMENTACION
    Object GetAll();
    int Add(Alumno alumno);
    Object GetById(int idAlumno);
   // Object Add(Alumno alumno);
}
