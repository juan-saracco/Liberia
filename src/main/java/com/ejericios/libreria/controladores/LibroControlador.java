package com.ejericios.libreria.controladores;

import com.ejericios.libreria.entidades.Autor;
import com.ejericios.libreria.entidades.Editorial;
import com.ejericios.libreria.entidades.Libro;
import com.ejericios.libreria.servicios.AutorServicio;
import com.ejericios.libreria.servicios.EditorialServicio;
import com.ejericios.libreria.servicios.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public String registrar(ModelMap modelo) {
        List<Autor> autores = autorServicio.obtenerAutores();
        List<Editorial> editoriales = editorialServicio.obtenerEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_form.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Libro> libros = libroServicio.obtenerLibros();
        modelo.addAttribute("libros", libros);
        return "libro_lista";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo, @RequestParam(required = false) Integer anio, @RequestParam(required = false) Integer ejemplares, @RequestParam(required = false) Long idEditorial, @RequestParam(required = false) Long idAutor, ModelMap model) throws Exception {
        try {
            libroServicio.crearLibro(isbn, titulo, anio, ejemplares, idEditorial, idAutor);
            model.put("Exito", "El libro fue cargado correctamente");
        } catch (Exception e) {
            throw new Exception(e);
        }
        return "index.html";
    }


    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){

        Long longId = Long.parseLong(id);
        modelo.put("libro", libroServicio.obtenerUno(longId));
        libroServicio.obtenerUno(longId);


        List<Autor> autores = autorServicio.obtenerAutores();
        List<Editorial> editoriales = editorialServicio.obtenerEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String isbn, String titulo, String anio, String ejemplares, String idEditorial, String idAutor, ModelMap modelo) throws Exception {

        Long longId = Long.parseLong(id);
        Long longIsbn = Long.parseLong(isbn);
        Integer intAnio = Integer.parseInt(anio);
        Integer intEjemplares = Integer.parseInt(ejemplares);
        Long longIdEditorial = Long.parseLong(idEditorial);
        Long longIdAutor = Long.parseLong(idAutor);

        try {
            libroServicio.modificarLibro(longId, longIsbn,titulo ,intAnio, intEjemplares, longIdEditorial, longIdAutor);
            return "redirect:../lista";
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable String id) throws Exception {
        Long longId = Long.parseLong(id);

        try {
            libroServicio.borrar(longId);

        }catch (Exception e){
            throw new Exception(e);
        }

        return "index.html";
    }


}
