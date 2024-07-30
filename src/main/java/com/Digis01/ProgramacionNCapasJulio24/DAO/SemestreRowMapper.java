/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.DAO;

import com.Digis01.ProgramacionNCapasJulio24.ML.Semestre;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author digis
 */
public class SemestreRowMapper implements RowMapper<Semestre> {

    @Override
    public Semestre mapRow(ResultSet rs, int rowNum) throws SQLException {
        Semestre semestre = new Semestre();
        semestre.setIdSemestre(rs.getInt("IdSemestre"));
        semestre.setNombre(rs.getString("Nombre"));
        return semestre;
    }
    
}
