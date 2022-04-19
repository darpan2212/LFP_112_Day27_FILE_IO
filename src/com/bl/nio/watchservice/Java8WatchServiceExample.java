package com.bl.nio.watchservice;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class Java8WatchServiceExample {

	private WatchService watcher;
	private Map<WatchKey, Path> dirWatchers;

	public Java8WatchServiceExample(Path dir)
			throws IOException {
		watcher = FileSystems.getDefault()
				.newWatchService();
		dirWatchers = new HashMap<>();
		scanAndRegisterDirectories(dir);
	}

	private void scanAndRegisterDirectories(Path start)
			throws IOException {

		Files.walkFileTree(start,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
							Path dir,
							BasicFileAttributes attrs)
							throws IOException {
						System.out.println(dir);
						registerDirWatcher(dir);
						return FileVisitResult.CONTINUE;
					}

				});

	}

	public void registerDirWatcher(Path dir)
			throws IOException {
		WatchKey watchKey = dir.register(watcher,
				ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		dirWatchers.put(watchKey, dir);
	}

	public void processEvents() {

		WatchKey key;
		while (true) {
			try {
				key = watcher.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}

			Path dir = dirWatchers.get(key);
			if (dir == null)
				continue;

			for (WatchEvent<?> event : key.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				Path name = ((WatchEvent<Path>) event)
						.context();
				Path child = dir.resolve(name);

				System.out.println(event.kind().name()
						+ "=>" + child + "\n");

				if (kind == ENTRY_CREATE) {
					if (Files.isDirectory(child)) {
						try {
							scanAndRegisterDirectories(
									child);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else if (kind == ENTRY_DELETE) {
					if (Files.isDirectory(child)) {
						dirWatchers.remove(key);
					}
				}
			}

			if (!key.reset()) {
				dirWatchers.remove(key);
				if (dirWatchers.isEmpty())
					break;
			}
		}
	}
}