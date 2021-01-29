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


    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.materialdesign2;

    opens jets.chatserver.gui.controllers to javafx.fxml;

    exports jets.chatserver;
}