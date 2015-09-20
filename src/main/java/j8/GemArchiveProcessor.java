package org.indyscala.streams.j8;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class GemArchiveProcessor {

    private final ObjectMapper mapper;

    public GemArchiveProcessor() {
        mapper = new ObjectMapper();
    }

    public void process(Iterable<String> files) {
        Stream<String> lines = lines(files);
        long count = lines
                .map(line -> parseJson(line))
                .count();
        System.out.println("LINES: " + count);
    }

    private Map parseJson(String json) {
        try {
            return mapper.readValue(json, Map.class);
        } catch (Exception e) {
            // punt on checked exceptions in Java8 map()
            throw new RuntimeException("Failed to parse JSON `" + json + "`", e);
        }
    }

    private Stream<String> lines(Iterable<String> files) {
        Stream<String> lines = Stream.empty();
        for (String file : files) {
            try {
                lines = Stream.concat(lines, asStream(file));
            } catch (IOException e) {
                throw new RuntimeException("Error opening or reading `" + file + "`", e);
            }
        }
        return lines;
    }

    private Stream<String> asStream(String file) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(file)), UTF_8));
        return r.lines();
    }

    public static void main(String... args) throws Exception {
        if (args.length < 1) {
            usage();
            System.exit(1);
        }

        new GemArchiveProcessor().process(Arrays.asList(args));
    }

    public static void usage() {
        System.out.println("java org.indyscala.streams.j8.GemArchiveProcessor file1.gz [file2.gz ... fileN.gz]");
    }
}
