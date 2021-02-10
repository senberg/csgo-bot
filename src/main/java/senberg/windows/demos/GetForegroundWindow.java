package senberg.windows.demos;

import com.sun.jna.platform.win32.WinDef;
import senberg.windows.Windows;

public class GetForegroundWindow {
    public static void main(String[] args) {
        WinDef.HWND hWnd = Windows.getForegroundWindow();
        System.out.println("Foreground Window " + Windows.getWindowText(hWnd));
    }
}
