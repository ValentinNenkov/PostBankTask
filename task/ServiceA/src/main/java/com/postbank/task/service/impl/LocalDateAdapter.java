package com.postbank.task.service.impl;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public LocalDate unmarshal(String v) {
        return v != null && !v.isEmpty() ? LocalDate.parse(v, FORMATTER) : null;
    }

    @Override
    public String marshal(LocalDate v) {
        return v != null ? v.format(FORMATTER) : null;
    }
}
