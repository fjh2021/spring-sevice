package com.fjh.common.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Slf4j
@JsonComponent
public class WebTypeConvertConfig {

    private static final String DATE_PATTERN_YMD_HMS = "yyyy-MM-dd HH:mm:ss";

    private static final String DATE_PATTERN_YMD = "yyyy-MM-dd";

    /**
     * 1.Long类型序列化损失精度。JSON 响应报文时,将所有的 long 变成 string, 因为 js 中得数字类型不能包含所有的 java long 值
     * 2.处理日期格式
     */
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerialize());
        timeModule.addSerializer(LocalDate.class, new LocalDateSerialize());
        timeModule.addDeserializer(LocalDateTime.class, new DateYmdHmsJsonDeserializer());
        timeModule.addDeserializer(LocalDate.class, new DateYmdJsonDeserializer());


        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        /*
         * 可配置项：
         * 1.Include.Include.ALWAYS : 默认
         * 2.Include.NON_DEFAULT : 默认值不序列化
         * 3.Include.NON_EMPTY : 属性为 空（""） 或者为 NULL 都不序列化
         * 4.Include.NON_NULL : 属性为NULL 不序列化
         **/
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat(DATE_PATTERN_YMD_HMS));
        objectMapper.registerModules(simpleModule, timeModule);
        return objectMapper;
    }

    private static class LocalDateTimeSerialize extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(
                LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            String format =DateTimeFormatter.ofPattern(DATE_PATTERN_YMD_HMS).format(value);
            gen.writeString(format);
        }
    }

    private static class LocalDateSerialize extends JsonSerializer<LocalDate> {
        @Override
        public void serialize(
                LocalDate value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            String format =DateTimeFormatter.ofPattern(DATE_PATTERN_YMD).format(value);
            gen.writeString(format);
        }
    }

    private static class DateYmdJsonDeserializer extends JsonDeserializer<LocalDate> {
        @Override
        public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return jsonParser != null && !StringUtils.isEmpty(jsonParser.getText()) ?
                    LocalDate.parse(jsonParser.getText(), DateTimeFormatter.ofPattern(DATE_PATTERN_YMD)) :
                    null;
        }
    }

    private static class DateYmdHmsJsonDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN_YMD_HMS);
            if (jsonParser != null && !StringUtils.isEmpty(jsonParser.getText())) {
                String text = jsonParser.getText();
                if (text.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
                    text = text.trim() + " 00:00:00";
                }
                return LocalDateTime.parse(text, dateTimeFormatter);
            }
            return null;
        }
    }
}
