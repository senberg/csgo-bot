package senberg.windows.demos;

import com.sun.jna.platform.win32.WinDef;
import senberg.windows.Windows;

public class ActivateWindow {
    public static void main(String[] args) {
        WinDef.HWND hWnd = Windows.getForegroundWindow();
        Windows.activateWindow(hWnd);
    }
}
