package com.zt.annotion.customannotion.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.io.IOException;
import java.util.Date;

public class CommonDateDeserializer extends StdScalarDeserializer {
    public  static final CommonDateDeserializer INSTANCE = new CommonDateDeserializer(Date.class);

    protected CommonDateDeserializer(Class vc) {
        super(vc);
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken t = jsonParser.getCurrentToken();
        if (t == JsonToken.VALUE_STRING) {
            CustomDateFormat customDateFormat = new CustomDateFormat();
            return customDateFormat.parse(jsonParser.getText().trim());
        }
        return super._parseDate(jsonParser,deserializationContext);
    }
}
