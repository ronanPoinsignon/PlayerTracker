package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

	public static File getFileFromResource(String fileName) throws IOException {
		File f = new File("file");
		Utils.copyInputStreamToFile(Utils.getInputStreamFromResource(fileName), f);
		return f;
	}

	public static InputStream getInputStreamFromResource(String fileName) throws IOException {
		ClassLoader classLoader = Utils.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(fileName);
		if (inputStream == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return inputStream;
		}
	}

	public static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
		try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
			byte[] bytes = new byte[2048];
			int read;
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		}
	}
}
