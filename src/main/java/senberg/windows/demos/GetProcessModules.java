package senberg.windows.demos;

import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import senberg.windows.Windows;
import senberg.windows.libraries.Psapi;

public class GetProcessModules {
    public static void main(String[] args) {
        for (WinDef.HWND hWnd : Windows.getWindows()) {
            System.out.println("Window " + Windows.getWindowText(hWnd));
            WinNT.HANDLE process = Windows.getProcess(hWnd);
            System.out.println("\tProcess " + process);

            for (WinDef.HMODULE module : Windows.getProcessModules(process)) {
                System.out.println("\t\tModule " + process);
                System.out.println("\t\tBase Name " + Windows.getModuleBaseName(process, module));

                Psapi.MODULEINFO moduleInformation = Windows.getModuleInformation(process, module);
                System.out.println("Entry Point " + moduleInformation.EntryPoint);
                System.out.println("Base of DLL " + moduleInformation.lpBaseOfDll);
                System.out.println("Size of Image " + moduleInformation.SizeOfImage);
            }
        }
    }
}
