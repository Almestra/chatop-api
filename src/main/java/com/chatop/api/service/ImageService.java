package com.chatop.api.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chatop.api.config.ImageProperties;
import com.chatop.api.exception.BadRequestException;
import com.chatop.api.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * Manages the rental pictures, stored in the folder set by {@link ImageProperties}.
 */
@Service
@Slf4j
public class ImageService {

    private static final Map<String, String> EXTENSIONS = Map.of(
            "image/jpeg", ".jpg",
            "image/png", ".png",
            "image/webp", ".webp");

    private final Path directory;
    private final String baseUrl;

    public ImageService(ImageProperties imageProperties) {
        this.directory = Path.of(imageProperties.directory()).toAbsolutePath().normalize();
        this.baseUrl = imageProperties.baseUrl();
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

    /**
     * Stores a picture under a generated name and returns its URL.
     *
     * <p>The extension comes from the type of the file, never from its original name,
     * which could lead out of the folder or overwrite another picture.
     *
     * @param picture the picture sent with the form
     * @return the absolute URL of the stored picture
     * @throws BadRequestException if the picture is missing, or is not a JPEG, PNG or WebP image
     */
    public String store(MultipartFile picture) {
        if (picture == null || picture.isEmpty()) {
            throw new BadRequestException("Picture is required");
        }

        String contentType = picture.getContentType();

        if (contentType == null || !EXTENSIONS.containsKey(contentType)) {
            throw new BadRequestException("Picture must be a JPEG, PNG or WebP image");
        }

        String filename = UUID.randomUUID() + EXTENSIONS.get(contentType);

        try {
            Files.createDirectories(directory);
            picture.transferTo(directory.resolve(filename));
        } catch (IOException e) {
            throw new UncheckedIOException("Could not store the picture", e);
        }

        return baseUrl + "/" + filename;
    }

    /**
     * Deletes a stored picture, for example when its rental could not be saved.
     *
     * <p>A failure is only logged, so that it does not hide the error being handled.
     *
     * @param url the URL returned by {@link #store(MultipartFile)}
     */
    public void delete(String url) {
        String filename = url.substring(url.lastIndexOf('/') + 1);

        try {
            Files.deleteIfExists(directory.resolve(filename));
        } catch (IOException e) {
            log.warn("Could not delete the picture {}", filename, e);
        }
    }

}
