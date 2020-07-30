package util;
import java.sql.*;
import java.util.*;
import javax.sql.DataSource;
import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;

public class JDBC
{
    private static DataSource dataSource;

    static
    {
        try
        {
            Properties properties = new Properties();
            properties.load(JDBC.class.getClassLoader().getResourceAsStream("util/JDBC.properties"));
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static Connection getConnection()
    {
        try
        {
            return dataSource.getConnection();
        }
        catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static List<Object> queryObjects(Class cls, String sql, Object... params)
    {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            for (int i = 0; i < params.length; i++)
                stmt.setObject(i + 1, params[i]);
            ResultSet result = stmt.executeQuery();

            ResultSetMetaData meta = result.getMetaData();
            String[] columns = new String[meta.getColumnCount()];
            for (int i = 0; i < columns.length; i++)
                columns[i] = meta.getColumnName(i + 1);

            List<Object> list = new ArrayList<>();
            while (result.next())
            {
                Object[] data = new Object[columns.length];
                for (int i = 0; i < data.length; i++)
                    data[i] = result.getObject(i + 1);
                list.add(Reflect.toObject(cls, columns, data));
            }
            return list;

        }
        catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    private static List<Object> queryScalars(String sql, Object... params)
    {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            for (int i = 0; i < params.length; i++)
                stmt.setObject(i + 1, params[i]);
            ResultSet result = stmt.executeQuery();

            List<Object> list = new ArrayList<>();
            while (result.next())
                list.add(result.getObject(1));
            return list;
        }
        catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static List<Integer> queryIntegers(String sql, Object... params)
    {
        List<Object> list = queryScalars(sql, params);
        List<Integer> integerList = new ArrayList<>(list.size());
        for (Object e: list)
            integerList.add((Integer)e);
        return integerList;
    }

    public static Object queryScalar(String sql, Object... params)
    {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            for (int i = 0; i < params.length; i++)
                stmt.setObject(i + 1, params[i]);
            ResultSet result = stmt.executeQuery();

            result.next();
            return result.getObject(1);
        }
        catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static int update(String sql, Object... params)
    {
        try (Connection conn = dataSource.getConnection())
        {
            return update(conn, sql, params);
        }
        catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static int update(Connection conn, String sql, Object... params)
    {
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            for (int i = 0; i < params.length; i++)
                stmt.setObject(i + 1, params[i]);
            return stmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public static Object insert(String sql, Object... params)
    {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            for (int i = 0; i < params.length; i++)
                stmt.setObject(i + 1, params[i]);
            stmt.executeUpdate();

            ResultSet result = stmt.getGeneratedKeys();
            result.next();
            return result.getObject(1);
        }
        catch (SQLException ex)
        {
            throw new RuntimeException(ex);
        }
    }
}