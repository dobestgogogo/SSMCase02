package com.tjx.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateSerializer  extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator jsGener, SerializerProvider arg2) throws IOException,
            JsonProcessingException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sf.format(value);
        jsGener.writeString(strDate);
    }
}
