package senberg.windows.demos;

import com.sun.jna.platform.win32.WinDef;
import senberg.windows.Windows;

public class GetWindows {
    public static void main(String[] args) {
        for (WinDef.HWND hWnd : Windows.getWindows()) {
            System.out.println("Window " + Windows.getWindowText(hWnd));
        }
    }
}
