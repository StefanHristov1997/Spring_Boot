package PrintAllMinionNames_05;

import GetVillainsNames_01.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrintAllMinionNames {
    private final static String GET_ALL_MINIONS_NAME = "SELECT `name` FROM `minions`";
    private final static String COLUMN_NAME_LABEL = "name";

    public static void main(String[] args) throws SQLException {
        Connection connection = Connector.getConnection();

        List<String> minionsNames = getAllMinionsNames(connection);

        printMinionsNamesInOrder(minionsNames);
    }

    private static List<String> getAllMinionsNames(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_MINIONS_NAME);
        ResultSet minionsResultSet = preparedStatement.executeQuery();

        List<String> minionNames = new ArrayList<>();

        while (minionsResultSet.next()) {
            String minionName = minionsResultSet.getString(COLUMN_NAME_LABEL);
            minionNames.add(minionName);
        }

        return minionNames;
    }

    public static void printMinionsNamesInOrder(List<String> minions){
        for (int index = 0; index < minions.size() / 2; index++) {
            System.out.println(minions.get(0 + index));
            System.out.println(minions.get(minions.size() - 1 - index));
        }

    }
}
