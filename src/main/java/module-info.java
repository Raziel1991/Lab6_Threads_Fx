module com.example.lab6_threads_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core; // Optional, if you're using BootstrapFX

    opens com.example.lab6_threads_fx to javafx.fxml;
    exports com.example.lab6_threads_fx;
}
