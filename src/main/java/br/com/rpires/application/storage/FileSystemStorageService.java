/**
 * 
 */
package br.com.rpires.application.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.rpires.application.service.LeitorCorrida;
import br.com.rpires.application.utils.DateUtils;

/**
 * @author Rodrigo Pires
 *
 */
@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    
    @Autowired
    private LeitorCorrida leitorCorridas;
    
    @Autowired
	private DateUtils dateUtils;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                .filter(path -> !path.equals(this.rootLocation))
                .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

	@Override
	public void storeCorridas(MultipartFile file) {
		Date data = Date.from(Instant.now());
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		String[] names = filename.split("\\.");
		String novoNome =  names[0] + "-" + formatter(data, "dd-MM-yyyy-HH_mm_ss") + "." + names[1];
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + novoNome);
            }
            if (novoNome.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + novoNome);
            }
            
            InputStream is = leitorCorridas.lerRetornarStream(file.getInputStream());
            
            Files.copy(is, this.rootLocation.resolve(novoNome),
                    StandardCopyOption.REPLACE_EXISTING);
            
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + novoNome, e);
        }
		
	}
	
	public String formatter(Date data, String formato) {
		return dateUtils.formatter(data, formato);
	}
}
