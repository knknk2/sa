package com.sa.sa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link To2mCarSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class To2mCarSearchRepositoryMockConfiguration {

    @MockBean
    private To2mCarSearchRepository mockTo2mCarSearchRepository;

}
