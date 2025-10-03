package co.com.bancopopular.automation.abilities;

import co.com.bancopopular.automation.exceptions.ConnectionToDbParametersException;
import co.com.bancopopular.automation.utils.logs.LogPrinter;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.HasTeardown;
import net.serenitybdd.screenplay.RefersToActor;
import net.serenitybdd.screenplay.Actor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToParameters implements Ability, RefersToActor, HasTeardown {
    private final String host;
    private final String port;
    private final String password;
    private final String database;
    private final String user;
    private static final String EXCEPTION_MYSQL_FAILED = "The connect to MySql with error ===> ";
    private static final String CLOSING_MYSQL_FAILED = "Fail closing connection... ";

    public Connection getConn() {
        return conn;
    }

    private Connection conn = null;

    public ConnectToParameters(String host, String port, String password, String database, String user) {
        this.host = host;
        this.port = port;
        this.password = password;
        this.database = database;
        this.user = user;
    }

    public Connection connect(){
        String dbUrl = "jdbc:mysql://".concat(host).concat(":").concat(port).concat("/").concat(database).concat("?useSSL=false");
        try{
            conn = DriverManager.getConnection(dbUrl,user,password);
        }catch (SQLException e){
            LogPrinter.printError(EXCEPTION_MYSQL_FAILED, e.toString());
        }
        return conn;
    }

    public static ConnectToParameters at(String host, String port, String password, String database, String user){
        return new ConnectToParameters(host, port, password, database, user);
    }

    public static ConnectToParameters as(Actor actor){
        if(actor.abilityTo(ConnectToParameters.class) == null){
            throw new ConnectionToDbParametersException();
        }
        return actor.abilityTo(ConnectToParameters.class).asActor(actor);
    }

    @Override
    public void tearDown() {
        try{
            conn.close();
        }catch(SQLException e){
            LogPrinter.printError(CLOSING_MYSQL_FAILED, e.toString());
        }
    }

    @Override
    public <T extends Ability> T asActor(Actor actor) {
        return (T) this;
    }
}
