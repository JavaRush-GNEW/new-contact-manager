package ua.com.javarush.gnew.contactm.services;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class CloudinaryService {
  private final Cloudinary cloudinary;

  public String upload(MultipartFile file) throws IOException {
    Map<String, Object> options = new HashMap<>();
    options.put("folder", "contacts"); // Optional: organize images in folders
    options.put("resource_type", "image");

    // Convert MultipartFile to something Cloudinary can understand
    Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

    return uploadResult.get("secure_url").toString();
  }

  public String upload(MultipartFile file, Map<String, String> customOptions) throws IOException {
    Map<String, Object> options = new HashMap<>();

    // Convert String map to Object map and add custom options
    if (customOptions != null) {
      options.putAll(customOptions);
    }

    options.put("resource_type", "image");

    // Convert MultipartFile to byte array for Cloudinary
    Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

    return uploadResult.get("secure_url").toString();
  }
}