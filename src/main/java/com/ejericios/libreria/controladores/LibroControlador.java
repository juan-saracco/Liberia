package com.ejericios.libreria.controladores;

import com.ejericios.libreria.servicios.AutorServicio;
import com.ejericios.libreria.servicios.EditorialServicio;
import com.ejericios.libreria.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    LibroServicio libroServicio;

    @Autowired
    EditorialServicio editorialServicio;

    @Autowired
    AutorServicio autorServicio;


    @GetMapping("/registrar")
    public String registrar(){
        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,  @RequestParam(required = false) Integer anio, @RequestParam(required = false) Integer ejemplares, @RequestParam(required = false) Long idEditorial, @RequestParam(required = false) Long idAutor){
        try {
            libroServicio.crearLibro(isbn, titulo, anio, ejemplares, idEditorial, idAutor);
            return "index.html";
        } catch (Exception e) {
            Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE,null, e);
            return "libro_form.html";
        }
    }


}
