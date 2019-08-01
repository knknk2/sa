package com.sa.sa.repository.search;

import com.sa.sa.domain.Bo2mCar;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Bo2mCar} entity.
 */
public interface Bo2mCarSearchRepository extends ElasticsearchRepository<Bo2mCar, Long> {
}
