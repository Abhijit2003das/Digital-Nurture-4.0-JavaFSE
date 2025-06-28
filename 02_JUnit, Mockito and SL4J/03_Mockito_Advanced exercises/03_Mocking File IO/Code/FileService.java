package com.example.mock_file_io;

public class FileService {
	private FileReader reader;
    private FileWriter writer;

    public FileService(FileReader reader, FileWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public String processFile() {
        String content = reader.read();
        String processed = "Processed " + content;
        writer.write(processed); // Writing is side effect
        return processed;
    }

}
