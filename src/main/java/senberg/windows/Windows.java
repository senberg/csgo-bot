package senberg.windows;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;
import senberg.windows.libraries.Kernel32;
import senberg.windows.libraries.Psapi;
import senberg.windows.libraries.Psapi.MODULEINFO;
import senberg.windows.libraries.User32;

import java.util.ArrayList;
import java.util.List;

import static com.sun.jna.platform.win32.WinNT.PROCESS_QUERY_INFORMATION;
import static com.sun.jna.platform.win32.WinNT.PROCESS_VM_READ;

public class Windows {
    private static final int MAX_TITLE_LENGTH = 1024;

    public static int getWindowTextLength(HWND hWnd) {
        return User32.INSTANCE.GetWindowTextLength(hWnd);
    }

    public static String getWindowText(HWND hWnd) {
        char[] buffer = new char[getWindowTextLength(hWnd) + 1];
        User32.INSTANCE.GetWindowText(hWnd, buffer, buffer.length);
        return Native.toString(buffer);
    }

    public static HWND getForegroundWindow() {
        return User32.INSTANCE.GetForegroundWindow();
    }

    public static void activateWindow(HWND hWnd) {
        User32.INSTANCE.ShowWindow(hWnd, 9);
        User32.INSTANCE.SetForegroundWindow(hWnd);
    }

    public static List<HWND> getWindowsByTitle(String name) {
        List<HWND> result = new ArrayList<>();

        for (HWND hWnd : getWindows()) {
            String title = getWindowText(hWnd);

            if (title.trim().equalsIgnoreCase(name.trim())) {
                result.add(hWnd);
            }
        }

        return result;
    }

    public static List<HWND> getWindows() {
        final List<HWND> result = new ArrayList<>();

        User32.INSTANCE.EnumWindows((hWnd, data) -> {
            result.add(hWnd);
            return true;
        }, null);

        return result;
    }

    public static List<HWND> getChildWindows(HWND hWnd) {
        final List<HWND> result = new ArrayList<>();

        User32.INSTANCE.EnumChildWindows(hWnd, (hWnd1, arg1) -> {
            result.add(hWnd1);
            return true;
        }, null);

        return result;
    }

    public static HANDLE getProcess(HWND hWnd) {
        final IntByReference reference = new IntByReference();
        User32.INSTANCE.GetWindowThreadProcessId(hWnd, reference);
        return Kernel32.INSTANCE.OpenProcess(PROCESS_VM_READ | PROCESS_QUERY_INFORMATION, false, reference.getValue());
    }

    public static List<HMODULE> getProcessModules(HANDLE process) {
        final HMODULE[] modules = new HMODULE[4096];
        final IntByReference lpcbNeeded = new IntByReference();
        Psapi.INSTANCE.EnumProcessModulesEx(process, modules, modules.length, lpcbNeeded, 0x03);
        List<HMODULE> result = new ArrayList<>();

        for (HMODULE module : modules) {
            if (module != null) {
                result.add(module);
            }
        }

        return result;
    }

    public static String getModuleBaseName(HANDLE process, HMODULE module) {
        char[] buffer = new char[MAX_TITLE_LENGTH + 1];
        Psapi.INSTANCE.GetModuleBaseName(process, module, buffer, buffer.length);
        return Native.toString(buffer);
    }

    public static MODULEINFO getModuleInformation(HANDLE hProcess, HMODULE hModule) {
        MODULEINFO moduleInformation = new Psapi.MODULEINFO();
        Psapi.INSTANCE.GetModuleInformation(hProcess, hModule, moduleInformation, moduleInformation.size());
        return moduleInformation;
    }

    public static HMODULE getModuleHandle(String moduleName) {
        return Kernel32.INSTANCE.GetModuleHandle(moduleName);
    }

    /**
     * Finds the value at a specific address.
     *
     * @param process     to be searched
     * @param offsets     used to traverse to the right memory point.
     * @param baseAddress is the initial address to start looking from.
     * @return a memory object containing a value
     */
    public static Memory findDynamicAddressAndGetValue(HANDLE process, int[] offsets, long baseAddress) {
        int size = 4;
        Memory pTemp = new Memory(size);
        long pointerAddress = baseAddress;

        if (offsets != null && offsets.length > 0) {
            Kernel32.INSTANCE.ReadProcessMemory(process, new Pointer(pointerAddress + offsets[0]), pTemp, size, null);
            for (int i = 1; i < offsets.length; i++) {
                pointerAddress = (pTemp.getInt(0) + offsets[i]);
                Kernel32.INSTANCE.ReadProcessMemory(process, new Pointer(pointerAddress), pTemp, size, null);
            }

            return pTemp;
        }

        throw new RuntimeException("Please add atl least one offset offsets.");

    }

}
