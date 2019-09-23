package com.sa.sa.repository.search;
import com.sa.sa.domain.Uo2oPassport;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Uo2oPassport} entity.
 */
public interface Uo2oPassportSearchRepository extends ElasticsearchRepository<Uo2oPassport, Long> {
}
