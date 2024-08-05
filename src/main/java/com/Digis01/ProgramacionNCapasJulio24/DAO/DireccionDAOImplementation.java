/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.DAO;

import com.Digis01.ProgramacionNCapasJulio24.ML.Direccion;
import com.Digis01.ProgramacionNCapasJulio24.ML.Result;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author digis
 */
public class DireccionDAOImplementation implements DireccionDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DireccionDAOImplementation(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Result Add(Direccion direccion, int IdAlumno) {
        Result result = new Result();
        try{
            //SP DireccionAdd
        }
        catch(Exception ex){
            
        }
        return result;
    }
    
}
