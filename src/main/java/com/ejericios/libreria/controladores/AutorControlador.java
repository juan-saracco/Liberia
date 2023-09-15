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
        System.out.println("Se esta accediendo al autor con id:" + longId);
        System.out.println("Se encontro el autor:" + autorServicio.buscarUno(longId));

        modelo.put("autor", autorServicio.buscarUno(longId));


        return "autor_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre){
        System.out.println("Esta llegando el string desde el post: " + id);
        Long longId = Long.parseLong(id);
        try {
            autorServicio.modificarAutor(longId, nombre);

        } catch (Exception e) {
            return "autor_modificar.html";
        }
        return "redirect:../lista";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre){
        try {
            autorServicio.crearAutor(nombre);
        } catch (Exception e) {
            return "autor_form.html";
        }
        return "index.html";
    }

    @PostMapping("/borrar/{id}")
    public String borrar(@PathVariable String id) throws Exception {
        Long longId = Long.parseLong(id);

        try {
            autorServicio.borrarAutor(longId);
            return "index.html";
        }catch (Exception e){
            throw new Exception(e);
        }
    }

}
