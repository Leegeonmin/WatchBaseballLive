package com.bongbong.watchbaseball.dto.weatherapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediumPrecipitationResponse {

    @JsonProperty("response")
    private Response response;

    @Getter
    @NoArgsConstructor
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {

        @JsonProperty("body")
        private Body body;
    }

    @Getter
    @NoArgsConstructor
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Body {

        @JsonProperty("items")
        private Items items;
    }

    @Getter
    @NoArgsConstructor
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Items {

        @JsonProperty("item")
        private List<MediumPrecipitationItem> item;
    }

    @Getter
    @NoArgsConstructor
    @ToString
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MediumPrecipitationItem {

        @JsonProperty("regId")
        private String regId;

        @JsonProperty("rnSt4Am")
        private int rnSt4Am;

        @JsonProperty("rnSt4Pm")
        private int rnSt4Pm;

        @JsonProperty("rnSt5Am")
        private int rnSt5Am;

        @JsonProperty("rnSt5Pm")
        private int rnSt5Pm;

        @JsonProperty("rnSt6Am")
        private int rnSt6Am;

        @JsonProperty("rnSt6Pm")
        private int rnSt6Pm;

        @JsonProperty("rnSt7Am")
        private int rnSt7Am;

        @JsonProperty("rnSt7Pm")
        private int rnSt7Pm;

        @JsonProperty("rnSt8")
        private int rnSt8;

        @JsonProperty("rnSt9")
        private int rnSt9;

        @JsonProperty("rnSt10")
        private int rnSt10;

        @JsonProperty("wf4Am")
        private String wf4Am;

        @JsonProperty("wf4Pm")
        private String wf4Pm;

        @JsonProperty("wf5Am")
        private String wf5Am;

        @JsonProperty("wf5Pm")
        private String wf5Pm;

        @JsonProperty("wf6Am")
        private String wf6Am;

        @JsonProperty("wf6Pm")
        private String wf6Pm;

        @JsonProperty("wf7Am")
        private String wf7Am;

        @JsonProperty("wf7Pm")
        private String wf7Pm;

        @JsonProperty("wf8")
        private String wf8;

        @JsonProperty("wf9")
        private String wf9;

        @JsonProperty("wf10")
        private String wf10;
    }
}
