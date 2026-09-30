package com.chatop.api.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.service.ImageService;

/**
 * Picture endpoints of the API.
 *
 * <p>The pictures are public, because the {@code <img>} tags of the front-end
 * never send the token.
 */
@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * Returns a rental picture.
     *
     * <p>The {@code Content-Type} is set from the file extension. Otherwise Spring
     * would choose it from the {@code Accept} header of the request, for example
     * {@code text/html} for a browser tab.
     *
     * @param filename the name of the file
     * @return the picture file, with its media type
     */
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Resource picture = imageService.getImage(filename);
        MediaType mediaType = MediaTypeFactory.getMediaType(picture)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(picture);
    }

}
