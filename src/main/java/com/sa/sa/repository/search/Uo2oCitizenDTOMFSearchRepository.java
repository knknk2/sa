package com.sa.sa.repository.search;

import com.sa.sa.domain.Uo2oCitizenDTOMF;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Uo2oCitizenDTOMF} entity.
 */
public interface Uo2oCitizenDTOMFSearchRepository extends ElasticsearchRepository<Uo2oCitizenDTOMF, Long> {
}
