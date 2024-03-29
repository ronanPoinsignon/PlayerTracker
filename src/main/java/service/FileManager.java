package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;
import modele.save.DataObject;

public class FileManager implements IService {

	DictionnaireService dictionnaire;

	private final Map<String, File> fileMap = new HashMap<>();
	private final Map<String, Image> imageMap = new HashMap<>();

	public File getApplicationFolderFile() {
		return new File(this.getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath()).getParentFile();
	}

	public File getOrCreateFile(final String fileName) {
		return new File(getApplicationFolderFile().getAbsolutePath() + File.separator + fileName);
	}

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
			throw new IllegalArgumentException(dictionnaire.getText("getInputStreamFromResourceFileNotFound").getValue() + " : " + fileName);
		} else {
			return inputStream;
		}
	}

	public void writeInto(final File file, final String content) throws IOException {
		if(!file.exists()) {
			file.createNewFile();
		}
		Files.write(Paths.get(file.toURI()), content.getBytes(StandardCharsets.UTF_8));
	}

	public void writeInto(final File file, final Serializable object) throws IOException {
		try (var oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(object);
		}
	}

	public <T extends Serializable> void writeInto(final File file, final List<T> elements) throws IOException {
		try (var oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeInt(elements.size());

			for(final T element : elements) {
				oos.writeObject(element);
			}
		}
	}

	public String readFile(final File file) throws IOException {
		return Files.readString(Paths.get(file.toURI()), StandardCharsets.UTF_8);
	}

	@SuppressWarnings("unchecked")
	public <T extends Serializable> T read(final File file) throws ClassNotFoundException, IOException {
		try (var ois = new ObjectInputStream(new FileInputStream(file))) {
			return (T) ois.readObject();
		}
	}

	public DataObject readSave(final File file) throws ClassNotFoundException, IOException {
		return read(file);
	}

	@Override
	public void init() {
		dictionnaire = ServiceManager.getInstance(DictionnaireService.class);
	}
}