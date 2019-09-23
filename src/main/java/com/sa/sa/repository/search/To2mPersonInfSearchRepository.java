package com.sa.sa.repository.search;

import com.sa.sa.domain.To2mPersonInf;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link To2mPersonInf} entity.
 */
public interface To2mPersonInfSearchRepository extends ElasticsearchRepository<To2mPersonInf, Long> {
}
