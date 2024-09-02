package com.shivam.elearn.app.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shivam.elearn.app.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String save(MultipartFile file, String outputPath, String filename) throws IOException {

		// /files/abc.png

		// logic
		Path path = Paths.get(outputPath);
		System.out.println("Path>>>>>{}"+path);
		// create output folder if not exists
		Files.createDirectories(path);

		// video/audio/image processing........

		// ffmpeg
		// code insert karna apne app: process

		// path ko join
		Path filePath = Paths.get(path.toString(), file.getOriginalFilename());
		System.out.println("filePath>>>>>{}"+filePath);
		// file writes
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		return filePath.toString();
	}

}
