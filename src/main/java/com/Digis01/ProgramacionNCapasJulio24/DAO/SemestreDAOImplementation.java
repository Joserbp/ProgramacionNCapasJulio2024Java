/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.DAO;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author digis
 */
@Repository
public class SemestreDAOImplementation implements SemestreDAO{

    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public SemestreDAOImplementation(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    
    @Override
    public Object GetAll() {
        String query = "SELECT IdSemestre,Nombre FROM Semestre";
        return jdbcTemplate.query(query, new SemestreRowMapper());
    }
    
}
