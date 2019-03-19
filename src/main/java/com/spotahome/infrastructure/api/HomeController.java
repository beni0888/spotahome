package com.spotahome.infrastructure.api;

import com.spotahome.application.GetHomes;
import com.spotahome.application.GetHomesQuery;
import com.spotahome.domain.Home;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/homes")
public class HomeController {
    @Autowired
    private GetHomes getHomes;

    @GetMapping
    public List<Home> listHomes(@RequestParam("sortedBy") String sortedBy) {
        GetHomesQuery query = new GetHomesQuery(sortedBy);
        return getHomes.execute(query);
    }
}
