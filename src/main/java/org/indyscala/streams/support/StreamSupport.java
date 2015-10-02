package org.indyscala.streams.support;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class StreamSupport {

    private static final String ENV_FILE_LIST = "FILES";

    public static Iterable<InputStream> findInputs() throws IOException {
        List<InputStream> files = new ArrayList<>();
        for (String file : extractFilenames(System.getenv(ENV_FILE_LIST))) {
            files.add(
                    file.endsWith(".gz")
                    ? new GZIPInputStream(new FileInputStream(file))
                    : new FileInputStream(file));
        }
        return files;
    }

    private static String[] extractFilenames(String paths) {
        return paths.split("\\s+");
    }

    public static Stream<String> bufferedLineStream(InputStream in, Charset cs, int bufferSize) {
        BufferedReader r = new BufferedReader(new InputStreamReader(in, cs), bufferSize);
        return r.lines();
    }
}
