package com.bl.employee.payroll.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import org.junit.Test;

public class NIOFileApiTest {

	public static String DIR_D = "D:";

	public static String PLAY_WITH_NIO = "TempDir";

	@Test
	public void testFileExist() throws IOException {
		Path dirPath = Paths.get(DIR_D);

		assertTrue(Files.exists(dirPath));

		Path playPath = Paths
				.get(dirPath + "/" + PLAY_WITH_NIO);
		if (Files.notExists(playPath)) {
			Files.createDirectory(playPath);
		}

		IntStream.range(1, 10).forEach(n -> {
			Path tempPath = Paths
					.get(playPath + "/temp" + n + ".txt");

			if (Files.notExists(tempPath)) {
				try {
					Files.createFile(tempPath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		IntStream.range(1, 10).forEach(n -> {
			Path tempPath = Paths
					.get(playPath + "/dir" + n);

			if (Files.notExists(tempPath)) {
				try {
					Files.createDirectory(tempPath);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		System.out.println("-------List of Files--------");
		Files.list(playPath)
				.filter(p -> Files.isRegularFile(p))
				.forEach(p -> System.out.println(p));

		System.out.println(
				"------new directory stream-------");
		Files.newDirectoryStream(playPath)
				.forEach(System.out::println);

		System.out.println(
				"------new directory stream with filter-------");
		Files.newDirectoryStream(playPath,
				p -> p.toFile().isFile() && p.getFileName()
						.toString().startsWith("temp"))
				.forEach(p -> System.out.println(p));

		System.out.println("------Not a temp file--------");
		Files.newDirectoryStream(playPath,
				p -> p.toFile().isFile() && !p.getFileName()
						.toString().startsWith("temp"))
				.forEach(p -> {
					try {
						Files.deleteIfExists(p);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});

	}

//	@Test
	public void createDirectory() {
		Path path = Paths.get("data");

		try {
			if (Files.exists(path)) {
				Path filePath = Paths.get("data/abc.txt");
				if (!Files.exists(filePath)) {
					Files.createFile(filePath);
				}
			} else {
				Files.createDirectory(path);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}