package AddMinion_03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class MinionsUtil {
    private final static String GET_TOWN_ID = "SELECT `id` FROM `towns` WHERE `name` = ?";
    private final static String GET_VILLAIN_ID = "SELECT `id` FROM villains WHERE name = ?";
    private final static String GET_MINION_ID = "SELECT `id` FROM minions WHERE name = ?";
    private final static String INSERT_TOWN = "INSERT INTO `towns` (name) VALUES (?)";
    private final static String INSERT_VILLAIN = "INSERT INTO `villains`(name, evilness_factor) VALUES (?, 'evil')";
    private final static String INSERT_MINION = "INSERT INTO minions (name, age, town_id) VALUES (?,?, (SELECT towns.id FROM towns where towns.name = ?))";
    private final static String INSERT_MINIONS_VILLAINS =
            " INSERT INTO minions_villains (minion_id, villain_id)" +
                    " VALUES (?, ?)";
    private final static String INSERT_TOWN_PRINT_FORMAT = "Town %s was added to the database.";
    private final static String INSERT_VILLAIN_PRINT_FORMAT = "Villain %s was added to the database.";
    private final static String INSERT_MINION_PRINT_FORMAT = "Successfully added %s to be minion of %s.";

    static ResultSet checkTown(Connection connection, String townName) throws SQLException {
        final PreparedStatement townStatement = connection.prepareStatement(GET_TOWN_ID);
        townStatement.setString(1, townName);
        return townStatement.executeQuery();
    }

    static ResultSet checkVillain(Connection connection, String villainName) throws SQLException {
        final PreparedStatement villainStatement = connection.prepareStatement(GET_VILLAIN_ID);
        villainStatement.setString(1, villainName);
        return villainStatement.executeQuery();
    }

    static String getInsertTown(String townName, Connection connection) throws SQLException {
        final PreparedStatement insetTownStatement = connection.prepareStatement(INSERT_TOWN);
        insetTownStatement.setString(1, townName);
        insetTownStatement.execute();
        return String.format(INSERT_TOWN_PRINT_FORMAT, townName);
    }

    static String getInsertVillain(String villainName, Connection connection) throws SQLException {
        final PreparedStatement insertVillainStatement = connection.prepareStatement(INSERT_VILLAIN);
        insertVillainStatement.setString(1, villainName);
        insertVillainStatement.execute();
        return String.format(INSERT_VILLAIN_PRINT_FORMAT, villainName);
    }

    static String getInsertMinion(String villainName, String minionName, int minionAge, String townName, Connection connection) throws SQLException {
        final PreparedStatement insertMinionStatement = connection.prepareStatement(INSERT_MINION);
        insertMinionStatement.setString(1, minionName);
        insertMinionStatement.setInt(2, minionAge);
        insertMinionStatement.setString(3, townName);
        insertMinionStatement.execute();
        return String.format(INSERT_MINION_PRINT_FORMAT, minionName, villainName);
    }

    static void getInsertMinionsVillains(String minionName, String villainName, Connection connection) throws SQLException {
        final PreparedStatement minionIDStatement = connection.prepareStatement(GET_MINION_ID);
        minionIDStatement.setString(1,minionName);
        ResultSet minionIdResultSet = minionIDStatement.executeQuery();
        minionIdResultSet.next();
        int minionID = minionIdResultSet.getInt("id");

        final PreparedStatement villainIDStatement = connection.prepareStatement(GET_VILLAIN_ID);
        villainIDStatement.setString(1,villainName);
        ResultSet villainIdResultSet = villainIDStatement.executeQuery();
        villainIdResultSet.next();
        int villainID = villainIdResultSet.getInt("id");

        final PreparedStatement insertMinionsVillainsStatement = connection.prepareStatement(INSERT_MINIONS_VILLAINS);
        insertMinionsVillainsStatement.setInt(1, minionID);
        insertMinionsVillainsStatement.setInt(2, villainID);
        insertMinionsVillainsStatement.execute();
    }

}
