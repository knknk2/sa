package com.sa.sa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link Um2oOwnerSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class Um2oOwnerSearchRepositoryMockConfiguration {

    @MockBean
    private Um2oOwnerSearchRepository mockUm2oOwnerSearchRepository;

}
