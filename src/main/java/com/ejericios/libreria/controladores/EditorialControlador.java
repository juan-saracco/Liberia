package com.ejericios.libreria.controladores;

import com.ejericios.libreria.entidades.Editorial;
import com.ejericios.libreria.servicios.EditorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Editorial> editoriales = editorialServicio.obtenerEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        return "editorial_lista.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        Long longId = Long.parseLong(id);

        modelo.put("editorial", editorialServicio.buscarUno(longId));

        return "editorial_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        Long longId = Long.parseLong(id);
        List<Editorial> editoriales = editorialServicio.obtenerEditoriales();
        modelo.addAttribute("editoriales", editoriales);

        try {
            modelo.put("exito", "Se modifico la Editorial: " + nombre);

            editorialServicio.modificarEditorial(longId, nombre);


        }catch (Exception e){
            modelo.put("error", "Error: " +e.getMessage());

        }

        return "editorial_lista.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo){
        List<Editorial> editoriales = editorialServicio.obtenerEditoriales();
        modelo.addAttribute("editoriales", editoriales);

        try {
            modelo.put("exito", "Se creo la Editorial: " + nombre);
            editorialServicio.crearEditorial(nombre);
        } catch (Exception e) {
            modelo.put("error", "Error: " +e.getMessage());
        }

        return "index.html";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable String id, ModelMap modelo){
        Long longId = Long.parseLong(id);

        try {
            modelo.put("exito", "Se elimino la Editorial: "+ editorialServicio.buscarUno(longId).getNombre());
            editorialServicio.borrar(longId);

        }catch (Exception e){
            modelo.put("error", "Error: no se puede borrar una Editorial de un libro existente. Borre su libro primero.");
        }
        return "index.html";
    }

}
