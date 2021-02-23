package commons.utils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationUtils {
    public static void showNotification(String title,String msg,EventHandler<ActionEvent> action,Object owner,String image){

        ImageView icon = new ImageView(ImageEncoderDecoder.getDecodedImage(image)) ;
        Notifications threshold = Notifications.create().title("Notification");
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(msg)
                .graphic(icon)
                .hideAfter(Duration.seconds(3.0))
                .threshold(3,threshold)
                .position(Pos.BOTTOM_RIGHT)
//                .darkStyle()
                .onAction(action)
                .owner(owner);
        //to run in threads
        System.out.println("asasasd"+owner);
        Platform.runLater(() -> notificationBuilder.show());
    }
    public static void showServerNotification(String msg,Object owner,String serverImg){

      ImageView icon = new ImageView(ImageEncoderDecoder.getDecodedImage(serverImg)) ;
      icon.setFitWidth(30);
      icon.setFitHeight(30);
        Notifications threshold = Notifications.create().title("ANNOUNCEMENT");
        Notifications notificationBuilder = Notifications.create()
                .title("ANNOUNCEMENT")
                .graphic(icon)
                .text(msg)
                .hideAfter(Duration.INDEFINITE)
                .threshold(3,threshold)
                .position(Pos.BOTTOM_RIGHT)
                .darkStyle()
                .owner(owner);
        //to run in threads
        Platform.runLater(() -> notificationBuilder.show());
    }
}
