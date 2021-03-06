package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class FileManager implements IService {

	private final Map<String, File> fileMap = new HashMap<>();
	private final Map<String, Image> imageMap = new HashMap<>();

	public File getFileFromResources(final String fileName) {
		var file = fileMap.get(fileName);
		if(file != null) {
			return file;
		}
		file = getFileFromResource(fileName);
		fileMap.put(fileName, file);
		return file;
	}

	public Image getImageFromResource(final String imageName) {
		var image = imageMap.get(imageName);
		if(image != null) {
			return image;
		}
		image = new Image(getInputStreamFromResource(imageName));
		imageMap.put(imageName, image);
		return image;
	}

	private File getFileFromResource(final String fileName) {
		File f = null;
		try {
			f = File.createTempFile("file", "");
			f.deleteOnExit();
			copyInputStreamToFile(getInputStreamFromResource(fileName), f);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		return f;
	}

	private void copyInputStreamToFile(final InputStream inputStream, final File file) throws IOException {
		try (var outputStream = new FileOutputStream(file, false)) {
			final var bytes = new byte[2048];
			int read;
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		}
	}

	private InputStream getInputStreamFromResource(final String fileName) {
		final var classLoader = this.getClass().getClassLoader();
		final var inputStream = classLoader.getResourceAsStream(fileName);
		if (inputStream == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return inputStream;
		}
	}
}