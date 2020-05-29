package input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardInput extends Input {

    public KeyboardInput(Scene scene, KeyCode leftKey, KeyCode rightKey, KeyCode jumpKey){
        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == leftKey)
                leftPressed = true;
            if(keyEvent.getCode() == rightKey)
                rightPressed = true;
            if(keyEvent.getCode() == jumpKey)
                jumpPressed = true;
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> {
            if(keyEvent.getCode() == leftKey)
                leftPressed = false;
            if(keyEvent.getCode() == rightKey)
                rightPressed = false;
            if(keyEvent.getCode() == jumpKey)
                jumpPressed = false;
        });
    }
}
