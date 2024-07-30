/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.DAO;

import com.Digis01.ProgramacionNCapasJulio24.ML.Alumno;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author digis
 */
@Repository  //Indicar que esta clase tiene acceso a datos
public class AlumnoDAOImplementation implements AlumnoDAO{

    
    //INYECCION DE DEPENDENCIAS
        //Incluir una clase dentro de otra
        //JDBC
    
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AlumnoDAOImplementation(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    
    @Override
    public Object GetAll() {
        String query = "SELECT Nombre,Apellido FROM Alumno"; //TODOS LOS DATOS
//        Object datos = jdbcTemplate.query(query, new AlumnoRowMapper());
//        return datos;
        return jdbcTemplate.query(query, new AlumnoRowMapper());
    }

    @Override
    public int Add(Alumno alumno) {
        String query = "INSERT INTO Alumno(Nombre,Apellido,FechaNacimiento,Genero,Telefono,Celular,Email,IdSemestre)  VALUES (?,?,TO_DATE(?,'yyyy-mm-dd'),?,?,?,?,?)";
        return jdbcTemplate.update(query,
                alumno.getNombre(),
                alumno.getApellido(),
                alumno.getFechaNacimiento(),
                alumno.getGenero(),
                alumno.getTelefono(),
                alumno.getCelular(),
                alumno.getEmail(),
                alumno.getSemestre().getIdSemestre());
    }
    
}
