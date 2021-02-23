module jets.chatserver {
    requires javafx.fxml;
    requires  javafx.media;
    requires javafx.controls;
    requires javafx.base;


    requires java.sql;
    requires mysql.connector.java;
    requires java.naming;

    requires org.controlsfx.controls;
    requires com.jfoenix;
    requires java.sql.rowset;
    requires java.rmi;


    requires eu.hansolo.tilesfx;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.materialdesign2;

    requires ChatAppCommons;
    requires ab;

    opens jets.chatserver.gui.controllers to javafx.fxml;

    exports  jets.chatserver.DBModels to java.rmi;
    exports jets.chatserver.database.dao ;
    exports jets.chatserver;
}