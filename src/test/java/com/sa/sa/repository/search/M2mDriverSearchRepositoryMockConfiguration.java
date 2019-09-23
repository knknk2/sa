package com.sa.sa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link M2mDriverSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class M2mDriverSearchRepositoryMockConfiguration {

    @MockBean
    private M2mDriverSearchRepository mockM2mDriverSearchRepository;

}
