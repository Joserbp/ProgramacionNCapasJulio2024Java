/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.Digis01.ProgramacionNCapasJulio24.Controller;

import com.Digis01.ProgramacionNCapasJulio24.DAO.AlumnoDAOImplementation;
import com.Digis01.ProgramacionNCapasJulio24.DAO.DireccionDAOImplementation;
import com.Digis01.ProgramacionNCapasJulio24.DAO.EstadoDAOImplementation;
import com.Digis01.ProgramacionNCapasJulio24.DAO.PaisDAOImplementation;
import com.Digis01.ProgramacionNCapasJulio24.DAO.SemestreDAOImplementation;
import com.Digis01.ProgramacionNCapasJulio24.ML.Alumno;
import com.Digis01.ProgramacionNCapasJulio24.ML.Estado;
import com.Digis01.ProgramacionNCapasJulio24.ML.Pais;
import com.Digis01.ProgramacionNCapasJulio24.ML.Result;
import com.Digis01.ProgramacionNCapasJulio24.ML.Semestre;
import java.io.IOException;
import java.util.List;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author digis
 */
@Controller 
@RequestMapping("/Alumno")
public class AlumnoController {
    
    @Autowired
    private AlumnoDAOImplementation alumnoDAOImplementation;
    @Autowired
    private SemestreDAOImplementation semestreDAOImplementation;
    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;
    @Autowired
    private PaisDAOImplementation paisDAOImplementation;
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;
            
            
    @GetMapping("/GetAll")
    public String GetAll(Model model){
        //Object datos = alumnoDAOImplementation.GetAll();
        Result result = alumnoDAOImplementation.GetAllSP();
        model.addAttribute("Alumnos", (List<Alumno>)result.object);
        return "AlumnoGetAll";
    }
    
    @GetMapping("/Form/{idAlumno}") //Mostar el form
    public String Form(@PathVariable int idAlumno,Model model){
        
        if(idAlumno == 0){ //ADD
            Alumno alumno = new Alumno();
            alumno.setSemestre(new Semestre());      
            model.addAttribute("Alumno", alumno);     
        }else{  //Update
            Result alumnoActualizar = alumnoDAOImplementation.GetByIdJPA(idAlumno);
        //    List<Alumno> alumnos = (List<Alumno>)alumnoActualizar;
            Alumno alumno = (Alumno)alumnoActualizar.object;        
            model.addAttribute("Alumno", alumno);
        }
        Object datosSemestre = semestreDAOImplementation.GetAll();
        Result resultPaises = paisDAOImplementation.GetAll();
        model.addAttribute("Paises", (List<Pais>) resultPaises.object );
        model.addAttribute("Semestres", (List<Semestre>)datosSemestre);
        return "AlumnoForm";
    }
    
    
    @PostMapping("/Form")
    public String Form(@ModelAttribute Alumno alumno, @RequestParam("fileImagen") MultipartFile fileImage){ 
        //Validar el ID
        //Id = 0 ADD
        //ID != 0 UPDATE
        //int rowAffetted = alumnoDAOImplementation.Add(alumno);
       
        if(fileImage.getName() != ""){
            try{
            byte[] imagenBytes = fileImage.getBytes();
            String imageBase64 = Base64.encodeBase64String(imagenBytes);//Base64 encode  encodeString 
            alumno.setImagen(imageBase64); 
            }
            catch(IOException ex){
                //Codigo pendiente
            }
        }
        
        
              
        //Result result = alumnoDAOImplementation.AddSP(alumno);
        Result result = alumnoDAOImplementation.AddJPA(alumno);
                //Como recuperar el IdAlumno insertado   //SP
                //result.Object == IDRecuperado
        int idAlumnoRecuperado = Integer.parseInt(result.object.toString());
        Result resultDireccion = direccionDAOImplementation.Add(alumno.getDireccion(), idAlumnoRecuperado);
        int stop;
        return "redirect:/Alumno/GetAll";
    }
    //Recibir la informacion de la vista
    
    @GetMapping("/EstadoGetByIdPais")
    @ResponseBody
    public List<Estado> EstadoGetByIdPais(@RequestParam("IdPais") int IdPais){
        Result result = estadoDAOImplementation.GetByIdPais(IdPais);
        return (List<Estado>)result.object;
    }
    
    @GetMapping("/ChangeStatus")
    @ResponseBody
    public Result ChangeStatus(@RequestParam("IdAlumno")int IdAlumno, @RequestParam("Status")boolean Status){

        int newStatusTernario = (Status) ? 1 : 0;
        // Operador ternario es un if/else de una sola linea
        // (condicion) ? verdadero : falso  //RETORNAR
        
        Result result = alumnoDAOImplementation.ChangeStatus(IdAlumno, newStatusTernario);
        return result;
    }
}
    

