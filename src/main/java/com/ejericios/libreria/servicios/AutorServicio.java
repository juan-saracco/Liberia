package com.ejericios.libreria.servicios;

import com.ejericios.libreria.entidades.Autor;
import com.ejericios.libreria.repositorios.AutorRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws Exception{

        validar(nombre);

        Autor autor = new Autor();

        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }

    @Transactional
    public void modificarAutor(Long id, String nombre) throws Exception{

        validar(nombre);

        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if(respuesta.isPresent()){
            Autor autor = respuesta.get();

            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }else{
            throw new Exception("No se encontro el autor");
        }
    }

    @Transactional
    public void borrarAutor(Long id) throws Exception{
        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autorRepositorio.delete(autor);
        }else{
            throw new Exception("No se encontro el autor");
        }
    }

    public Autor buscarUno(Long id){
        //System.out.println("Autor encontrado en buscar uno: " + autorRepositorio.getOne(id));
        return autorRepositorio.getOne(id);
    }

    public List<Autor> obtenerAutores(){
        return autorRepositorio.findAll();
    }

    private void validar(String nombre) throws Exception{

        if (nombre == null || nombre.isEmpty()){
            throw new Exception("El nombre del autor no puede ser nulo");
        }
    }

}
