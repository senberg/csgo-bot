package senberg.windows.demos;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinUser;
import senberg.windows.Windows;
import senberg.windows.libraries.User32;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Trigger bot for CS
 */
public class CSGOTriggerBot {

    /**
     * Variables to locate the crosshair
     */
    public static final int m_iCrosshairId = 0xB2A4;
    public static final int dwLocalPlayer = 0xAA7AB4;
    public static final int dwEntityList = 0x4A8246C;
    public static final int m_iTeamNum = 0xF0;
    public static final int m_iHealth = 0xFC;

    /**
     * Amount of delay before shooting to simulate human behavior.
     */
    private static final int REACTION_DELAY = 10;

    /**
     * To make it more realistic, we will fluctuate the delay with the following the value
     * <p>
     * For instance, REACTION_DELAY set to 50 and FLUCTUATION set to 20. Then the reaction will be somewhere between
     * 30 ms and 70 ms.
     */
    private static final int REACTION_DELAY_FLUCTUATION = 5;


    private static WinUser.HHOOK hhk;
    private static WinUser.LowLevelKeyboardProc keyboardHook;
    private static volatile boolean active = true;

    public static void main(String[] args) throws InterruptedException, AWTException {
        // Runs in a seperate thread.
        initiateToogleKeyListener();

        List<WinDef.HWND> games = Windows.getWindowsByTitle("Counter-Strike: Global Offensive");
        System.out.println("Amount of games found: " + games.size());
        for (WinDef.HWND game : games) {
            Thread.sleep(100);
            WinNT.HANDLE process = Windows.getProcess(game);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Shutting down handle!");
                Kernel32.INSTANCE.CloseHandle(process);
            }));


            List<WinDef.HMODULE> modules = Windows.getProcessModules(process);
            for (WinDef.HMODULE module : modules) {
                if (Windows.getModuleBaseName(process, module).equalsIgnoreCase("client.dll")) {
                    System.out.println("In sync with the right modules");
                    Robot robot = new Robot();
                    while (true) {
                        if (active) {
                            short aimId = getCSGOMemoryValue(process, module, new int[]{dwLocalPlayer, m_iCrosshairId});
                            short playerTeam = getCSGOMemoryValue(process, module, new int[]{dwLocalPlayer, m_iTeamNum});

                            int enemyBasePointer = dwEntityList + ((aimId - 1) * 0x10);
                            short enemyTeam = getCSGOMemoryValue(process, module, new int[]{enemyBasePointer, m_iTeamNum});
                            short enemyHealth = getCSGOMemoryValue(process, module, new int[]{enemyBasePointer, m_iHealth});

                            if (aimId > 0 && playerTeam != enemyTeam && enemyHealth > 0) {
                                Thread.sleep(REACTION_DELAY + ((int) (Math.random() * REACTION_DELAY_FLUCTUATION * 2)) - REACTION_DELAY_FLUCTUATION);
                                robot.keyPress(KeyEvent.VK_N);
                                robot.delay(30);
                                robot.keyRelease(KeyEvent.VK_N);
                            }

                        }
                        Thread.sleep(1);

                    }

                }
            }


            Kernel32.INSTANCE.CloseHandle(process);

        }

    }

    /**
     * Key listener to toggle on and off
     */
    private static void initiateToogleKeyListener() {
        new Thread(() -> {
            final User32 lib = User32.INSTANCE;
            WinDef.HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
            WinUser.LowLevelKeyboardProc keyboardHook = (nCode, wParam, info) -> {
                if (nCode >= 0) {
                    switch (wParam.intValue()) {
                        //case WinUser.WM_KEYUP:
                        case WinUser.WM_KEYDOWN:
                        case WinUser.WM_SYSKEYUP:
                        case WinUser.WM_SYSKEYDOWN:
                            if (info.vkCode == 0x56) {
                                active = active ? false : true;
                                System.out.println("Active: " + active);
                            }
                    }
                }

                Pointer ptr = info.getPointer();
                long peer = Pointer.nativeValue(ptr);
                return lib.CallNextHookEx(hhk, nCode, wParam, new WinDef.LPARAM(peer));
            };
            hhk = lib.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, keyboardHook, hMod, 0);

            WinUser.MSG msg = new WinUser.MSG();
            while (User32.INSTANCE.GetMessage(msg, null, 0, 0) != 0) {
                User32.INSTANCE.TranslateMessage(msg);
                User32.INSTANCE.DispatchMessage(msg);
            }

            lib.UnhookWindowsHookEx(hhk);

        }).start();
    }

    private static void printCSGOInfo(WinNT.HANDLE process, WinDef.HMODULE module, String info, int[] offsets) {
        System.out.println(info + ": " + getCSGOMemoryValue(process, module, offsets));
    }

    private static short getCSGOMemoryValue(WinNT.HANDLE process, WinDef.HMODULE module, int[] offsets) {
        return Windows.findDynamicAddressAndGetValue(process, offsets, Pointer.nativeValue(module.getPointer())).getShort(0);
    }

}
