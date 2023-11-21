package com.practice_springboot.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UploadFileServiceImpl implements IUploadFileService{
	private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);
	private final static String DIR_UPLOAD = "uploads";

	
	@Override
	public Resource load(String namePhoto) throws MalformedURLException {
		Path routeFile = getPath(namePhoto);
		log.info(routeFile.toString());		
		Resource resourse = new UrlResource(routeFile.toUri());;

		if(!resourse.exists() && !resourse.isReadable()) {
			routeFile = Paths.get(
				"src/main/resources/static/images")
				.resolve("no-usuario.png").toAbsolutePath();
			
			resourse = new UrlResource(routeFile.toUri());
			log.error("Error no se pudo cargar la imagen: " + namePhoto);
		}
		return resourse;
	}

	
	@Override
	public String copy(MultipartFile file) throws IOException {
		String namePhoto = UUID.randomUUID().toString()+"_"+
				file.getOriginalFilename().replace(" ", "");
		
		Path routeFile = getPath(namePhoto);
		log.info(routeFile.toString());
		Files.copy(file.getInputStream(), routeFile);
		return namePhoto;
	}

	
	@Override
	public boolean delete(String namePhoto) {
		if(namePhoto !=null && namePhoto.length() >0) {
			Path rutaFotoAnterior = Paths.get("uploads").resolve(namePhoto).toAbsolutePath();
			File fileLastPhoto = rutaFotoAnterior.toFile();
			if(fileLastPhoto.exists() && fileLastPhoto.canRead()) {
				fileLastPhoto.delete();
				return true;
			}
		}
		return false;
	}

	
	@Override
	public Path getPath(String namePhoto) {
		return Paths.get(DIR_UPLOAD).resolve(namePhoto).toAbsolutePath();
	}

}
