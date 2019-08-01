package com.sa.sa.repository.search;

import com.sa.sa.domain.To2mCarInf;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link To2mCarInf} entity.
 */
public interface To2mCarInfSearchRepository extends ElasticsearchRepository<To2mCarInf, Long> {
}
