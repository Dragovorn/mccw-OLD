import com.dragovorn.mccw.utils.Passwords;
import com.dragovorn.mccw.utils.SQL;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class TestSQL {

    @Test
    public void testSQL() throws SQLException, ClassNotFoundException {
        SQL sql = new SQL("dragovorn.com", 8080, "mccw", "mccw", Passwords.sql);

        String preStatment = "INSERT INTO players VALUES (?, ?, 0.0, 0, ?, ?, 0, 0, 0, 0, 0)";

        PreparedStatement preparedStatement = sql.getConnection().prepareStatement(preStatment);

        preparedStatement.setString(1, "fa2daf04-02e9-4fe2-a70c-b38db29afc47");
        preparedStatement.setString(2, "Dragovorn");
        preparedStatement.setDate(3, new java.sql.Date(System.currentTimeMillis()));
        preparedStatement.setDate(4, new java.sql.Date(new Date().getTime()));

        preparedStatement.executeUpdate();

        System.out.println(new Date(System.currentTimeMillis()));
    }
}