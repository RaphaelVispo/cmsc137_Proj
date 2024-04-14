module cmsc137 {
    requires javafx.controls;
    requires javafx.fxml;

    opens cmsc137 to javafx.fxml;
    exports cmsc137;
}
