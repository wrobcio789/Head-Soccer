package input;

import utility.Callback;

public abstract class Input {

    protected boolean leftPressed = false;
    protected boolean rightPressed = false;
    protected boolean jumpPressed = false;

    protected Callback moveLeftCallback = null;
    protected Callback moveRightCallback = null;
    protected Callback jumpCallback = null;

    public void setMoveLeftCallback(Callback callback){
        moveLeftCallback = callback;
    }

    public void setMoveRightCallback(Callback callback){
        moveRightCallback = callback;
    }

    public void setJumpCallback(Callback callback){
        jumpCallback = callback;
    }

    public void update() {
        if(leftPressed)
            call(moveLeftCallback);
        if(rightPressed)
            call(moveRightCallback);
        if(jumpPressed)
            call(jumpCallback);
    }

    private void call(Callback callback){
        if(callback != null)
            callback.call();
    }
}
