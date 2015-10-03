package org.indyscala.streams.j8;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Map;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

import static org.indyscala.streams.support.StreamSupport.*;

public class Demo {
    private static final int BUFFER_SIZE = 8192;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static long countLines() throws Exception {
        return streamLines()
            .count();
    }

    public static long countJson() throws Exception {
        return streamJson()
            .count();
    }

    private static Stream<String> streamLines() throws Exception {
        Iterable<InputStream> inputs = findInputs();
        Stream<String> lines = Stream.empty();
        for (InputStream in : inputs) {
            lines = Stream.concat(lines, bufferedLineStream(in, UTF_8, BUFFER_SIZE));
        }
        return lines;
    }

    private static Stream<Map> streamJson() throws Exception {
        return streamLines()
            .map(Demo::parseJson);
    }

    private static Map parseJson(String json) {
        try {
            return mapper.readValue(json, Map.class);
        } catch (Exception e) {
            // punt on checked exceptions in Java8 map()
            throw new RuntimeException("Failed to parse JSON `" + json + "`", e);
        }
    }
}
