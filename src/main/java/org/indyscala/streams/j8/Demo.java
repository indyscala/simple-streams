package org.indyscala.streams.j8;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

import static org.indyscala.streams.support.StreamSupport.*;

public class Demo {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Result countLines() throws Exception {
        long count = prepareLines()
            .count();

        return new Result()
            .put("count", count)
            .lock();
    }

    public static Result countJson() throws Exception {
        long count = prepareJson()
            .count();

        return new Result()
            .put("count", count)
            .lock();
    }

    public static Result countJsonGrouped() throws Exception {
        Map<Boolean,Long> counts = prepareJson()
            .collect(Collectors.groupingBy(
                        Optional::isPresent, HashMap::new, Collectors.counting()));

        // groupingBy(): http://docs.oracle.com/javase/8/docs/api/java/util/stream/Collectors.html#groupingBy-java.util.function.Function-java.util.function.Supplier-java.util.stream.Collector-

        long errorCount = counts.get(Boolean.FALSE).longValue();
        long parsedCount = counts.get(Boolean.TRUE).longValue();

        return new Result()
            .put("count", parsedCount + errorCount)
            .put("parsed", parsedCount)
            .put("errors", errorCount)
            .lock();
    }

    static Stream<String> prepareLines() throws Exception {
        Iterable<InputStream> inputs = findInputs();
        Stream<String> lines = Stream.empty();
        for (InputStream in : inputs) {
            lines = Stream.concat(lines, bufferedLineStream(in, UTF_8, BUFFER_SIZE));
        }
        return lines;
    }

    private static Stream<Optional<JsonNode>> prepareJson() throws Exception {
        return prepareLines()
            .map(Demo::parseJson);
    }

    private static Optional<JsonNode> parseJson(String json) {
        try {
            return Optional.of(mapper.readTree(json));
        } catch (Exception e) {
            // punt on checked exceptions in Java8 map()
            return Optional.empty();
        }
    }

    public static class Result {
        private Map<String, Object> vals;

        public Result() {
            vals = new HashMap<>();
        }

        public Result lock() {
            vals = Collections.unmodifiableMap(vals);
            return this;
        }

        public Result put(String key, Object val) {
            vals.put(key, val);
            return this;
        }

        public Object get(String key) {
            return vals.get(key);
        }

        public Number getCount() {
            Number count = (Number) vals.get("count");
            if (count == null) {
                throw new NoSuchElementException(("count not specified"));
            }
            return count;
        }
    }
}
