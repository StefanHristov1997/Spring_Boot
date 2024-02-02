package AddMinion_03;

import GetVillainsNames_01.Connector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

class AddMinion {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        String[] minionData = scanner.nextLine().split("\\s+");
        final String minionName = minionData[1];
        final int minionAge = Integer.parseInt(minionData[2]);
        final String townName = minionData[3];

        String[] villainData = scanner.nextLine().split("\\s+");
        final String villainName = villainData[1];

        final Connection connection = Connector.getConnection();

        if (!MinionsUtil.checkTown(connection, townName).next()) {
            System.out.println(MinionsUtil.getInsertTown(townName, connection));
        }

        if (!MinionsUtil.checkVillain(connection, villainName).next()) {
            System.out.println(MinionsUtil.getInsertVillain(villainName, connection));
        }

        System.out.println(MinionsUtil.getInsertMinion(villainName, minionName, minionAge, townName, connection));
        MinionsUtil.getInsertMinionsVillains(minionName, villainName, connection);

        connection.close();
    }


}
