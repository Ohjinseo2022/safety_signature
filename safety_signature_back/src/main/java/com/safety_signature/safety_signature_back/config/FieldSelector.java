package com.safety_signature.safety_signature_back.config;

import com.google.common.collect.Sets;
import com.safety_signature.safety_signature_back.app.common.dto.View;

import java.util.List;
import java.util.Set;

/**
 *
 * response에 포함할 필드 선택자 (View 또는 필드 목록)
 *
 */
public class FieldSelector {

    public static final String FILTER_NAME = "ApiFilter";
    private Class<?> view;
    private Set<String> fields;

    public Class<?> getView() {
        return view;
    }

    public Set<String> getFields() {
        return fields;
    }

    public void setFields(Set<String> fields) {
        this.fields = fields;
    }

    public FieldSelector(Class<?> view) {
        this.view = view;
    }

    public FieldSelector(Set<String> fields) {
        this.fields = fields;
    }

    public FieldSelector(Class<?> view, Set<String> fields) {
        this.view = view;
        this.fields = fields;
    }

    public static FieldSelector withDefaultView(List<String> selectors, Class<?> defaultView) {

        //checkNotNull(defaultView);

        if (selectors == null || selectors.isEmpty()) {
            return new FieldSelector(defaultView);
        }

        Set<String> fields = Sets.newLinkedHashSet();
        for(String each : selectors) {
            Class<?> view = toViewClass(each);

            if (view == null) {
                fields.add(each);
            }
            else {
                return new FieldSelector(view);
            }
        }

        return new FieldSelector(fields);
    }

    static Class<?> toViewClass(String str) {

        if ("@min".equals(str)) return View.Min.class;
        else if ("@summary".equals(str)) return View.Summary.class;
        else if ("@basic".equals(str)) return View.Basic.class;
        else if ("@detail".equals(str)) return View.Detail.class;

        return null;
    }
}
