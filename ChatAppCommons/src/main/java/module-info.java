module ChatAppCommons {

    requires javafx.fxml;
    requires javafx.media;
    requires javafx.controls;
    requires javafx.base;

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
    requires java.desktop;


//    exports  commons.remotes.server.impl;
    exports commons.remotes.server;
    exports commons.sharedmodels;
    exports commons.remotes.client;
    exports commons.utils;
}