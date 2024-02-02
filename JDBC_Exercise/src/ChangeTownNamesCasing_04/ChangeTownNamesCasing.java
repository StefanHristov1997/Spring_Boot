package ChangeTownNamesCasing_04;

import GetVillainsNames_01.Connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChangeTownNamesCasing {
    private final static String GET_COUNT_OF_TOWNS = "SELECT COUNT(id) AS count_towns FROM towns AS t WHERE t.country = ?";
    private final static String GET_TOWN_NAMES = "SELECT name FROM towns WHERE country = ?";
    private final static String UPDATE_TOWN_NAMES = "UPDATE towns" +
            " SET name = UPPER(name)" +
            " WHERE country = ?";
    private final static String COUNT_TOWNS_LABEL = "count_towns";
    private final static String TOWN_NAME_LABEL = "name";
    private final static String PRINT_NUMBER_OF_TOWNS_FORMAT = "%d town names were affected.\n";

    public static void main(String[] args) throws SQLException {
        final Connection connection = Connector.getConnection();

        final String country = new Scanner(System.in).next();

        printCountOfTowns(connection, country);
        printUpdatedTowns(connection, country);

        connection.close();
    }

    private static void printCountOfTowns(Connection connection, String country) throws SQLException {
        final PreparedStatement getTownStatement = connection.prepareStatement(GET_COUNT_OF_TOWNS);
        getTownStatement.setString(1, country);
        final ResultSet townResultSet = getTownStatement.executeQuery();

        townResultSet.next();
        final int countTowns = townResultSet.getInt(COUNT_TOWNS_LABEL);
        if (countTowns < 1) {
            System.out.println("No town names were affected.");
            return;
        }
        System.out.printf(PRINT_NUMBER_OF_TOWNS_FORMAT, countTowns);
    }

    private static void printUpdatedTowns(Connection connection, String country) throws SQLException {
        final PreparedStatement updateTownNameStatement = connection.prepareStatement(UPDATE_TOWN_NAMES);
        updateTownNameStatement.setString(1, country);
        updateTownNameStatement.executeUpdate();

        final PreparedStatement getTownNamesStatement = connection.prepareStatement(GET_TOWN_NAMES);
        getTownNamesStatement.setString(1, country);
        final ResultSet townNamesResultSet = getTownNamesStatement.executeQuery();

        final List<String> towns = new ArrayList<>();

        while (townNamesResultSet.next()) {
            final String currentTown = townNamesResultSet.getString(TOWN_NAME_LABEL);
            towns.add(currentTown);
        }

        if (towns.isEmpty()) {
            return;
        }
        System.out.println(towns);
    }
}
