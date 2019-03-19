package com.spotahome.application;

import com.spotahome.domain.Home;
import com.spotahome.domain.HomeRepository;
import com.spotahome.domain.SortingCriteria;
import com.spotahome.domain.SortingFieldIsInvalid;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetHomesTest {

    private GetHomes getHomes;
    private HomeRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = mock(HomeRepository.class);
        getHomes = new GetHomes(repository);
    }

    @Test
    public void givenValidSortingField_whenExecute_thenItWorks() {

        when(repository.findAllSortedBy(any(SortingCriteria.class))).thenReturn(emptyList());

        List<Home> homes = getHomes.execute(new GetHomesQuery("id"));

        assertThat(homes).isNotNull().isEmpty();
    }

    @Test(expected = SortingFieldIsInvalid.class)
    public void givenInvalidSortingField_whenExecute_thenItThrowsAnException() {

        getHomes.execute(new GetHomesQuery("asdf"));
    }
}