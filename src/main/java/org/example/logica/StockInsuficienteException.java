package org.example.logica;
/**
 * Excepción lanzada cuando se intenta adquirir una cantidad de un producto
 * o suministro mayor al stock disponible en el proveedor.
 *
 * @author Valentina
 */
public class StockInsuficienteException extends RuntimeException {
    /**
     * Construye una nueva excepción con el mensaje descriptivo especificado.
     *
     * @param message Mensaje detallando la causa de la excepción
     */
    public StockInsuficienteException(String message) {
        super(message);
    }
}
