import java.sql.*;
import org.apache.log4j.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) throws Exception {
        try{
            Class.forName("org.h2.Driver").newInstance();
        }catch(ClassNotFoundException e){
            logger.error("Error en conexión primaria con base de datos: ", e);
            System.exit(0);
        }
        try{
            Connection c = DriverManager.getConnection("jdbc:h2:"+
                    "./Database/my", "sa", "sa");
            Statement stmt = c.createStatement();
            String createSql = """
                DROP TABLE IF EXISTS EMPLEADO;
                CREATE TABLE EMPLEADO(ID INT PRIMARY KEY, NAME VARCHAR(255), EDAD TINYINT, EMPRESA VARCHAR(255), FECHA_COMIENZO DATE);
                INSERT INTO EMPLEADO VALUES(1, 'Juan', 21, 'Digital', '2020-07-27');
                INSERT INTO EMPLEADO VALUES(2, 'Elian', 21, 'Google', '2021-11-22');
                INSERT INTO EMPLEADO VALUES(3, 'Enzo', 27, 'Facebook', '2022-02-15');
                """;
            stmt.execute(createSql);
            String sql = "SELECT * FROM EMPLEADO";
            ResultSet rd = stmt.executeQuery(sql);
            while(rd.next()) {
                System.out.println(rd.getInt(1) + " " +
                                   rd.getString(2) + " " +
                                   rd.getInt(3) + " " +
                                   rd.getString(4) + " " +
                                   rd.getDate(5));
            }
            try{
                c.close();
            }catch(SQLException e){
                logger.error("Error al intentar cerrar al conexión con la base de datos: ", e);
                System.exit(0);
            }
        }catch(Exception e){
            logger.error("Error posible en dos dos:" +
                         "la conexión secundaria con la base de datos" +
                         "o la creación o llamada de la tabla SQL", e);
            System.exit(0);
        }
    }
}