package input;

import utility.Callback;

public abstract class Input {

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

    protected void call(Callback callback){
        if(callback != null)
            callback.call();
    }
}
