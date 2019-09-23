package com.sa.sa.repository.search;

import com.sa.sa.domain.Uo2oPassportDTOMF;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Uo2oPassportDTOMF} entity.
 */
public interface Uo2oPassportDTOMFSearchRepository extends ElasticsearchRepository<Uo2oPassportDTOMF, Long> {
}
