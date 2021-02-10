package senberg.windows.demos.screenshot;

import java.awt.image.BufferedImage;

public class CapturedScreenshot {
    private int shRelativeX, shRelativeY;

    private BufferedImage fullWindow;
    private BufferedImage customArea;

    public int getShRelativeX() {
        return shRelativeX;
    }

    public void setShRelativeX(int shRelativeX) {
        this.shRelativeX = shRelativeX;
    }

    public int getShRelativeY() {
        return shRelativeY;
    }

    public void setShRelativeY(int shRelativeY) {
        this.shRelativeY = shRelativeY;
    }

    public BufferedImage getFullWindow() {
        return fullWindow;
    }

    public void setFullWindow(BufferedImage fullWindow) {
        this.fullWindow = fullWindow;
    }

    public BufferedImage getCustomArea() {
        return customArea;
    }

    public void setCustomArea(BufferedImage customArea) {
        this.customArea = customArea;
    }
}
