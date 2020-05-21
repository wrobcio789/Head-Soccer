package input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardInput extends Input {

    public KeyboardInput(Scene scene, KeyCode leftKey, KeyCode rightKey, KeyCode jumpKey){
        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == leftKey)
                call(moveLeftCallback);
            if(keyEvent.getCode() == rightKey)
                call(moveRightCallback);
            if(keyEvent.getCode() == jumpKey)
                call(jumpCallback);
        });
    }
}
