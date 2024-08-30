/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.Controller;

import com.Digis01.ProgramacionNCapasJulio24.DAO.AlumnoDAOImplementation;
import com.Digis01.ProgramacionNCapasJulio24.ML.Alumno;
import com.Digis01.ProgramacionNCapasJulio24.ML.ErrorExcel;
import com.Digis01.ProgramacionNCapasJulio24.ML.Result;
import com.Digis01.ProgramacionNCapasJulio24.ML.Semestre;
import jakarta.servlet.http.HttpSession;
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
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String Get() {
        //model.addAtrribute("errores" null) //NO USAR DE PREFERENCIA
        return "CargaMasivaGet";
    }

    @PostMapping("/txt")
    public String CargaTxt(@RequestParam MultipartFile txtFile) {
        // String path = "C:\\Users\\digis\\OneDrive\\Documentos\\Jose Ramon Becerra Perez\\Proyectos\\Java\\ProgramacionNCapasJulio24\\CargaMasiva.txt";
        try {
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
            while ((linea = bufferedReader.readLine()) != null) {
                String[] datos = linea.split("\\|");
                Alumno alumno = new Alumno();
                alumno.setNombre(datos[0]);
                alumno.setApellido(datos[1]);
                alumno.setSemestre(new Semestre());
                alumno.getSemestre().setIdSemestre(Integer.parseInt(datos[5]));

                alumnoDAOImplementation.AddJPA(alumno);
            }
        } catch (IOException ex) {

        } catch (Exception ex) {

        }
        return "redirect";
    }

    @PostMapping("/excel")
    public String CargaExcel(Model model, @RequestParam MultipartFile excelFile, HttpSession session) {
        try {
            if (excelFile != null && !excelFile.isEmpty()) {

                String extension = StringUtils.getFilenameExtension(excelFile.getOriginalFilename());
                if (extension.equals("xlsx")) {
                    //Copia del excel //Historico // Validar/Seguridad/Respaldo
                    String root = System.getProperty("user.dir"); //Raiz del proyecto
                    String path = "/src/main/resources/static/ArchivosExcel/";
                    String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                    //C:User......../src/res/sta/archEx/20240827170112CargaMasiva.xlsx
                    String nombreArchivo = excelFile.getOriginalFilename(); //Extension
                    String pathFinal = root + path + fecha + nombreArchivo;

                    excelFile.transferTo(new File(pathFinal)); //Guarda un archivo copia

                    List<Alumno> alumnos = LeerExcel(pathFinal);
                    List<ErrorExcel> errores = ValidarDatos(alumnos);  // Mostrar errores en caso de existir
                    if (errores.size() > 0) { //Hay Errores
                        model.addAttribute("ListaErrores", errores);
                        return "CargaMasivaGet";
                    } else {
                        model.addAttribute("Mensaje", "El archivo es correcto, esta listo para subir");
                        //PATHFINAL  --> Almacenar en una session
                        session.setAttribute("path", pathFinal);
                        return "CargaMasivaGet";
                    }

                } else {
                    //Mensaje de error
                }
            } else {
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

        return "redirect";
    }

    @PostMapping("/excel/Subir")
    public String SubirExcel(HttpSession session) {
        String pathFinal = session.getAttribute("path").toString();
        try {
            List<Alumno> alumnos = LeerExcel(pathFinal);
            for (Alumno alumno : alumnos) {
                Result result =alumnoDAOImplementation.AddJPA(alumno);
                if (!result.correct) {
                    //Generar una lista de Errores
                    //Generar un TXT - Log
                    //Descarga automatica
                }
            }
        } catch (Exception ex) {
           //Manejo de la ex
        }
        //session.setAttribute("path", null);
        session.removeAttribute("path");
        return "redirect";
    }

    public List<Alumno> LeerExcel(String pathExcel) {
        List<Alumno> alumnos = new ArrayList<>();
        try ( XSSFWorkbook workBook = new XSSFWorkbook(pathExcel)) {
            Sheet sheet = workBook.getSheetAt(0);

            boolean isPrimeraFila = true;
            for (Row row : sheet) {  //Lectura es de inicio a fin
                // if(row[0] ) continue;
                //if(row.getCell(0).toString().equals("Nombre")) continue;
                if (isPrimeraFila) {
                    isPrimeraFila = false;
                } else {
                    Alumno alumno = new Alumno();
                    alumno.setNombre(row.getCell(0).toString());
                    alumno.setApellido(row.getCell(1).toString());
                    alumno.setSemestre(new Semestre());
                    //Sin apache
//            if(!row.getCell(5).toString().equals("")){
//            String idSemestre = row.getCell(5).toString();
//            double numeroDouble = Double.parseDouble(idSemestre);
//            int numeroInt = (int)numeroDouble;
//            alumno.getSemestre().setIdSemestre(numeroInt);
//            }
//          //Con libreria de Apache
                    String idSemestre = row.getCell(5).toString();
                    double idSemestreDouble = NumberUtils.toDouble(idSemestre, 0); //Ex(X) default
                    int idSemestreInt = (int) idSemestreDouble;
                    alumno.getSemestre().setIdSemestre(idSemestreInt);
                    alumnos.add(alumno);
                }
            }
        } catch (Exception ex) {

        }
        return alumnos;

        //Filas 4
        //Columnas 4
        //Celdas  3 //DATOS
        //Libros  1
        //Hojas  2
    }

    public List<ErrorExcel> ValidarDatos(List<Alumno> alumnos) {
        List<ErrorExcel> erroresExcel = new ArrayList<ErrorExcel>();
        int fila = 2;
        for (Alumno alumnoAValidar : alumnos) {
            String errores = "";
            if (alumnoAValidar.getNombre().equals("")) { //STRING
                errores += "Falta el nombre, ";
            }
            //(condicion) ? true : false
            errores += (alumnoAValidar.getApellido().equals("")) ? "Falta Apellido, " : "";

            if (alumnoAValidar.getSemestre().getIdSemestre() == 0) {
                errores += "Falta el IdSemestre, ";
            }

            if (!errores.equals("")) {
                ErrorExcel errorExcel = new ErrorExcel(fila, errores);
                erroresExcel.add(errorExcel);
            }
            fila++;
        }
        return erroresExcel;
    }
}
