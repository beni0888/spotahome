package com.spotahome.infrastructure.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotahome.application.GetHomes;
import com.spotahome.application.GetHomesQuery;
import com.spotahome.domain.Home;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeController {

    private static final String DOWNLOADED_FILE_NAME = "homes.json";
    private static final String DEFAULT_SORTING_FIELD = "id";


    @Autowired
    private GetHomes getHomes;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(path = "/homes")
    public List<Home> listHomes(@RequestParam(value = "sortedBy", defaultValue = DEFAULT_SORTING_FIELD) String sortedBy) {
        GetHomesQuery query = new GetHomesQuery(sortedBy);
        return getHomes.execute(query);
    }

    @GetMapping(path = "/download")
    public ResponseEntity<Resource> downloadHomes(
            @RequestParam(value = "sortedBy", defaultValue = DEFAULT_SORTING_FIELD) String sortedBy)
            throws JsonProcessingException {

        List<Home> homes = getHomes.execute(new GetHomesQuery(sortedBy));
        ByteArrayResource jsonResource = new ByteArrayResource(objectMapper.writeValueAsString(homes).getBytes(StandardCharsets.UTF_8));

        return ResponseEntity.ok()
                .headers(getHttpHeaders())
                .contentLength(jsonResource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(jsonResource);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", DOWNLOADED_FILE_NAME));
        return headers;
    }

}
