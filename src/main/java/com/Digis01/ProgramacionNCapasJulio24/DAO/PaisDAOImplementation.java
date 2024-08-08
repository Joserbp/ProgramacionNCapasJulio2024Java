/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.DAO;

import com.Digis01.ProgramacionNCapasJulio24.ML.Pais;
import com.Digis01.ProgramacionNCapasJulio24.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author digis
 */
@Repository
public class PaisDAOImplementation implements PaisDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PaisDAOImplementation(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Override
    public Result GetAll() {
        Result result = new Result();
        try{
            List<Pais> paises = jdbcTemplate.execute("{Call PaisGetAll(?)}", (CallableStatementCallback<List<Pais>>) callableStatementCallback ->{ 
               callableStatementCallback.registerOutParameter("pDatosCursor", Types.REF_CURSOR);
               callableStatementCallback.execute();
               ResultSet rs = (ResultSet)callableStatementCallback.getObject("pDatosCursor");
               List<Pais> paisList = new ArrayList();
               PaisRowMapper paisRowMapper = new PaisRowMapper();
               while(rs.next()){
                   Pais pais = paisRowMapper.mapRow(rs,rs.getRow());
                   paisList.add(pais);
               }
               return paisList;
            });
            
            if(paises != null){
                result.correct = true;
                result.object = paises;
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
    
    
}
