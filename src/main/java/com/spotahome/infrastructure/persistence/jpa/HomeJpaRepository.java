package com.spotahome.infrastructure.persistence.jpa;

import com.spotahome.domain.Home;
import com.spotahome.domain.HomeRepository;
import com.spotahome.domain.SortingCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class HomeJpaRepository implements HomeRepository {

    private EntityManager entityManager;

    @Autowired
    public HomeJpaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Home> findAllSortedBy(SortingCriteria criteria) {
        String query = "SELECT h FROM HomeJpaEntity h ORDER BY " + criteria.fieldName;
        List<HomeJpaEntity> homes = entityManager.createQuery(query).getResultList();

        return homes.stream().map(homeJpaEntity -> homeJpaEntity.toDomain())
                .collect(Collectors.toList());
    }

    @Override
    public void save(Home home) {
        HomeJpaEntity homeJpaEntity = HomeJpaEntity.fromDomain(home);
        entityManager.persist(homeJpaEntity);
    }
}
