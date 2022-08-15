package com.shopdashboardservice;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;

import static org.testcontainers.utility.MountableFile.forClasspathResource;

@Slf4j
public class TestEnvironment {

    private static TestEnvironment testEnvironment;

    private final Network network;
    private final PostgresDBTestContainer postgres;

    private TestEnvironment() {
        network = Network.newNetwork();
        log.info("--------------------------");
        log.info("Starting test environment");
        log.info("--------------------------");

        postgres = PostgresDBTestContainer.create(network);

        log.info("Postgres started at host={} port={}", postgres.getHost(), postgres.getFirstMappedPort().toString());
    }

    public String getPostgresUrl(String database) {
        return String.format("jdbc:postgresql://%s:%s/%s",
                postgres.getHost(),
                postgres.getFirstMappedPort().toString(),
                database);
    }

    public static TestEnvironment getInstance() {
        if (testEnvironment == null) {
            try {
                testEnvironment = new TestEnvironment();
            } catch (Exception e) {
                log.error("", e);
                throw new RuntimeException(e);
            }
        }
        return testEnvironment;
    }

    public static class PostgresDBTestContainer extends GenericContainer<PostgresDBTestContainer> {
        private static final String POSTGRES_SERVER_STARTED = ".*database system is ready to accept connections.*";

        private static final int PORT = 5432;
        private static final String IMAGE_VERSION = "postgres:14";
        private static PostgresDBTestContainer container;

        private PostgresDBTestContainer() {
            super(IMAGE_VERSION);
        }

        public static PostgresDBTestContainer create(final Network network) {
            if (container == null) {
                container = new PostgresDBTestContainer()
                        .withEnv("POSTGRES_USER", "postgres")
                        .withEnv("POSTGRES_PASSWORD", "postgres")
                        .withNetwork(network)
                        .withNetworkAliases("postgres")
                        .withExposedPorts(PORT)
                        .withCopyFileToContainer(
                                forClasspathResource("script/sql/create-tables.sql"), "/script/sql/create-tables.sql")
                        .withCopyFileToContainer(
                                forClasspathResource("script/sql/insert-test-data.sql"), "/script/sql/insert-test-data.sql")
                        .withCopyFileToContainer(
                                forClasspathResource("script/insert-test-data.sh"), "/docker-entrypoint-initdb.d/insert-test-data.sh")
                        .waitingFor(Wait.forLogMessage(POSTGRES_SERVER_STARTED, 1));
            }
            container.start();
            return container;
        }
    }
}
