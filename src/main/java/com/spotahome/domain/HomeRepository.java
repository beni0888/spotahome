package com.spotahome.domain;

import java.util.List;

public interface HomeRepository {

    List<Home> findAllSortedBy(SortingCriteria criteria);

}
