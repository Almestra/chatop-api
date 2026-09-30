package com.chatop.api.service;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.chatop.api.config.ImageProperties;
import com.chatop.api.exception.NotFoundException;

/**
 * Manages the rental pictures, stored in the folder set by {@link ImageProperties}.
 */
@Service
public class ImageService {

    private final Path directory;

    public ImageService(ImageProperties imageProperties) {
        this.directory = Path.of(imageProperties.directory()).toAbsolutePath().normalize();
    }

    /**
     * Returns a stored picture.
     *
     * <p>The file name comes from the URL: a name that leads out of the folder,
     * such as {@code ../.env}, is treated as an unknown picture.
     *
     * @param filename the name of the file
     * @return the picture file
     * @throws NotFoundException if the file does not exist or is outside the folder
     */
    public Resource getImage(String filename) {
        Path file = directory.resolve(filename).normalize();

        if (!file.startsWith(directory) || !Files.isRegularFile(file)) {
            throw new NotFoundException("Picture not found");
        }

        return new FileSystemResource(file);
    }

}
