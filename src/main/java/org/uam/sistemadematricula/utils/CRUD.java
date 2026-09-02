package org.uam.sistemadematricula.utils;

import java.util.List;

public interface CRUD<T> {

    void registrar(T objeto);

    List<T> mostrar();

    void actualizar(int index, T objeto);

    void eliminar(int index);
}
