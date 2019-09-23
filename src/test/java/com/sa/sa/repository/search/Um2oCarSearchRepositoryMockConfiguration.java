package com.sa.sa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link Um2oCarSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class Um2oCarSearchRepositoryMockConfiguration {

    @MockBean
    private Um2oCarSearchRepository mockUm2oCarSearchRepository;

}
