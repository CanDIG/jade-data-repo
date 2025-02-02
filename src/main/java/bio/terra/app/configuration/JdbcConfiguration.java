package bio.terra.app.configuration;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.util.Properties;

/**
 * Base class for accessing JDBC configuration properties.
 */
public class JdbcConfiguration {
    private String uri;
    private String username;
    private String password;
    private String changesetFile;

    // Not a property
    private PoolingDataSource<PoolableConnection> dataSource;

    public String getUri() {
        return uri;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getChangesetFile() {
        return changesetFile;
    }

    // NOTE: even though the setters appear unused, the Spring infrastructure uses them to populate the properties.
    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setChangesetFile(String changesetFile) {
        this.changesetFile = changesetFile;
    }

    // Main use of the configuration is this pooling data source object.
    public PoolingDataSource<PoolableConnection> getDataSource() {
        // Lazy allocation of the data source
        if (dataSource == null) {
            configureDataSource();
        }
        return dataSource;
    }

    private void configureDataSource() {
        Properties props = new Properties();
        props.setProperty("user", getUsername());
        props.setProperty("password", getPassword());

        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(getUri(), props);

        PoolableConnectionFactory poolableConnectionFactory =
                new PoolableConnectionFactory(connectionFactory, null);

        ObjectPool<PoolableConnection> connectionPool =
                new GenericObjectPool<>(poolableConnectionFactory);

        poolableConnectionFactory.setPool(connectionPool);

        dataSource = new PoolingDataSource<>(connectionPool);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("uri", uri)
                .append("username", username)
                // .append("password", password) NOTE: password is not printed; that avoids it showing up in logs
                .toString();
    }
}
