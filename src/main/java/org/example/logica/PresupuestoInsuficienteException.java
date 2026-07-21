package org.example.logica;
/**
 * Excepción lanzada cuando una operación de compra o transacción excede
 * el presupuesto monetario disponible en la tienda.
 *
 * @author Valentina
 */
public class PresupuestoInsuficienteException extends RuntimeException {
    /**
     * Construye una nueva excepción con el mensaje descriptivo especificado.
     *
     * @param message Mensaje detallando la causa de la excepción
     */
    public PresupuestoInsuficienteException(String message) {
        super(message);
    }
}
