package com.safety_signature.safety_signature_back.config;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public class Partial<E> extends MappingJacksonValue {

    public Partial(Object value) {
        super(value);
    }

    public static <E>Partial<E> with(E value, FieldSelector fieldSelector) {

        checkNotNull(value);
        checkNotNull(fieldSelector);

        Partial<E> result = new Partial<E>(value);
        Class<?> serializationView = fieldSelector.getView();
        Set<String> fields = fieldSelector.getFields();

        if (serializationView != null) {
            result.setSerializationView(serializationView);
        }

        if (fields != null && !fields.isEmpty()) {
            FilterProvider filter = new SimpleFilterProvider()
                    .addFilter(FieldSelector.FILTER_NAME, SimpleBeanPropertyFilter.filterOutAllExcept(fields));
            result.setFilters(filter);
        }

        return result;
    }

    public static <E>Partial<E> with(E value) {

        checkNotNull(value);

        Partial<E> result = new Partial<E>(value);
        return result;

    }
}
