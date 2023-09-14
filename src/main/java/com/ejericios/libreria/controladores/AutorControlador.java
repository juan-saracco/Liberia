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
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        System.out.println("Esta llegando el string desde el post: " + id);
        Long longId = Long.parseLong(id);
        try {
            autorServicio.modificarAutor(longId, nombre);

            return "redirect:../lista";
        } catch (Exception e) {
            modelo.put("error", e);
            return "autor_modificar.html";
        }
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo){
        try {
            autorServicio.crearAutor(nombre);
            modelo.put("Exito", "Se registro el autor correctamente");
        } catch (Exception e) {
            modelo.put("Error", e);
            return "autor_form.html";
        }
        return "index.html";
    }

    /*@DeleteMapping("/borrar")
    public String borrar(@RequestParam List<String> listaId, ModelMap modelo){
        //Recorres la lista de id
        for (String stringId : listaId) {
            //Pasas los datos de String a Long que es el tipo de dato de tu autorId
            Long id = Long.parseLong(stringId);
            //Aquí llamas a tu método eliminar con parametro id
            try {
                autorServicio.borrarAutor(id);
                modelo.put("Exito", "Se borro el autor correctamente");

            } catch (Exception e) {
                modelo.put("Error", e);
                return "autor_form.html";
            }
        }
        return "autor_form.html";
    }
*/

}
