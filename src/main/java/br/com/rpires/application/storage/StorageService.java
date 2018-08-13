/**
 * 
 */
package br.com.rpires.application.storage;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Rodrigo Pires
 *
 */
public interface StorageService {

	void init();

    void store(MultipartFile file);
    
    void storeCorridas(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
}
