package com.raisin.backend.infrastructure.http.config;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.raisin.backend.infrastructure.http.client.model.ReportResponse;
import com.raisin.backend.infrastructure.http.client.model.SourceAResponse;
import com.raisin.backend.infrastructure.http.client.model.SourceBResponse;
import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Optional;

import static com.raisin.backend.infrastructure.http.config.ResponseTypeNames.OPTIONAL_REPORT_RESPONSE;
import static com.raisin.backend.infrastructure.http.config.ResponseTypeNames.OPTIONAL_SOURCE_A_RESPONSE;
import static com.raisin.backend.infrastructure.http.config.ResponseTypeNames.OPTIONAL_SOURCE_B_RESPONSE;

@Slf4j
public class FixtureDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        try {
            if (type != null) {
                switch (type.getTypeName()) {
                    case OPTIONAL_SOURCE_A_RESPONSE -> {
                        ObjectMapper mapper = new ObjectMapper();
                        return Optional.of(mapper.readValue(response.body().asInputStream(), SourceAResponse.class));
                    }
                    case OPTIONAL_SOURCE_B_RESPONSE -> {
                        XmlMapper mapperXml = new XmlMapper();
                        return Optional.of(mapperXml.readValue(response.body().asInputStream(), SourceBResponse.class));
                    }
                    case OPTIONAL_REPORT_RESPONSE -> {
                        ObjectMapper mapper = new ObjectMapper();
                        return Optional.of(mapper.readValue(response.body().asInputStream(), ReportResponse.class));
                    }
                }
            }
        } catch (DecodeException | JsonParseException | MismatchedInputException exception) {
            log.error("Malformed response Type Should Ignored {}", exception.getMessage());
        }
        return Optional.empty();
    }

}
