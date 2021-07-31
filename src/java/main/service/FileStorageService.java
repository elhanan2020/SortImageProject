package main.service;
import main.data.FileMetaData;
import main.exeption.FileStorageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

public interface FileStorageService {

    FileMetaData store(MultipartFile file ,String dirFile) throws FileStorageException;
    List<Path> getAllFiles();
    FileMetaData getFile(String fileName) throws FileNotFoundException;
}