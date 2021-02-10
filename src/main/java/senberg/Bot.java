package senberg;

import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import senberg.windows.Windows;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bot {

    public static void main(String[] args) throws Exception {
        Set<HWND> activeGames = new HashSet<>();


        while (true) {
            List<HWND> games = Windows.getWindowsByTitle("Counter-Strike: Global Offensive");

            for (HWND game : games) {
                if (!activeGames.contains(game)) {
                    System.out.println("Found new game window: " + game);
                    activeGames.add(game);
                    new Thread("Game " + game) {
                        public void run() {
                            try {
                                gameHandler(game);
                            } catch (Exception e) {
                                System.out.println("Game handler for " + game + " interrupted.");
                            }
                        }
                    }.start();
                }
            }

            Thread.sleep(1000);
        }
    }

    private static void gameHandler(HWND game) throws InterruptedException {
        System.out.println("Started game handler for " + game);
        Windows.activateWindow(game);
        Thread.sleep(100);
        System.out.println("Text " + Windows.getWindowText(game));
        HANDLE process = Windows.getProcess(game);

        while (true) {
            List<HMODULE> modules = Windows.getProcessModules(process);
            for (HMODULE module : modules) {
                System.out.println("Module " + module);
                System.out.println("Module " + Windows.getModuleBaseName(process, module));
            }

            Thread.sleep(4000);
        }
    }
}