package senberg.windows.demos.screenshot;

import com.sun.jna.platform.win32.WinDef;
import senberg.windows.Windows;
import senberg.windows.libraries.User32;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ScreenshotWindow {
    public static void main(String[] args) throws AWTException, IOException, InterruptedException {
        System.out.println("Select a window to capture in 5 seconds");
        Thread.sleep(5000);
        WinDef.HWND hWnd = Windows.getForegroundWindow();
        WinDef.RECT rect = new WinDef.RECT();

        // Get active window coordinates
        User32.INSTANCE.GetWindowRect(hWnd, rect);
        System.out.println("Foreground Window " + Windows.getWindowText(hWnd));

        Rectangle window = rect.toRectangle();

        BufferedImage fullWindow = new Robot().createScreenCapture(rect.toRectangle());

        // Allow user to enter coordinates.
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter custom X: ");
        int relativeX = Integer.parseInt(scanner.next());
        System.out.print("Enter custom Y: ");
        int relativeY = Integer.parseInt(scanner.next());
        System.out.print("Enter width: ");
        int customWindowWidth = Integer.parseInt(scanner.next());
        System.out.print("Enter height: ");
        int customWindowHeight = Integer.parseInt(scanner.next());

        Rectangle customRect = new Rectangle((int) window.getX() + relativeX, (int) window.getY() + relativeY, customWindowWidth, customWindowHeight);
        BufferedImage customWindow = new Robot().createScreenCapture(customRect);

        // Example object that can be created to determine if a custom window matches the full screenshot
        CapturedScreenshot capturedScreenshot = new CapturedScreenshot();
        capturedScreenshot.setShRelativeX(relativeX);
        capturedScreenshot.setShRelativeY(relativeY);
        capturedScreenshot.setFullWindow(fullWindow);
        capturedScreenshot.setCustomArea(customWindow);

        // Save images to disk, (to be able to see what is being taken a screenshot of)
        ImageIO.write(fullWindow, "png", new File("screenshot.png"));
        ImageIO.write(customWindow, "png", new File("screenshot_custom.png"));

        System.out.println("Matches? " + checkMatchingCustomWindow(capturedScreenshot));
    }


    /**
     * Check if the captured screenshot matches it's customWindow positions.
     *
     * @param capturedScreenshot to be checked.
     * @return if screenshot matches.
     */
    public static boolean checkMatchingCustomWindow(CapturedScreenshot capturedScreenshot) {
        for (int x = 0; x < capturedScreenshot.getCustomArea().getWidth(); x++) {
            for (int y = 0; y < capturedScreenshot.getCustomArea().getHeight(); y++) {
                if (capturedScreenshot.getCustomArea().getRGB(x, y) != capturedScreenshot.getFullWindow().getRGB(capturedScreenshot.getShRelativeX() + x, capturedScreenshot.getShRelativeY() + y))
                    return false;
            }
        }

        return true;
    }
}
