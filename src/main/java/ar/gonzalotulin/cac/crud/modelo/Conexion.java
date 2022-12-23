/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.gonzalotulin.cac.crud.modelo;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
/**
 *
 * @author Gonzalo
 */
public class Conexion {

    private static final String URL_DB = "jdbc:mysql://root:gonzalo@localhost:3306/cac_crud_bd?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static Connection con;
    private static BasicDataSource dataSource;

    private Conexion() {
    }

    public static DataSource getDataSource() {
        if (dataSource == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                dataSource = new BasicDataSource();
                dataSource.setUrl(URL_DB);
                dataSource.setInitialSize(50);
            } catch (Exception ex) {
                throw new RuntimeException("Error de E/S al leer config de BBDD", ex);
            }
        }
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
}
