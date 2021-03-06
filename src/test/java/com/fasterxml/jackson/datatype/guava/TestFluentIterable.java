package com.fasterxml.jackson.datatype.guava;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests to verify serialization of {@link FluentIterable}s.
 */
public class TestFluentIterable extends BaseTest
{
    private final ObjectMapper MAPPER = mapperWithModule();


    FluentIterable<Integer> createFluentIterable() {
        return new FluentIterable<Integer>() {
            private final Iterable<Integer> _iterable = Sets.newHashSet(1, 2, 3);
            @Override
            public Iterator<Integer> iterator() {
                return _iterable.iterator();
            }
        };
    }

    /**
     * This test is present so that we know if either Jackson's handling of FluentIterable
     * or Guava's implementation of FluentIterable changes.
     * @throws Exception
     */
    public void testSerializationWithoutModule() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Iterable<Integer> fluentIterable = createFluentIterable();

        String json = mapper.writeValueAsString(fluentIterable);

        assertEquals("{\"empty\":false}", json);
    }

    public void testSerialization() throws Exception {
        Iterable<Integer> fluentIterable = createFluentIterable();

        String json = MAPPER.writeValueAsString(fluentIterable);

        assertEquals("[1,2,3]", json);
    }

}
