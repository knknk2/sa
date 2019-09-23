package com.sa.sa.repository.search;
import com.sa.sa.domain.To2mCar;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link To2mCar} entity.
 */
public interface To2mCarSearchRepository extends ElasticsearchRepository<To2mCar, Long> {
}
