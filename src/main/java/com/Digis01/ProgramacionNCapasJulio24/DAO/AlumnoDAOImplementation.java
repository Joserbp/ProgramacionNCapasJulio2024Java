/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.DAO;

import com.Digis01.ProgramacionNCapasJulio24.ML.Alumno;
import com.Digis01.ProgramacionNCapasJulio24.ML.Result;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
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
        String query = "SELECT IdAlumno,Nombre,Apellido FROM Alumno"; //TODOS LOS DATOS
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

    @Override
    public Object GetById(int idAlumno) {
        String query = "SELECT IdAlumno,Nombre,Apellido FROM Alumno WHERE IdAlumno = ?"; //TODOS LOS DATOS
//        Object datos = jdbcTemplate.query(query, new AlumnoRowMapper());
//        return datos;
        return jdbcTemplate.query(query, new AlumnoRowMapper(), idAlumno);
    }


        //CallebleStatement
        //CallBack  //Return 
                    //Parametro va otra funcion
                    //Funcion que ejecuta otra funcion
                    //Interfaz
                    //Funcion anonima o funcion flecha o arrow function o function lambda
                        //Funcion sin nombre
                        //Se guardan en una varible
    @Override
    public Result AddSP(Alumno alumno) {
        Result result = new Result();
        try{
            int rowAffeted = jdbcTemplate.execute("{Call AlumnoAdd(?,?,?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatementCallback ->{
            callableStatementCallback.setString("pNombre", alumno.getNombre());
            callableStatementCallback.setString("pApellido", alumno.getApellido());
            callableStatementCallback.setString("pFechaNacimiento", alumno.getFechaNacimiento());
            callableStatementCallback.setString("pGenero", alumno.getGenero());
            callableStatementCallback.setString("pTelefono", alumno.getTelefono());
            callableStatementCallback.setString("pCelular", alumno.getCelular());
            callableStatementCallback.setString("pEmail", alumno.getEmail());
            
            callableStatementCallback.execute();
            return callableStatementCallback.getUpdateCount();
            });
            
            if(rowAffeted != 0){
                result.correct = true;
            }else{
                result.correct = false;
                result.errorMessage = "No se inserto el registro";
            }
        }
        catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.exception = ex;
        }
        return result;
    }
    
}
