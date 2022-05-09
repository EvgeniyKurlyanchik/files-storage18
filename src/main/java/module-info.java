module ru.gb {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.netty.transport;
    requires io.netty.codec;
    requires io.netty.buffer;
    requires common;
    opens ru.gb to javafx.fxml;
    exports ru.gb;
}
