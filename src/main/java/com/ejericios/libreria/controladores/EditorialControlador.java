package com.ejericios.libreria.controladores;

import com.ejericios.libreria.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar(){
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre){

        try {
            editorialServicio.crearEditorial(nombre);
            return "index.html";
        } catch (Exception e) {
            Logger.getLogger(EditorialControlador.class.getName()).log(Level.SEVERE,null, e);
            return "editorial_form.html";
        }
    }


}
