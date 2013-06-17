package com.astrider.sfc.app.lib.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;

public class TestDataManager {
    private String testDataDirectory;
    private String driverClassName;
    private String databaseURL;
    private String databaseUserName;
    private String databasePassword;
    private String databaseScheme;
    private String[] tables = {
        "TRI_COOK_LOGS_COOK_LOG_ID",
        "TRI_MATERIALS_MATERIAL_ID",
        "TRI_MEAL_NUT_AMOUNTS_MEAL_ID",
        "TRI_NUTRIENTS_NUTRIENT_ID",
        "TRI_RECIPES_RECIPE_ID",
        "TRI_USERS_USER_ID",
        "TRI_WEEKLY_NUT_AMOUNTS_WEEK_ID"
    };

    public TestDataManager(String testDataDirectory, String driverClassName,
            String databaseURL, String databaseUserName,
            String databasePassword, String databaseScheme) {
        this.testDataDirectory = testDataDirectory;
        this.driverClassName = driverClassName;
        this.databaseURL = databaseURL;
        this.databaseUserName = databaseUserName;
        this.databasePassword = databasePassword;
        this.databaseScheme = databaseScheme;
    }

    public IDatabaseConnection getIDatabaseConnection()
        throws ClassNotFoundException, SQLException, IOException, DatabaseUnitException {
        IDatabaseConnection connection = new DatabaseConnection(
                getConnection(), databaseScheme);

        // for Oracle 10g idiom
        DatabaseConfig config = connection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
                new Oracle10DataTypeFactory());

        return connection;
    }

    public Connection getConnection() throws ClassNotFoundException,
            SQLException, DatabaseUnitException, IOException {
        Class.forName(driverClassName);
        Connection jdbcConnection = DriverManager.getConnection(databaseURL, databaseUserName, databasePassword);

        return jdbcConnection;
    }

    private void backupTables(String[] targetTableNames, String dataFileName)
        throws IOException, SQLException, DatabaseUnitException, ClassNotFoundException {
        IDatabaseConnection connection = getIDatabaseConnection();
        IDataSet partialDataSet = connection.createDataSet(targetTableNames);
        XlsDataSet.write(partialDataSet, new FileOutputStream(new File(dataFileName)));
        connection.close();
        return;
    }

    public String createBackupFileName(String dataFileName) throws Exception {
        String[] tokens = dataFileName.split("\\.(?=[^\\.]+$)");
        if (tokens.length >= 2) {
            return tokens[0] + "_backup.xls";
        } else {
            throw new Exception("data file name is wrong.");
        }
    }
    public void restoreTestDataInXLS(String dataFileName) throws Exception {
        String backupFileName = testDataDirectory + createBackupFileName(dataFileName);
        IDataSet partialDataSet = null;
        partialDataSet = new XlsDataSet(new FileInputStream(new File(backupFileName)));
        IDatabaseConnection connection = getIDatabaseConnection();
        DatabaseOperation.CLEAN_INSERT.execute(connection, partialDataSet);
        connection.close();
        return;
    }

    public void loadTestDataInXLS(String dataFileName) throws Exception {
        disableTriggers();

        // データインポート
        IDataSet partialDataSet = null;
        partialDataSet = new XlsDataSet(new FileInputStream(new File(testDataDirectory + dataFileName)));

        // データのバックアップ
        String[] backupTableNames = partialDataSet.getTableNames();
        String backupFileName = testDataDirectory + createBackupFileName(dataFileName);
        backupTables(backupTableNames, backupFileName);

        // CLEANしてからインポート
        IDatabaseConnection connection = getIDatabaseConnection();
        DatabaseOperation.CLEAN_INSERT.execute(connection, partialDataSet);
        connection.close();

        enableTriggers();
    }

    public static String getWebInfPath() {
        return "/Users/astrider/Documents/workspace/recruit/sante/WEB-INF/test-data/";
    }

    public void disableTriggers() {
        try {
            Connection con = getConnection();
            for (String t : tables) {
                String sql = "ALTER TRIGGER " + t + " DISABLE";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.execute();
                pstmt.close();
            }
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DatabaseUnitException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enableTriggers() {
        try {
            Connection con = getConnection();
            for (String t : tables) {
                String sql = "ALTER TRIGGER " + t + " ENABLE";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.execute();
                pstmt.close();
            }
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DatabaseUnitException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshSequences() {
        try {
            Connection con = getConnection();
            for (String t : tables) {
                String sql = "ALTER TRIGGER " + t + " ENABLE";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.execute();
                pstmt.close();
            }
            con.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DatabaseUnitException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
