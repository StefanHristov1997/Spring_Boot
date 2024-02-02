package IncreaseAgeStoredProcedure_06;

import GetVillainsNames_01.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class IncreaseAgeStoredProcedure {
    private final static String CALL_INCREASE_AGE_PROCEDURE = "CALL usp_get_older (?)";
    private final static String GET_MINION_NAME_AND_AGE = "SELECT `name`, `age` FROM minions\n" +
            "WHERE id = ?;";

    private final static String COLUMN_LABEL_NAME = "name";
    private final static String COLUMN_LABEL_AGE = "age";

    public static void main(String[] args) throws SQLException {
        Connection connection = Connector.getConnection();

        int minionId = new Scanner(System.in).nextInt();

        getCallIncreaseAgeProcedure(connection, minionId);
        printIncreasedMinion(connection, minionId);
    }

    private static void getCallIncreaseAgeProcedure(Connection connection, int minionId) throws SQLException {
        PreparedStatement callProcedureStatement = connection.prepareStatement(CALL_INCREASE_AGE_PROCEDURE);
        callProcedureStatement.setInt(1, minionId);
        callProcedureStatement.execute();
    }

    private static void printIncreasedMinion(Connection connection, int minionId) throws SQLException {
        PreparedStatement minionNameAndAgeStatement = connection.prepareStatement(GET_MINION_NAME_AND_AGE);
        minionNameAndAgeStatement.setInt(1, minionId);
        ResultSet minionDataResultSet = minionNameAndAgeStatement.executeQuery();

        while (minionDataResultSet.next()) {
            String minionName = minionDataResultSet.getString(COLUMN_LABEL_NAME);
            int minionAge = minionDataResultSet.getInt(COLUMN_LABEL_AGE);

            System.out.println(minionName + " " + minionAge);
        }
    }
}
