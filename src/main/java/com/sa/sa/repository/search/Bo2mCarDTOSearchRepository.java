package com.sa.sa.repository.search;

import com.sa.sa.domain.Bo2mCarDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Bo2mCarDTO} entity.
 */
public interface Bo2mCarDTOSearchRepository extends ElasticsearchRepository<Bo2mCarDTO, Long> {
}
