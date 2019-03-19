package com.spotahome.application;

import com.spotahome.domain.Home;
import com.spotahome.domain.HomeRepository;
import com.spotahome.domain.SortingCriteria;

import java.util.List;

public class GetHomes {

    private HomeRepository repository;


    public GetHomes(HomeRepository repository) {
        this.repository = repository;
    }

    public List<Home> execute(GetHomesQuery query) {
        SortingCriteria sortingCriteria = SortingCriteria.create(query.sortedBy);
        return repository.findAllSortedBy(sortingCriteria);
    }
}
