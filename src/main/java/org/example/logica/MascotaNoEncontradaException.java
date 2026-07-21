package org.example.logica;
/**
 * Excepción lanzada cuando se intenta buscar una mascota y esta
 * no se encuentra registrada en el inventario o sistema.
 *
 * @author Valentina
 */
public class MascotaNoEncontradaException extends RuntimeException {
    /**
     * Construye una nueva excepción con el mensaje descriptivo especificado.
     *
     * @param message Mensaje detallando la causa de la excepción
     */
    public MascotaNoEncontradaException(String message) {
        super(message);
    }
}
