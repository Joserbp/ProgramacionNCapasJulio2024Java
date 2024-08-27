/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.Controller;

import com.Digis01.ProgramacionNCapasJulio24.DAO.AlumnoDAOImplementation;
import com.Digis01.ProgramacionNCapasJulio24.ML.Alumno;
import com.Digis01.ProgramacionNCapasJulio24.ML.Semestre;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author digis
 */
@Controller
@RequestMapping("/CargaMasiva")
public class CargaMasiva {
    
    @Autowired
    private AlumnoDAOImplementation alumnoDAOImplementation;
    
    @GetMapping("/Get")
    public String Get(){
        return "CargaMasivaGet";
    } 
    
    @PostMapping("/txt")
    public String CargaTxt(@RequestParam MultipartFile txtFile){
       // String path = "C:\\Users\\digis\\OneDrive\\Documentos\\Jose Ramon Becerra Perez\\Proyectos\\Java\\ProgramacionNCapasJulio24\\CargaMasiva.txt";
        try{
        //FileReader fileReader = new FileReader(path);
        InputStream inputStream = txtFile.getInputStream();
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputReader);
        
        //FileReader   Caracter por caracter h
        //bufferedReader     Linea por Linea          
        //Reader - Todo el documento en una sola consulta LIST
        String linea;
        //Diego Alejandro|Saavedra|234567|34567|H|2|diego@gmail.com|2002-09-17
        bufferedReader.readLine();
           while((linea = bufferedReader.readLine()) != null){
           String [] datos = linea.split("\\|");
           Alumno alumno = new Alumno();
           alumno.setNombre(datos[0]);
           alumno.setApellido(datos[1]);
           alumno.setSemestre(new Semestre());
           alumno.getSemestre().setIdSemestre(Integer.parseInt(datos[5]));
           
           alumnoDAOImplementation.AddJPA(alumno);
         }
        }
        catch(IOException ex){
            
        }
        catch(Exception ex){
        
        }
       return "redirect" ;
    }
    
    @PostMapping("/excel")
    public String CargaExcel(@RequestParam MultipartFile excelFile){
    try {
        if(excelFile != null && !excelFile.isEmpty()){
            
            String extension = StringUtils.getFilenameExtension(excelFile.getOriginalFilename());
            if(extension.equals("xlsx")){
                //Copia del excel //Historico // Validar/Seguridad/Respaldo
                String root = System.getProperty("user.dir"); //Raiz del proyecto
                String path = "/src/main/resources/static/ArchivosExcel/";
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                //C:User......../src/res/sta/archEx/20240827170112CargaMasiva.xlsx
                String nombreArchivo = excelFile.getOriginalFilename(); //Extension
                String pathFinal = root + path + fecha + nombreArchivo; 
            
                excelFile.transferTo(new File(pathFinal)); //Guarda un archivo copia
               
                List<Alumno> alumnos = LeerExcel(excelFile);
                //ValidarDatos(alumnos);
                
            }else{
                //Mensaje de error
            }
        }else{
            //Mesaje de error
        }
    } catch (IOException ex) {
                    Logger.getLogger(CargaMasiva.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalStateException ex) {
                    Logger.getLogger(CargaMasiva.class.getName()).log(Level.SEVERE, null, ex);
                }
        //isExcel
        //isEmpty
        //Validar Informacion
        //Validar Campos
        //Tipos de datos en los campos
        
        return "redirect" ;
    }
    
    
    public List<Alumno> LeerExcel(MultipartFile excelFile) throws IOException{
        XSSFWorkbook workBook = new XSSFWorkbook(excelFile.getInputStream());
        Sheet sheet = workBook.getSheetAt(0);
        List<Alumno> alumnos = new ArrayList<>();
        for(Row row : sheet){
            Alumno alumno = new Alumno();
            alumno.setNombre(row.getCell(0).toString());
            alumno.setApellido(row.getCell(1).toString());
            alumno.setSemestre(new Semestre());
            alumno.getSemestre().setIdSemestre(Integer.parseInt(row.getCell(5).toString()));
            
            alumnos.add(alumno);
        }
        return alumnos;
        //Filas 4
        //Columnas 4
        //Celdas  3 //DATOS
        //Libros  1
        //Hojas  2
    }
}
