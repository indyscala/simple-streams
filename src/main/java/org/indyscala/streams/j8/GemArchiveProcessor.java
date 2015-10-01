package org.indyscala.streams.j8;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Stream;

import org.indyscala.streams.support.StreamSupport;

import static java.nio.charset.StandardCharsets.UTF_8;

public class GemArchiveProcessor {

    private final ObjectMapper mapper;

    public GemArchiveProcessor() {
        mapper = new ObjectMapper();
    }

    public void process() {
        Stream<String> lines = lines();
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

    private Stream<String> lines() {
        Stream<String> lines = Stream.empty();
        Iterable<InputStream> inputs;
        try {
            inputs = StreamSupport.findInputs();
        } catch (Exception e) {
            throw new RuntimeException("Error finding inputs for processing.", e);
        }
        for (InputStream in : inputs) {
            try {
                lines = Stream.concat(lines, asStream(in));
            } catch (IOException e) {
                throw new RuntimeException("Error opening or reading input.", e);
            }
        }
        return lines;
    }

    private Stream<String> asStream(InputStream in) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(in, UTF_8));
        return r.lines();
    }

    public static void main(String... args) throws Exception {
        new GemArchiveProcessor().process();
    }
}
