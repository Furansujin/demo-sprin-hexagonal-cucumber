package com.furansujin.demosprinhexagonalcucumber.itTest.vectorstore;

import com.furansujin.demosprinhexagonalcucumber.infrastructure.adapters.persistence.postgreSQL.PgVectorStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Christian Tzolov
 */
@ExtendWith(MockitoExtension.class)
public class PgVectorEmbeddingDimensionsTests {

	@Mock
	private EmbeddingClient embeddingClient;

	@Mock
	private JdbcTemplate jdbcTemplate;

	@Test
	public void explicitlySetDimensions() {

		final int explicitDimensions = 696;

		var dim = new PgVectorStore(jdbcTemplate, embeddingClient, explicitDimensions).embeddingDimensions();

		assertThat(dim).isEqualTo(explicitDimensions);
		verify(embeddingClient, never()).dimensions();
	}

	@Test
	public void embeddingClientDimensions() {
		when(embeddingClient.dimensions()).thenReturn(969);

		var dim = new PgVectorStore(jdbcTemplate, embeddingClient).embeddingDimensions();

		assertThat(dim).isEqualTo(969);

		verify(embeddingClient, only()).dimensions();
	}

	@Test
	public void fallBackToDefaultDimensions() {

		when(embeddingClient.dimensions()).thenThrow(new RuntimeException());

		var dim = new PgVectorStore(jdbcTemplate, embeddingClient).embeddingDimensions();

		assertThat(dim).isEqualTo(PgVectorStore.OPENAI_EMBEDDING_DIMENSION_SIZE);
		verify(embeddingClient, only()).dimensions();
	}

}
