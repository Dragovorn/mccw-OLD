import com.dragovorn.mccw.utils.Passwords;
import com.dragovorn.mccw.utils.SQL;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TestSQL {

    @Test
    public void testSQL() throws SQLException, ClassNotFoundException {
        SQL sql = new SQL("dragovorn.com", 8080, "mccw", "mccw", Passwords.sql);

        String preStatement = "INSERT INTO players VALUES (?, ?, 0.0, 0, ?, ?, 0, 0, 0, 0, 0)";

        PreparedStatement preparedStatement = sql.getConnection().prepareStatement(preStatement);

        preparedStatement.setString(1, "fa2daf04-02e9-4fe2-a70c-b38db29afc47");
        preparedStatement.setString(2, "Dragovorn");
        preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

        preparedStatement.executeUpdate();

        System.out.println(new Timestamp(System.currentTimeMillis()));
    }
}