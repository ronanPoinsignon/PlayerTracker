package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class FileManager implements IService {

	private Map<String, File> fileMap = new HashMap<>();
	private Map<String, Image> imageMap = new HashMap<>();

	public File getFileFromResources(String fileName) {
		var file = fileMap.get(fileName);
		if(file != null) {
			return file;
		}
		file = getFileFromResource(fileName);
		fileMap.put(fileName, file);
		return file;
	}

	public Image getImageFromResource(String imageName) {
		var image = imageMap.get(imageName);
		if(image != null) {
			return image;
		}
		image = new Image(getInputStreamFromResource(imageName));
		imageMap.put(imageName, image);
		return image;
	}

	private File getFileFromResource(String fileName) {
		File f = null;
		try {
			f = File.createTempFile("file", "");
			f.deleteOnExit();
			copyInputStreamToFile(getInputStreamFromResource(fileName), f);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return f;
	}

	private void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
		try (var outputStream = new FileOutputStream(file, false)) {
			var bytes = new byte[2048];
			int read;
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		}
	}

	private InputStream getInputStreamFromResource(String fileName) {
		var classLoader = this.getClass().getClassLoader();
		var inputStream = classLoader.getResourceAsStream(fileName);
		if (inputStream == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return inputStream;
		}
	}
}
