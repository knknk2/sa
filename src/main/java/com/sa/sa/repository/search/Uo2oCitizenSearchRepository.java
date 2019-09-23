package com.sa.sa.repository.search;
import com.sa.sa.domain.Uo2oCitizen;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Uo2oCitizen} entity.
 */
public interface Uo2oCitizenSearchRepository extends ElasticsearchRepository<Uo2oCitizen, Long> {
}
