/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.DAO;

import com.Digis01.ProgramacionNCapasJulio24.ML.Alumno;
import com.Digis01.ProgramacionNCapasJulio24.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
    private EntityManager entityManager;
    
    @Autowired
    public AlumnoDAOImplementation(DataSource dataSource, EntityManager entityManager) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.entityManager = entityManager;
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
            int rowAffeted = jdbcTemplate.execute("{Call AlumnoAdd(?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatementCallback ->{
            callableStatementCallback.setString("pNombre", alumno.getNombre());
            callableStatementCallback.setString("pApellido", alumno.getApellido());
            callableStatementCallback.setString("pFechaNacimiento", alumno.getFechaNacimiento());
            callableStatementCallback.setString("pGenero", alumno.getGenero());
            callableStatementCallback.setString("pTelefono", alumno.getTelefono());
            callableStatementCallback.setString("pCelular", alumno.getCelular());
            callableStatementCallback.setString("pEmail", alumno.getEmail());
            callableStatementCallback.setString("pImagen", alumno.getImagen());
            //callableStatementCallback.registerOutParameter("pIdAlumno", Types.NUMERIC);
            
            callableStatementCallback.execute();
            //result.object = callableStatementCallback.getObject("pIdAlumno");
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

    @Override
    public Result GetAllSP() {
        Result result = new Result();
        try{
            List<Alumno> alumnos = jdbcTemplate.execute("{Call AlumnoGetAll(?)}", (CallableStatementCallback<List<Alumno>>) callableStatementCallback ->{ 
               callableStatementCallback.registerOutParameter("pDatosCursor", Types.REF_CURSOR);
               callableStatementCallback.execute();
               ResultSet rs = (ResultSet)callableStatementCallback.getObject("pDatosCursor");
               List<Alumno> alumnosList = new ArrayList();
               AlumnoRowMapper alumnoRowMapper = new AlumnoRowMapper();
               while(rs.next()){
                   Alumno alumno = alumnoRowMapper.mapRow(rs,rs.getRow());
                   alumnosList.add(alumno);
               }
               return alumnosList;
            });
            
            if(alumnos != null){
                result.correct = true;
                result.object = alumnos;
            } else{
                result.correct = false;
                result.errorMessage = "La tabla alumno esta vacia";
            }
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.exception = ex; 
        }
        return result;
    }
    
    
    @Override
    public Result ChangeStatus(int IdAlumno, int Status) {
        Result result = new Result();
        try{
            int rowAffeted = jdbcTemplate.execute("{Call AlumnoChangeStatus(?,?)}", (CallableStatementCallback<Integer>) callableStatementCallback ->{
            callableStatementCallback.setInt("pIdAlumno", IdAlumno);
            callableStatementCallback.setInt("pStatus", Status);
            
            callableStatementCallback.execute();
            //result.object = callableStatementCallback.getObject("pIdAlumno");
            return callableStatementCallback.getUpdateCount();
            });
            
            if(rowAffeted != 0){
                result.correct = true;
            }else{
                result.correct = false;
                result.errorMessage = "No se actualizo el status del registro";
            }
        }
        catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.exception = ex;
        }
        return result;
    }

    @Override
    @Transactional
    public Result AddJPA(Alumno alumno) {
        Result result = new Result();
        try{
            com.Digis01.ProgramacionNCapasJulio24.JPA.Alumno alumnoJPA = new com.Digis01.ProgramacionNCapasJulio24.JPA.Alumno();
            alumnoJPA.setNombre(alumno.getNombre());
            alumnoJPA.setApellido(alumno.getApellido());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date fechaConvertida = dateFormat.parse(alumno.getFechaNacimiento());
            alumnoJPA.setFechaNacimiento(fechaConvertida);
            alumnoJPA.setGenero(alumno.getGenero());
            alumnoJPA.setTelefono(alumno.getTelefono());
            alumnoJPA.setCelular(alumno.getCelular());
            alumnoJPA.setEmail(alumno.getEmail());
            alumnoJPA.setSemestre(new com.Digis01.ProgramacionNCapasJulio24.JPA.Semestre());
            alumnoJPA.getSemestre().setIdSemestre(alumno.getSemestre().getIdSemestre());
            alumnoJPA.setImagen(alumno.getImagen());
            alumnoJPA.setStatus(1);
            entityManager.persist(alumnoJPA); //ADD
            entityManager.merge(alumnoJPA); //UPDATE
            entityManager.remove(alumnoJPA); //Lo van investigar
            
            result.correct = true;
        }
        catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.exception = ex;
        }
        return result;

    }
}
