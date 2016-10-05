import com.dragovorn.mccw.utils.Passwords;
import com.dragovorn.mccw.utils.SQL;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestSQL {

    @Test
    public void testSQL() throws SQLException, ClassNotFoundException {
        SQL sql = new SQL("dragovorn.com", 1433, "mccw", "mccw", Passwords.sql);

        Statement statement = sql.getConnection().createStatement();

        ResultSet res = statement.executeQuery("SELECT games FROM player WHERE uuid='fa2daf04-02e9-4fe2-a70c-b38db29afc47';");
        res.next();

        System.out.println(res.getInt("games"));
    }
}