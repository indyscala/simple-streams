package org.indyscala.streams.j8;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class GemArchiveProcessor {

    public void process(Iterable<String> files) {
        Stream<String> lines = lines(files);
        long count = lines.count();
        System.out.println("LINES: " + count);
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
