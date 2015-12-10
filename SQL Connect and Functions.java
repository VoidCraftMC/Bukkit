package me.fbmc.economy;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main
extends JavaPlugin
implements Listener {
    public void onEnable() {
        PluginManager manager = this.getServer().getPluginManager();
        manager.registerEvents((Listener)this, (Plugin)this);
        try {
            this.updateStatus(1, Bukkit.getOnlinePlayers().length);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask((Plugin)this, new Runnable(){

            @Override
            public void run() {
                try {
                    Main.this.updateStatus(1, Bukkit.getOnlinePlayers().length);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 80);
    }

    public void onDisable() {
        try {
            this.updateStatus(0, 0);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeSql(String sql) throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("user");
        dataSource.setPassword("password");
        dataSource.setServerName("host");
        dataSource.setDatabaseName("database");
        Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        int rs = stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
    }

    public void querySql(String sql) throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("user");
        dataSource.setPassword("password");
        dataSource.setServerName("host");
        dataSource.setDatabaseName("database");
        Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            int n = rs.getInt("playernumber");
        }
        stmt.close();
        conn.close();
    }

    public boolean main(String id, String title, String content, String date, String by) throws SQLException {
        this.executeSql("INSERT INTO posts ( `id`, `title`, `content`, `date`, `by` ) VALUES ('" + id + "', '" + title + "', '" + content + "', '" + date + "', '" + by + "' )");
        return false;
    }

    public void updateStatus(int online, int playernumber) throws SQLException {
        this.executeSql("UPDATE serverstatus SET online='" + online + "', playernumber='" + playernumber + "'");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sql")) {
            try {
                MysqlDataSource dataSource = new MysqlDataSource();
                dataSource.setUser("user");
                dataSource.setPassword("password");
                dataSource.setServerName("host");
                dataSource.setDatabaseName("database");
                Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM `serverstatus`");
                if (rs.next()) {
                    int str1;
                    int playernumber = str1 = rs.getInt("playernumber");
                    sender.sendMessage(String.valueOf(playernumber) + " Players online");
                }
                stmt.close();
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

}

