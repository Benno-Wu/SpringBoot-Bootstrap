package zust.logistics.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class FileStorage {
    private static final Path path = Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\pic");

    public static String save(MultipartFile file, String id) {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        try {
            Files.copy(file.getInputStream(), path.resolve(id + suffixName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return "/pic/" + id + suffixName;
    }
}
