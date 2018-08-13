/**
 * 
 */
package br.com.rpires.application.storage;

/**
 * @author Rodrigo Pires
 *
 */
public class StorageFileNotFoundException extends StorageException {

	private static final long serialVersionUID = -6306939546906754738L;

	public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
