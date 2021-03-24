package me.Cooltimmetje.TimmyCore.Data.Database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public final class QueryExecutor {

    private static final Logger logger = LoggerFactory.getLogger(QueryExecutor.class);

    private enum Operation {
        INT, LONG, STRING, DOUBLE, FLOAT, BOOLEAN
    }

    private ArrayList<Integer> usedPositions;
    private Connection c;
    private PreparedStatement ps;
    private ResultSet rs;
    private Operation lastOperation;
    private Object lastValue;

    public QueryExecutor(Query query) throws SQLException {
//        logger.info("Making new query of type " + query);
        c = HikariManager.getConnection();
        ps = c.prepareStatement(query.getQuery(), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        usedPositions = new ArrayList<>();
    }

    public QueryExecutor setInt(int position, int value) throws SQLException {
        if(isPositionUsed(position)) throw new IllegalArgumentException("Position " + position + " is not free.");
//        logger.info("Setting INT at position " + position + " with value " + value);
        usedPositions.add(position);
        ps.setInt(position, value);
        lastOperation = Operation.INT;
        lastValue = value;
        return this;
    }

    public QueryExecutor setLong(int position, long value) throws SQLException {
        if(isPositionUsed(position)) throw new IllegalArgumentException("Position " + position + " is not free.");
//        logger.info("Setting LONG at position " + position + " with value " + value);
        usedPositions.add(position);
        ps.setLong(position, value);
        lastOperation = Operation.LONG;
        lastValue = value;
        return this;
    }

    public QueryExecutor setDouble(int position, double value) throws SQLException {
        if(isPositionUsed(position)) throw new IllegalArgumentException("Position " + position + " is not free.");
//        logger.info("Setting DOUBLE at position " + position + " with value " + value);
        usedPositions.add(position);
        ps.setDouble(position, value);
        lastOperation = Operation.LONG;
        lastValue = value;
        return this;
    }

    public QueryExecutor setFloat(int position, float value) throws SQLException {
        if(isPositionUsed(position)) throw new IllegalArgumentException("Position " + position + " is not free.");
//        logger.info("Setting FLOAT at position " + position + " with value " + value);
        usedPositions.add(position);
        ps.setFloat(position, value);
        lastOperation = Operation.LONG;
        lastValue = value;
        return this;
    }

    public QueryExecutor setBoolean(int position, boolean value) throws SQLException {
        if(isPositionUsed(position)) throw new IllegalArgumentException("Position " + position + " is not free.");
//        logger.info("Setting BOOLEAN at position " + position + " with value " + value);
        usedPositions.add(position);
        ps.setBoolean(position, value);
        lastOperation = Operation.BOOLEAN;
        lastValue = value;
        return this;
    }

    public QueryExecutor setString(int position, String value) throws SQLException {
        if(isPositionUsed(position)) throw new IllegalArgumentException("Position " + position + " is not free.");
//        logger.info("Setting STRING at position " + position + " with value " + value);
        usedPositions.add(position);
        ps.setString(position, value);
        lastOperation = Operation.STRING;
        lastValue = value;
        return this;
    }

    public QueryExecutor and(int position) throws SQLException {
        if(isPositionUsed(position)) throw new IllegalArgumentException("Position " + position + " is not free.");
//        logger.info("Repeating last " + lastOperation + " value at position " + position);
        if(lastOperation == Operation.INT) ps.setInt(position, (int) lastValue);
        if(lastOperation == Operation.LONG) ps.setLong(position, (long) lastValue);
        if(lastOperation == Operation.STRING) ps.setString(position, (String) lastValue);
        if(lastOperation == Operation.FLOAT) ps.setFloat(position, (float) lastValue);
        if(lastOperation == Operation.DOUBLE) ps.setDouble(position, (double) lastValue);
        if(lastOperation == Operation.BOOLEAN) ps.setBoolean(position, (boolean) lastValue);
        return this;
    }

    private boolean isPositionUsed(int position){
        return usedPositions.contains(position);
    }

    public void execute() throws SQLException {
        logger.info("Executing query " + ps.toString());
        ps.execute();
    }

    public QueryResult executeQuery() throws SQLException {
        logger.info("Executing query " + ps.toString());
        rs = ps.executeQuery();
        return new QueryResult(rs);
    }

    public void close(){
        if(c != null){
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
