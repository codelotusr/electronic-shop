module com.coursework.eshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires mysql.connector.j;


    opens com.coursework.eshop to javafx.fxml;
    exports com.coursework.eshop;

    opens com.coursework.eshop.fxController to javafx.fxml;
    exports com.coursework.eshop.fxController to javafx.fxml;

    opens com.coursework.eshop.model to lombok;
    exports com.coursework.eshop.model to lombok;
}