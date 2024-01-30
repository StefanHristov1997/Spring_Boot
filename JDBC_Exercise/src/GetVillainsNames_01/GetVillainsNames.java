package GetVillainsNames_01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class GetVillainsNames {
    private final static String GET_VILLAINS_NAMES_AND_MINIONS =
            "SELECT v.`name`, COUNT(DISTINCT mv.`minion_id`) AS count_minions FROM `villains` AS v" +
                    " JOIN `minions_villains` AS mv ON mv.`villain_id` = v.`id`" +
                    " GROUP BY mv.`villain_id`" +
                    " HAVING `count_minions` > ?" +
                    " ORDER BY `count_minions` DESC";

    private final static String COLUMN_NAME = "name";
    private final static String COLUMN_COUNT_MINIONS = "count_minions";
    private final static String RESULT_FORMAT = "%s %d";

    public static void main(String args[]) throws SQLException {
        Connection connection = Connector.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(GET_VILLAINS_NAMES_AND_MINIONS);
        int minimumMinions = new Scanner(System.in).nextInt();
        preparedStatement.setInt(1, minimumMinions);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String villainName = resultSet.getString(COLUMN_NAME);
            int numberOfMinions = resultSet.getInt(COLUMN_COUNT_MINIONS);
            printResult(villainName, numberOfMinions);
        }
    }

    private static void printResult(String villainName, int numberOfMinions) {
        System.out.printf(RESULT_FORMAT, villainName, numberOfMinions);
    }
}
