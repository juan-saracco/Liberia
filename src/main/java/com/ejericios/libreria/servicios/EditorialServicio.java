package com.ejericios.libreria.servicios;

import com.ejericios.libreria.entidades.Editorial;
import com.ejericios.libreria.repositorios.EditorialRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialServicio {

    @Autowired
    EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws Exception{

        validar(nombre);

        Editorial editorial = new Editorial();

        editorial.setNombre(nombre);

        editorialRepositorio.save(editorial);
    }

    @Transactional
    public void modificarEditorial(Long id, String nombre) throws  Exception{

        validar(nombre);

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();

            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }else{
            throw new Exception("No se encontro la editorial con id: " + id);
        }

    }

    public List<Editorial> obtenerEditoriales(){
        return editorialRepositorio.findAll();
    }

    public Editorial buscarUno(Long id){
        return editorialRepositorio.getOne(id);
    }

    private void validar(String nombre) throws Exception{

        if (nombre == null || nombre.isEmpty()){
            throw new Exception("El nombre de la editorial no puede ser nulo");
        }
    }

}
