package com.example.dao;

import com.example.entity.Notification;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class NotificationDao {
    private Sql2o sql2o = new Sql2o("jdbc:mysql://mysql.agh.edu.pl:3306/sadowski", "sadowski", "xPjFWAM00pYfJAP6");

    public void insertNotification(String content, int groupId, int importance, int authorId) {
        String insertSql = "INSERT into notification VALUES(null, :content, :groupId, :importance, :authorId, null)";

        try (Connection con = sql2o.open()) {
            con.createQuery(insertSql)
                    .addParameter("content", content)
                    .addParameter("groupId", groupId)
                    .addParameter("importance", importance)
                    .addParameter("authorId", authorId)
                    .executeUpdate();
        }
    }


    public Notification getNewestNotification() {
        try (Connection con = sql2o.open()) {
            final String query = "SELECT * FROM notification ORDER BY createdOn DESC LIMIT 1";

            return con.createQuery(query)
                    .executeAndFetch(Notification.class).get(0);
        }
    }

    public List<Notification> getNotifications() {
        try (Connection con = sql2o.open()) {
            final String query = "SELECT * FROM notification";

            return con.createQuery(query)
                    .executeAndFetch(Notification.class);
        }
    }

    public static void main(String[] args) {
        System.out.println(new NotificationDao().getNewestNotification());
    }
}
