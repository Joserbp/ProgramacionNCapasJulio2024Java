/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.DAO;

import com.Digis01.ProgramacionNCapasJulio24.ML.Pais;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author digis
 */
class PaisRowMapper implements RowMapper<Pais> {

    @Override
    public Pais mapRow(ResultSet rs, int rowNum) throws SQLException {
        Pais pais = new Pais();
        pais.setIdPais(rs.getInt("IdPais"));
        pais.setNombre(rs.getString("Nombre"));
        return pais;
    }
    
}
