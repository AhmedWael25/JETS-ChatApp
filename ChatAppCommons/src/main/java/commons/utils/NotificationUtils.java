package commons.utils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationUtils {
    public static void showNotification(String title,String msg,EventHandler<ActionEvent> action,Object owner,Image image){

//        ImageView icon = new ImageView(image) ;
        Circle icon = new Circle();
        icon.setRadius(15);
        icon.setFill(new ImagePattern(image));
        Notifications threshold = Notifications.create().title("Notification");
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(msg)
                .graphic(icon)
                .hideAfter(Duration.seconds(2.0))
                .threshold(2,threshold)
                .position(Pos.TOP_CENTER)

//                .darkStyle()
                .onAction(action)
                .owner(owner);
        //to run in threads
        Platform.runLater(() -> notificationBuilder.show());
    }
}
