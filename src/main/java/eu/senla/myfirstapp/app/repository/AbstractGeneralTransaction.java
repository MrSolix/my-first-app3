package eu.senla.myfirstapp.app.repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractGeneralTransaction<T> implements GeneralTransaction<T> {
    protected final ThreadLocal<T> threadLocal = new ThreadLocal<>();
    private static ConnectionType connectionType = ConnectionType.SINGLE;

    public static void setConnectionType(ConnectionType connectionType) {
        AbstractGeneralTransaction.connectionType = connectionType;
    }

    public static ConnectionType getConnectionType() {
        return connectionType;
    }

    public void closeQuietly(AutoCloseable... autoCloseable) {
        try {
            for (AutoCloseable ac :
                    autoCloseable) {
                if (ac != null) {
                    ac.close();
                }
            }
        } catch (Exception e) {
            log.error("Couldn't close ", e);
        }
    }
}
