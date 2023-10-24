module com.coursework.eshop {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.coursework.eshop to javafx.fxml;
    exports com.coursework.eshop;
    exports com.coursework.eshop.fxController;
    opens com.coursework.eshop.fxController to javafx.fxml;
}