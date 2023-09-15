package com.ejericios.libreria.controladores;

import com.ejericios.libreria.entidades.Autor;
import com.ejericios.libreria.servicios.AutorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    AutorServicio autorServicio;

    @GetMapping("/registrar")
    public String registrar(){
        return "autor_form.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Autor> autores = autorServicio.obtenerAutores();
        modelo.addAttribute("autores", autores);
        return "autor_lista.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        Long longId = Long.parseLong(id);

        modelo.put("autor", autorServicio.buscarUno(longId));


        return "autor_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre){
        Long longId = Long.parseLong(id);
        try {
            autorServicio.modificarAutor(longId, nombre);

        } catch (Exception e) {
            return "autor_modificar.html";
        }
        return "redirect:../lista";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre,ModelMap modelo) throws Exception {
        try {
            autorServicio.crearAutor(nombre);
            modelo.put("exito", "Se creo el autor " + nombre);
        } catch (Exception e) {
            modelo.put("error", "Error: " +e.getMessage());
            throw new Exception(e);
        }
        return "index.html";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable String id,ModelMap modelo){
        Long longId = Long.parseLong(id);

        try {
            modelo.put("exito", "Se elimino el autor: "+ autorServicio.buscarUno(longId).getNombre());
            autorServicio.borrarAutor(longId);

        }catch (Exception e){
            modelo.put("error", "Error: no se puede borrar un Autor de un libro existente. Borre su libro primero.");
        }
        return "index.html";
    }

}
