package ua.com.javarush.gnew.contactm.controller.rest.img;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.com.javarush.gnew.contactm.services.CloudinaryService;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

  private final CloudinaryService imageService;

  @PostMapping("/upload")
  public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
    try {
      String imageUrl = imageService.upload(file, null);
      return ResponseEntity.status(HttpStatus.CREATED).body(imageUrl);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed: " + e.getMessage());
    }
  }

}
