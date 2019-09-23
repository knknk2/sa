package com.sa.sa.repository.search;
import com.sa.sa.domain.M2mCar;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link M2mCar} entity.
 */
public interface M2mCarSearchRepository extends ElasticsearchRepository<M2mCar, Long> {
}
