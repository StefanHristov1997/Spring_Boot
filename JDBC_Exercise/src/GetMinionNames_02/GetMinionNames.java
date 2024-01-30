package GetMinionNames_02;

import GetVillainsNames_01.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

 class GetMinionNames {
    private final static String GET_VILLAIN_NAME = "SELECT `name` FROM villains WHERE `id` = ?";
    private final static String GET_MINIONS_NAME_AND_AGE = "SELECT `name`, `age` FROM minions AS m" +
            " JOIN minions_villains mv on mv.`minion_id` = m.`id`" +
            " WHERE villain_id = ?";
    private final static String INVALID_VILLAIN_ID = "No villain with ID %d exists in the database.";
    private final static String VILLAIN_NAME_PRINT_FORMAT = "Villain: %s\n";
    private final static String MINIONS_NAME_AGE_PRINT_FORMAT = "%d. %s %d\n";

    public static void main(String[] args) throws SQLException {
        Connection connection = Connector.getConnection();
        int villainID = new Scanner(System.in).nextInt();

        PreparedStatement villainPreparedStatement = connection.prepareStatement(GET_VILLAIN_NAME);
        villainPreparedStatement.setInt(1, villainID);
        ResultSet villainNameResultSet = villainPreparedStatement.executeQuery();

        PreparedStatement minionsPreparedStatement = connection.prepareStatement(GET_MINIONS_NAME_AND_AGE);
        minionsPreparedStatement.setInt(1, villainID);
        ResultSet minionsResultSet = minionsPreparedStatement.executeQuery();

        if (!villainNameResultSet.next()) {
            connection.close();
            System.out.printf(INVALID_VILLAIN_ID, villainID);
            return;
        }

        final String villainNamePrint = villainNameResultSet.getString(MinionsTableColumns.COLUMN_NAME);
        System.out.printf(VILLAIN_NAME_PRINT_FORMAT, villainNamePrint);

        for (int index = 1; minionsResultSet.next(); index++) {
            final String minionName = minionsResultSet.getString(MinionsTableColumns.COLUMN_NAME);
            final int minionAge = minionsResultSet.getInt(MinionsTableColumns.COLUMN_AGE);
            System.out.printf(MINIONS_NAME_AGE_PRINT_FORMAT, index, minionName, minionAge);
        }
    }
}
