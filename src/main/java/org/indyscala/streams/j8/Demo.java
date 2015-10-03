package org.indyscala.streams.j8;

import java.io.InputStream;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

import static org.indyscala.streams.support.StreamSupport.*;

public class Demo {
    private static final int BUFFER_SIZE = 8192;

    public static long countLines() throws Exception {
        return streamLines()
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
}
