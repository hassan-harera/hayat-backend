package com.harera.hayat.authorization.config;

import static java.time.LocalDate.parse;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.AbstractConverter;
import org.modelmapper.AbstractProvider;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.convention.MatchingStrategies;

import com.harera.hayat.authorization.util.DateFormat;

public class NotNullableMapper extends ModelMapper {

    public NotNullableMapper() {
        this.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Provider<LocalDate> localDateProvider = createLocalDateProvider();
        Converter<String, LocalDate> localDateConvertor = createLocalDateConverter();
        this.createTypeMap(String.class, LocalDate.class);
        this.addConverter(localDateConvertor);
        this.getTypeMap(String.class, LocalDate.class).setProvider(localDateProvider);

        Provider<OffsetDateTime> localDateTimeProvider = createOffsetDateTimeProvider();
        Converter<String, OffsetDateTime> localDateTimeConvertor =
                        getOffsetDateTimeToStringConverter();

        this.createTypeMap(String.class, OffsetDateTime.class);
        this.addConverter(localDateTimeConvertor);
        this.getTypeMap(String.class, OffsetDateTime.class)
                        .setProvider(localDateTimeProvider);

        this.addConverter(getOffsetDateTimetoStringConverter());
    }

    @Override
    public <D> D map(Object source, Class<D> destinationType) {
        if (source == null) {
            return null;
        }
        return super.map(source, destinationType);
    }

    private Provider<LocalDate> createLocalDateProvider() {
        return new AbstractProvider<>() {
            @Override
            public LocalDate get() {
                return LocalDate.now();
            }
        };
    }

    private Converter<String, LocalDate> createLocalDateConverter() {
        return new AbstractConverter<>() {
            @Override
            protected LocalDate convert(String source) {
                if (StringUtils.isNotBlank(source)) {
                    DateTimeFormatter format =
                                    DateTimeFormatter.ofPattern(DateFormat.DATE_FORMAT);
                    return parse(source, format);
                }
                return null;
            }
        };
    }

    private Provider<OffsetDateTime> createOffsetDateTimeProvider() {
        return new AbstractProvider<>() {
            @Override
            public OffsetDateTime get() {
                return OffsetDateTime.now();
            }
        };
    }

    private Converter<String, OffsetDateTime> getOffsetDateTimeToStringConverter() {
        return new AbstractConverter<>() {
            @Override
            protected OffsetDateTime convert(String source) {
                if (StringUtils.isNotBlank(source)) {
                    return OffsetDateTime.parse(source);
                }
                return null;
            }
        };
    }

    private Converter<OffsetDateTime, String> getOffsetDateTimetoStringConverter() {
        return new AbstractConverter<>() {
            @Override
            protected String convert(OffsetDateTime source) {
                if (source == null) {
                    return null;
                }
                return source.toString();
            }
        };
    }
}
