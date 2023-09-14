package com.ejericios.libreria.servicios;

import com.ejericios.libreria.entidades.Autor;
import com.ejericios.libreria.entidades.Editorial;
import com.ejericios.libreria.entidades.Libro;
import com.ejericios.libreria.repositorios.AutorRepositorio;
import com.ejericios.libreria.repositorios.EditorialRepositorio;
import com.ejericios.libreria.repositorios.LibroRepositorio;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class LibroServicio {

    @Autowired
    LibroRepositorio libroRepositorio;
    @Autowired
    AutorRepositorio autorRepositorio;
    @Autowired
    EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Long idEditorial, Long idAutor) throws Exception{

        validar(isbn, titulo, anio, ejemplares, idEditorial, idAutor);

        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);

        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        if (respuestaEditorial == null || respuestaEditorial.isEmpty()){
            throw new Exception("No se encontro la editorial de id: "+ idEditorial);
        }else {
            Editorial editorial = respuestaEditorial.get();
            libro.setEditorial(editorial);
        }

        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);

        if(respuestaAutor == null || respuestaAutor.isEmpty()){
            throw new Exception("No se encontro el autor de id: "+ idAutor);
        }else {
            Autor autor = respuestaAutor.get();
            libro.setAutor(autor);
        }

        libroRepositorio.save(libro);
    }

    @Transactional
    public void modificarLibro(Long id, Long isbn, String titulo, Integer anio, Integer ejemplares, Long idEditorial, Long idAutor) throws Exception{
        //System.out.println("se accedio al metodo modificar");
        validar(isbn, titulo, anio, ejemplares, idEditorial, idAutor);

        Optional<Libro> respuestaLibro = libroRepositorio.findById(id);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();

        if (respuestaEditorial == null || respuestaEditorial.isEmpty()){
            throw new Exception("No se encontro la editorial con id: " + id);
        }else {
            editorial = respuestaEditorial.get();
        }

        if (respuestaAutor == null || respuestaAutor.isEmpty()){
            throw new Exception("No se encontro el autor con id: " + id);
        }else {
            autor = respuestaAutor.get();
        }


        if (respuestaLibro == null || respuestaLibro.isEmpty()){
            throw new Exception("No se encontro el libro con id: " + id);
        }else {
            Libro libro = respuestaLibro.get();
            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEditorial(editorial);
            libro.setAutor(autor);
            libroRepositorio.save(libro);
        }
    }

    public List<Libro> obtenerLibros(){
        return libroRepositorio.findAll();
    }

    public Libro obtenerUno(Long id){
        //System.out.println("se obtuvo el libro " + libroRepositorio.getOne(id));
        return libroRepositorio.getOne(id);
    }

    private void validar(Long isbn, String titulo, Integer anio, Integer ejemplares, Long idEditorial, Long idAutor) throws Exception{


        if(isbn == null){
            throw new Exception("El isbn no puede ser nulo");
        }

        if ((titulo == null || titulo.isEmpty())){
            throw new Exception("El titulo no puede ser nulo o estar vacio");
        }

        if(anio == null){
            throw new Exception("El año no puede ser nulo");
        }

        if(ejemplares == null){
            throw new Exception("Los ejemplares no pueden ser nulos");
        }

        if(idEditorial == null){
            throw new Exception("La editorial no peude ser nula");
        }

        if(idAutor == null){
            throw new Exception("El autor no puede ser nulo");
        }

    }


}
