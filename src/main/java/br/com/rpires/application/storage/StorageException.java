/**
 * 
 */
package br.com.rpires.application.storage;

/**
 * @author Rodrigo Pires
 *
 */
public class StorageException extends RuntimeException {

	private static final long serialVersionUID = -187169143879406496L;

	public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
