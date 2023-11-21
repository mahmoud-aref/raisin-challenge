package com.raisin.backend.infrastructure.http.client.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "msg")
public class SourceBResponse {

    // jackson maps the response to ex -> { id: { value: "123" } }
    // jackson maps the done response to ex -> { done= }


    @JacksonXmlProperty(localName = "id")
    private IdWrapper id;

    @JacksonXmlProperty(localName = "done")
    private DoneWrapper done;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IdWrapper {
        @JacksonXmlProperty(localName = "value")
        private String value;
    }

    @Data
    public static class DoneWrapper {
        @JacksonXmlProperty(localName = "value")
        private String value;
    }
}
