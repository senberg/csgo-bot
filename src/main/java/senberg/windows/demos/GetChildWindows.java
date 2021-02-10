package senberg.windows.demos;

import com.sun.jna.platform.win32.WinDef;
import senberg.windows.Windows;

public class GetChildWindows {
    public static void main(String[] args) {
        for (WinDef.HWND hWnd : Windows.getWindows()) {
            System.out.println("Window " + hWnd + " " + Windows.getWindowText(hWnd));

            for (WinDef.HWND child : Windows.getChildWindows(hWnd)) {
                System.out.println("\tChild " + child + " " + Windows.getWindowText(child));
            }
        }
    }
}
