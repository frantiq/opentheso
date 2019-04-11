/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mom.trd.opentheso.ws.rest;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import mom.trd.opentheso.SelectedBeans.Connexion;

import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author miled.rousset
 */
@Path("theso")
public class theso {

    private HikariDataSource ds;

    /**
     * Creates a new instance of resources La connexion est faite à chaque
     * question
     *
     */
    public theso() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("hikari.properties");
            if (inputStream != null) {
                properties.load(inputStream);
                //   if(properties.getProperty("webservices").equalsIgnoreCase("false"))
                //      return;
                this.ds = openConnexionPool(properties);
            }
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }
    }

    private HikariDataSource openConnexionPool(Properties properties) {
        HikariConfig config = new HikariConfig();
        config.setMinimumIdle(Integer.parseInt(properties.getProperty("minimumIdle")));
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("setMaximumPoolSize")));
        config.setAutoCommit(true);
        config.setIdleTimeout(Integer.parseInt(properties.getProperty("idleTimeout")));
        config.setConnectionTimeout(Integer.parseInt(properties.getProperty("connectionTimeout")));
        config.setConnectionTestQuery(properties.getProperty("connectionTestQuery"));
        config.setDataSourceClassName(properties.getProperty("dataSourceClassName"));

        config.addDataSourceProperty("user", properties.getProperty("dataSource.user"));
        config.addDataSourceProperty("password", properties.getProperty("dataSource.password"));
        config.addDataSourceProperty("databaseName", properties.getProperty("dataSource.databaseName"));
        config.addDataSourceProperty("serverName", properties.getProperty("dataSource.serverName"));
        config.addDataSourceProperty("portNumber", properties.getProperty("dataSource.serverPort"));

        HikariDataSource poolConnexion1 = new HikariDataSource(config);
        try {
            Connection conn = poolConnexion1.getConnection();
            if (conn == null) {
                return null;
            }
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, ex.getClass().getName(), ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
            poolConnexion1.close();
            return null;
        }

        return poolConnexion1;
    }

    /**
     * Permet de lire les préférences d'un thésaurus pour savoir si le
     * webservices est activé ou non
     *
     * @param idTheso
     */
    private boolean getStatusOfWebservices(String idTheso) {
        return true;
    }

/////////////////////////////////////////////////////    
///////////////////////////////////////////////////// 
    /*
     * Partie du REST pour produire du SKOS
     * 
     */
///////////////////////////////////////////////////// 
/////////////////////////////////////////////////////    
    /**
     * Cette fonction renvoie un concept par son id et par l'id du thésaurus
     *
     * @param name
     * @return
     */
    @Path("{theso}")
    @GET
    @Produces("application/xml;charset=UTF-8")
    public Response getConcept(@PathParam("theso") String name) {
/*        if (!getStatusOfWebservices(idTheso)) {
            ds.close();
            return Response.status(Status.SERVICE_UNAVAILABLE).entity("test").type(MediaType.TEXT_PLAIN).build();
        }*/

        ds.close();
        return Response.ok("thesaurus = " + name).header("Access-Control-Allow-Origin", "*").build();
    }
    
}