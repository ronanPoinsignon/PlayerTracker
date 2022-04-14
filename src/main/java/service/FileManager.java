package service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;
import utils.Utils;

public class FileManager implements IService {

	private Map<String, File> fileMap = new HashMap<>();
	private Map<String, Image> imageMap = new HashMap<>();

	public File getFileFromResources(String fileName) throws IOException {
		File file = fileMap.get(fileName);
		if(file != null) {
			return file;
		}
		file = Utils.getFileFromResource(fileName);
		fileMap.put(fileName, file);
		return file;
	}

	public Image getImageFromResource(String imageName) throws IOException {
		Image image = imageMap.get(imageName);
		if(image != null) {
			return image;
		}
		image = new Image(Utils.getInputStreamFromResource(imageName));
		imageMap.put(imageName, image);
		return image;
	}
}
