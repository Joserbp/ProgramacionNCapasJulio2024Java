/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.DAO;

import com.Digis01.ProgramacionNCapasJulio24.ML.Estado;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author digis
 */
class EstadoRowMapper implements RowMapper<Estado>{

    @Override
    public Estado mapRow(ResultSet rs, int rowNum) throws SQLException {
        Estado estado = new Estado();
        estado.setIdEstado(rs.getInt("IdEstado"));
        estado.setNombre(rs.getString("Nombre"));
        return estado;
    }
    
}
