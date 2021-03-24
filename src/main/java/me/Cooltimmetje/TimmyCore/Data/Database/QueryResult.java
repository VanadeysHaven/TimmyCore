package me.Cooltimmetje.TimmyCore.Data.Database;

import me.Cooltimmetje.TimmyCore.Utilities.TableUtilities.TableArrayGenerator;
import me.Cooltimmetje.TimmyCore.Utilities.TableUtilities.TableDrawer;
import me.Cooltimmetje.TimmyCore.Utilities.TableUtilities.TableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public final class QueryResult {

    private static final Logger logger = LoggerFactory.getLogger(QueryResult.class);

    private ResultSet rs;
    private boolean verboseLogging;

    public QueryResult(ResultSet rs, boolean verboseLogging) throws SQLException {
        this.rs = rs;
        this.verboseLogging = verboseLogging;

//        logger.info("QueryResult Created: \n" + generateTable());
    }

    public QueryResult(ResultSet rs) throws SQLException {
        this(rs, false);
    }

    public boolean nextResult() throws SQLException {
        if(rs.next()) {
            if(verboseLogging) logger.info("Getting next result");
            return true;
        } else {
            if(verboseLogging) logger.info("Result set is empty...");
            return false;
        }
    }

    public int getInt(String columnReference) throws SQLException {
        int val = rs.getInt(columnReference);
        if(verboseLogging) logger.info("Returning INT value " + val + " from column " + columnReference);
        return val;
    }

    public long getLong(String columnReference) throws SQLException {
        long val = rs.getLong(columnReference);
        if(verboseLogging) logger.info("Returning LONG value " + val + " from column " + columnReference);
        return val;
    }

    public double getDouble(String columnReference) throws SQLException {
        double val = rs.getDouble(columnReference);
        if(verboseLogging) logger.info("Returning DOUBLE value " + val + " from column " + columnReference);
        return val;
    }

    public float getFloat(String columnReference) throws SQLException {
        float val = rs.getFloat(columnReference);
        if(verboseLogging) logger.info("Returning FLOAT value " + val + " from column " + columnReference);
        return val;
    }

    public boolean getBoolean(String columnReference) throws SQLException {
        boolean val = rs.getBoolean(columnReference);
        if(verboseLogging) logger.info("Returning BOOLEAN value " + val + " from column " + columnReference);
        return val;
    }

    public String getString(String columnReference) throws SQLException {
        String val = rs.getString(columnReference);
        if(verboseLogging) logger.info("Returning STRING value " + val + " from column " + columnReference);
        return val;
    }


    private String generateTable() throws SQLException {
        rs.beforeFirst();
        ResultSetMetaData rsmd = rs.getMetaData();
        int numOfColumns = rsmd.getColumnCount();
        TableArrayGenerator tag = new TableArrayGenerator();
        TableRow topRow = new TableRow();
        for(int i=1; i <= numOfColumns; i++){
            topRow.add(rsmd.getColumnLabel(i));
        }
        tag.addRow(topRow);
        while (rs.next()){
            TableRow tr = new TableRow();
            for(int i=1; i <= numOfColumns; i++){
                tr.add(rs.getString(i));
            }
            tag.addRow(tr);
        }

        rs.beforeFirst();
        return new TableDrawer(tag).drawTable();
    }



}
