module jets.chatclient {
    requires javafx.fxml;
    requires  javafx.media;
    requires javafx.controls;
    requires javafx.base;
    requires javafx.graphics;

    requires java.sql;
    requires mysql.connector.java;
    requires java.naming;

    requires org.controlsfx.controls;
    requires com.jfoenix;
    requires java.rmi;


    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.materialdesign2;
    requires ChatAppCommons;
    requires java.desktop;
    requires jakarta.xml.bind;


    //opens  jets.chatclient;
    opens jets.chatclient.gui.controllers to javafx.fxml;
    opens jets.chatclient.gui.models to helpers, jakarta.xml.bind;
    exports jets.chatclient;
}