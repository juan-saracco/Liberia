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
            editorialServicio.modificarEditorial(longId, nombre);
            return "editorial_lista.html";

        }catch (Exception e){
            modelo.put("error", e);
            return "editorial_modificar.html";
        }

    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo){

        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("Exito", "La editorial se registro correctamente");
        } catch (Exception e) {
            modelo.put("Error", e);
            return "editorial_form.html";
        }
        return "index.html";

    }


}
