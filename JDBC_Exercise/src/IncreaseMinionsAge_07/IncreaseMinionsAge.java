package IncreaseMinionsAge_07;

import GetVillainsNames_01.Connector;
import jdk.jfr.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class IncreaseMinionsAge {
    private final static String UPDATE_AGE_QUERY = "UPDATE `minions`" +
            " SET `age` = age + 1" +
            " WHERE `id` = ?";
    private final static String UPDATE_NAME_QUERY = "UPDATE `minions`" +
            " SET `name` = LOWER(`name`)" +
            " WHERE `id` = ?";
    private final static String GET_MINIONS_NAME_AGE_QUERY = "SELECT `name`, `age` FROM minions";

    private final static String COLUMN_NAME_LABEL = "name";
    private final static String COLUMN_AGE_LABEL = "age";


    public static void main(String[] args) throws SQLException {

        Connection connection = Connector.getConnection();

        final int[] minionIds = Arrays.stream(new Scanner(System.in).nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt).toArray();

        updateMinionNameAndAge(connection, minionIds);
        printUpdatedMinions(connection);
    }

    private static void updateMinionNameAndAge(Connection connection, int[] minionIds) throws SQLException {
        for (int minionId : minionIds) {
            PreparedStatement updateAgeStatement = connection.prepareStatement(UPDATE_AGE_QUERY);
            updateAgeStatement.setInt(1, minionId);
            updateAgeStatement.execute();

            PreparedStatement updateNameStatement = connection.prepareStatement(UPDATE_NAME_QUERY);
            updateNameStatement.setInt(1, minionId);
            updateNameStatement.execute();
        }
    }

    private static void printUpdatedMinions(Connection connection) throws SQLException {
        PreparedStatement minionsAgeAndNameStatement = connection.prepareStatement(GET_MINIONS_NAME_AGE_QUERY);
        ResultSet minionsResultSet = minionsAgeAndNameStatement.executeQuery();

        while (minionsResultSet.next()) {
            String minionName = minionsResultSet.getString(COLUMN_NAME_LABEL);
            String minionAge = minionsResultSet.getString(COLUMN_AGE_LABEL);

            System.out.println(minionName + " " + minionAge);
        }
    }
}
