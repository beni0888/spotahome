package com.spotahome.infrastructure.persistence.jpa;

import com.spotahome.domain.Home;
import com.spotahome.domain.SortingCriteria;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@RunWith(SpringRunner.class)
public class HomeJpaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    private HomeJpaRepository repository;

    @Before
    public void setUp() {
        repository = new HomeJpaRepository(entityManager.getEntityManager());
    }

    @Test
    public void givenFindAllSortedBy_whenItIsCalledPassingId_thenItWorks() {
        HomeJpaEntity home1 = aHomeEntity("Home 1", "Madrid", "http://foo.com/home-1", "http://foo.com/picture-1.jpg");
        HomeJpaEntity home2 = aHomeEntity("Home 2", "Barcelona", "http://foo.com/home-2", "http://foo.com/picture-2.jpg");

        home1 = entityManager.persist(home1);
        home2 = entityManager.persistAndFlush(home2);

        List<Home> homes = repository.findAllSortedBy(SortingCriteria.create("id"));

        assertThat(homes).hasSize(2);
        assertThat(homes.get(0).getId()).isEqualTo(home1.getId());
        assertThat(homes.get(1).getId()).isEqualTo(home2.getId());
    }

    @Test
    public void givenFindAllSortedBy_whenItIsCalledPassingTitle_thenItWorks() {
        HomeJpaEntity home1 = aHomeEntity("B Home 1", "Madrid", "http://foo.com/home-1", "http://foo.com/picture-1.jpg");
        HomeJpaEntity home2 = aHomeEntity("A Home 2", "Barcelona", "http://foo.com/home-2", "http://foo.com/picture-2.jpg");

        home1 = entityManager.persist(home1);
        home2 = entityManager.persistAndFlush(home2);

        List<Home> homes = repository.findAllSortedBy(SortingCriteria.create("title"));

        assertThat(homes).hasSize(2);
        assertThat(homes.get(0).getId()).isEqualTo(home2.getId());
        assertThat(homes.get(1).getId()).isEqualTo(home1.getId());
    }

    private HomeJpaEntity aHomeEntity(String title, String city, String url, String picture) {
        HomeJpaEntity homeJpaEntity = new HomeJpaEntity();
        homeJpaEntity.setTitle(title).setCity(city).setUrl(url).setPicture(picture);
        return homeJpaEntity;
    }
}