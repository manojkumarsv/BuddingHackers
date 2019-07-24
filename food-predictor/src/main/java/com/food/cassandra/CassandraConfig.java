package com.food.cassandra;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraEntityClassScanner;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@PropertySource(value = { "classpath:cassandra.properties" })
@EnableCassandraRepositories(basePackages = "com.food.cassandra.repositories")
public class CassandraConfig extends AbstractCassandraConfiguration {

	@Autowired
	private Environment environment;

	@Override
	protected String getContactPoints() {
		return environment.getProperty("cassandra.contactpoints");
	}

	@Override
	protected int getPort() {
		return Integer.parseInt(environment.getProperty("cassandra.port"));
	}

	@Override
	protected String getKeyspaceName() {
		return environment.getProperty("cassandra.keyspace");
	}

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.RECREATE_DROP_UNUSED;
	}

	@Override
	public String[] getEntityBasePackages() {
		return new String[] { "com.food.cassandra.entity" };
	}

	@Override
	protected Set<Class<?>> getInitialEntitySet() throws ClassNotFoundException {
		return CassandraEntityClassScanner.scan(getEntityBasePackages());
	}

	@Bean
	public CassandraMappingContext mappingContext() throws ClassNotFoundException {
		CassandraMappingContext mappingContext = new CassandraMappingContext();
		mappingContext.setInitialEntitySet(getInitialEntitySet());
		return mappingContext;
	}

	@Bean
	public CassandraConverter converter() throws ClassNotFoundException {
		return new MappingCassandraConverter(cassandraMapping());
	}

	@Override
	protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
		CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(getKeyspaceName())
				.ifNotExists().with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication();
		return Arrays.asList(specification);
	}

	@Bean
	public CassandraClusterFactoryBean cluster() {
		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setContactPoints(getContactPoints());
		cluster.setPort(getPort());
		cluster.setJmxReportingEnabled(false);
		cluster.setKeyspaceCreations(getKeyspaceCreations());
		return cluster;
	}

	@Bean
	public CassandraSessionFactoryBean session() {
		CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
		session.setCluster(cluster().getObject());
		session.setKeyspaceName(getKeyspaceName());
		try {
			session.setConverter(converter());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		session.setSchemaAction(getSchemaAction());

		return session;
	}

}