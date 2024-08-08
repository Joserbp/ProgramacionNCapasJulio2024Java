/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.DAO;

import com.Digis01.ProgramacionNCapasJulio24.ML.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author digis
 */
public class AlumnoRowMapper implements RowMapper<Alumno>{

    @Override
    public Alumno mapRow(ResultSet rs, int rowNum) throws SQLException {
        Alumno alumno = new Alumno();
        alumno.setIdAlumno(rs.getInt("IdAlumno"));
        alumno.setNombre(rs.getString("NombreAlumno"));
        alumno.setApellido(rs.getString("Apellido"));
        alumno.setSemestre(new Semestre());
        alumno.getSemestre().setIdSemestre(rs.getInt("IdSemestre"));
        alumno.getSemestre().setNombre(rs.getString("NombreSemestre"));
        alumno.setDireccion(new Direccion());
       // alumno.getDireccion().setIdDireccion(rs.getInt("IdDireccion"));
        alumno.getDireccion().setCalle(rs.getNString("Calle"));
        alumno.getDireccion().setNumeroExterior(rs.getString("NumeroExterior"));
        alumno.getDireccion().setColonia(new Colonia());
        alumno.getDireccion().getColonia().setIdColonia(rs.getInt("IdColonia"));
        alumno.getDireccion().getColonia().setNombre(rs.getString("NombreColonia"));
        
        //alumno.getDireccion().getColonia().getEstado().getPais().setNombre(rs.getString("NombrePais"));
        return alumno;
    }
    
}
