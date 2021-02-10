package senberg.windows.demos;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import senberg.windows.Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CSGOGeneralInfo {

    // Netvars
    public static final int m_ArmorValue = 0xB238;
    public static final int m_Collision = 0x318;
    public static final int m_CollisionGroup = 0x470;
    public static final int m_Local = 0x2FAC;
    public static final int m_MoveType = 0x258;
    public static final int m_OriginalOwnerXuidHigh = 0x316C;
    public static final int m_OriginalOwnerXuidLow = 0x3168;
    public static final int m_aimPunchAngle = 0x301C;
    public static final int m_aimPunchAngleVel = 0x3028;
    public static final int m_bGunGameImmunity = 0x3894;
    public static final int m_bHasDefuser = 0xB248;
    public static final int m_bHasHelmet = 0xB22C;
    public static final int m_bInReload = 0x3245;
    public static final int m_bIsDefusing = 0x3888;
    public static final int m_bIsScoped = 0x387E;
    public static final int m_bSpotted = 0x939;
    public static final int m_bSpottedByMask = 0x97C;
    public static final int m_clrRender = 0x70;
    public static final int m_dwBoneMatrix = 0x2698;
    public static final int m_fAccuracyPenalty = 0x32B0;
    public static final int m_fFlags = 0x100;
    public static final int m_flFallbackWear = 0x3178;
    public static final int m_flFlashDuration = 0xA2F8;
    public static final int m_flFlashMaxAlpha = 0xA2F4;
    public static final int m_flNextPrimaryAttack = 0x31D8;
    public static final int m_hActiveWeapon = 0x2EE8;
    public static final int m_hMyWeapons = 0x2DE8;
    public static final int m_hObserverTarget = 0x3360;
    public static final int m_hOwner = 0x29BC;
    public static final int m_hOwnerEntity = 0x148;
    public static final int m_iAccountID = 0x2FA8;
    public static final int m_iClip1 = 0x3204;
    public static final int m_iCompetitiveRanking = 0x1A44;
    public static final int m_iCompetitiveWins = 0x1B48;
    public static final int m_iCrosshairId = 0xB2A4;
    public static final int m_iEntityQuality = 0x2F8C;
    public static final int m_iFOVStart = 0x31D8;
    public static final int m_iGlowIndex = 0xA310;
    public static final int m_iHealth = 0xFC;
    public static final int m_iItemDefinitionIndex = 0x2F88;
    public static final int m_iItemIDHigh = 0x2FA0;
    public static final int m_iObserverMode = 0x334C;
    public static final int m_iShotsFired = 0xA2B0;
    public static final int m_iState = 0x31F8;
    public static final int m_iTeamNum = 0xF0;
    public static final int m_lifeState = 0x25B;
    public static final int m_nFallbackPaintKit = 0x3170;
    public static final int m_nFallbackSeed = 0x3174;
    public static final int m_nFallbackStatTrak = 0x317C;
    public static final int m_nForceBone = 0x267C;
    public static final int m_nTickBase = 0x3404;
    public static final int m_rgflCoordinateFrame = 0x440;
    public static final int m_szCustomName = 0x301C;
    public static final int m_szLastPlaceName = 0x3588;
    public static final int m_thirdPersonViewAngles = 0x31C8;
    public static final int m_vecOrigin = 0x134;
    public static final int m_vecVelocity = 0x110;
    public static final int m_vecViewOffset = 0x104;
    public static final int m_viewPunchAngle = 0x3010;


    // Signatures
    public static final int dwClientState = 0x57E854;
    public static final int dwClientState_GetLocalPlayer = 0x180;
    public static final int dwClientState_IsHLTV = 0x4CC8;
    public static final int dwClientState_Map = 0x28C;
    public static final int dwClientState_MapDirectory = 0x188;
    public static final int dwClientState_MaxPlayer = 0x310;
    public static final int dwClientState_PlayerInfo = 0x5240;
    public static final int dwClientState_State = 0x108;
    public static final int dwClientState_ViewAngles = 0x4D10;
    public static final int dwEntityList = 0x4A8246C;
    public static final int dwForceAttack = 0x2EC47F8;
    public static final int dwForceAttack2 = 0x2EC4804;
    public static final int dwForceBackward = 0x2EC47E0;
    public static final int dwForceForward = 0x2EC4834;
    public static final int dwForceJump = 0x4F1970C;
    public static final int dwForceLeft = 0x2EC47EC;
    public static final int dwForceRight = 0x2EC47C8;
    public static final int dwGameDir = 0x61C870;
    public static final int dwGameRulesProxy = 0x4F83A54;
    public static final int dwGetAllClasses = 0xACC26C;
    public static final int dwGlobalVars = 0x57E558;
    public static final int dwGlowObjectManager = 0x4F9F800;
    public static final int dwInput = 0x4ECD050;
    public static final int dwInterfaceLinkList = 0x6E4014;
    public static final int dwLocalPlayer = 0xAA7AB4;
    public static final int dwMouseEnable = 0xAAD318;
    public static final int dwMouseEnablePtr = 0xAAD2E8;
    public static final int dwPlayerResource = 0x2EC2B4C;
    public static final int dwRadarBase = 0x4EB7154;
    public static final int dwSensitivity = 0xAAD1B4;
    public static final int dwSensitivityPtr = 0xAAD188;
    public static final int dwSetClanTag = 0x87060;
    public static final int dwViewMatrix = 0x4A73E84;
    public static final int dwWeaponTable = 0x4ECDC4C;
    public static final int dwWeaponTableIndex = 0x31FC;
    public static final int dwYawPtr = 0xAACF78;
    public static final int dwZoomSensitivityRatioPtr = 0xAB1FE0;
    public static final int dwbSendPackets = 0xCD55A;
    public static final int dwppDirect3DDevice9 = 0xA1F40;
    public static final int m_pStudioHdr = 0x293C;
    public static final int m_pitchClassPtr = 0x4EB7400;
    public static final int m_yawClassPtr = 0xAACF78;


    static class ReplayDate extends JPanel {
        private List<CustomPoint> points;

        public ReplayDate() {
            points = new ArrayList<CustomPoint>();

        }

        public void addPoint(CustomPoint point) {
            points.add(point);
        }

        public void clearPoints() {
            points.clear();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (CustomPoint p : points) {
                g.fillRect(p.x, p.y, 4, 4);
                g.setColor(p.getColor());
            }


        }

        public void draw() {
            repaint();
        }
    }

    public static void main(String[] args) throws InterruptedException {


        List<WinDef.HWND> games = Windows.getWindowsByTitle("Counter-Strike: Global Offensive");

        System.out.println("Amount of games: " + games.size());
        for (WinDef.HWND game : games) {
            System.out.println("Started game handler for " + game);
            Thread.sleep(100);
            System.out.println("Window name: " + Windows.getWindowText(game));
            WinNT.HANDLE process = Windows.getProcess(game);

            Game frame = new Game();
            frame.setBounds(100, 100, 500, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(replayDate); // Add replay data to jframe

            frame.setVisible(true);
            List<WinDef.HMODULE> modules = Windows.getProcessModules(process);
            for (WinDef.HMODULE module : modules) {
                if (Windows.getModuleBaseName(process, module).equalsIgnoreCase("client.dll")) {
                    while (true) {


                        printCSGOInfo(process, module, "Health", new int[]{dwLocalPlayer, m_iHealth});
                        printCSGOInfo(process, module, "Armor", new int[]{dwLocalPlayer, m_ArmorValue});
                        printCSGOInfo(process, module, "Defuser", new int[]{dwLocalPlayer, m_bHasDefuser});
                        printCSGOInfo(process, module, "Has helmet", new int[]{dwLocalPlayer, m_bHasHelmet});
                        printCSGOInfo(process, module, "Crosshair aiming at object with id", new int[]{dwLocalPlayer, m_iCrosshairId});


                        short playerTeam = getCSGOMemoryValueAsShort(process, module, new int[]{dwLocalPlayer, m_iTeamNum});
                        short crossHair = getCSGOMemoryValueAsShort(process, module, new int[]{dwLocalPlayer, m_iCrosshairId});

                        replayDate.clearPoints();
                        for(int i = 1; i < 64; i++) {

                            int team = getCSGOMemoryValueAsShort(process, module, new int[] {dwEntityList + ((i - 1) * 0x10), m_iTeamNum});

                            long positionX = getCSGOMemoryValueAsInt(process, module, new int[] {dwEntityList + ((i - 1) * 0x10), m_vecOrigin}) & 0xffffffffl;
                            long positionY = getCSGOMemoryValueAsInt(process, module, new int[] {dwEntityList + ((i - 1) * 0x10), m_vecOrigin + 0x04})  & 0xffffffffl;
                            long positionZ = getCSGOMemoryValueAsInt(process, module, new int[] {dwEntityList + ((i - 1) * 0x10), m_vecOrigin  + 0x08})  & 0xffffffffl;

                            int x = ((int)(positionX/1000000.0)-1000);
                            int y = ((int)(positionY/1000000.0)-900);
                            replayDate.addPoint(new CustomPoint(x,y, team % 2 == 0 ? Color.BLUE : Color.RED));
                            System.out.println("Entity "+i+" Vec: " + positionX + " " + positionY + " "+ positionZ + " " + x +" " + y);
                        }
                        frame.revalidate();
                        frame.repaint();
                        int enemyBasePointer = dwEntityList + ((crossHair - 1) * 0x10);
                        short enemyBaseValue = getCSGOMemoryValueAsShort(process, module, new int[]{dwEntityList + ((crossHair - 1) * 0x10)});
                        short enemyTeam = getCSGOMemoryValueAsShort(process, module, new int[]{enemyBasePointer, m_iTeamNum});
                        short enemyHealth = getCSGOMemoryValueAsShort(process, module, new int[]{enemyBasePointer, m_iHealth});

                        System.out.println("EnemyBase: " + enemyBasePointer);
                        System.out.println("Player Team: " + playerTeam);
                        System.out.println("Player in crosshair belong to team: " + enemyTeam);
                        System.out.println("Player in crosshair (Health): " + enemyHealth);

                        /*
                         * For flags: https://github.com/ValveSoftware/source-sdk-2013/blob/master/mp/src/public/const.h
                         */
                        short target = getCSGOMemoryValueAsShort(process, module, new int[]{dwLocalPlayer, m_fFlags});

                        System.out.println("Is player on the ground: " + (target & (1 << 0)));
                        System.out.println("Is player in water?: " + (target & (1 << 7)));
                        Thread.sleep(1000);
                    }

                }
            }


            Kernel32.INSTANCE.CloseHandle(process);

        }
    }

    // In your Main class
    private static ReplayDate replayDate = new ReplayDate();



    private static void printCSGOInfo(WinNT.HANDLE process, WinDef.HMODULE module, String info, int[] offsets) {
        System.out.println(info + ": " + getCSGOMemoryValueAsShort(process, module, offsets));
    }

    private static short getCSGOMemoryValueAsShort(WinNT.HANDLE process, WinDef.HMODULE module, int[] offsets) {
        return Windows.findDynamicAddressAndGetValue(process, offsets, Pointer.nativeValue(module.getPointer())).getShort(0);
    }

    private static int getCSGOMemoryValueAsInt(WinNT.HANDLE process, WinDef.HMODULE module, int[] offsets) {
        return Windows.findDynamicAddressAndGetValue(process, offsets, Pointer.nativeValue(module.getPointer())).getInt(0);
    }

    private static float[] getCSGOMemoryValueAsFloatArray(WinNT.HANDLE process, WinDef.HMODULE module, int[] offsets) {
        return Windows.findDynamicAddressAndGetValue(process, offsets, Pointer.nativeValue(module.getPointer())).getFloatArray(0,1);
    }

    private static class Game extends JFrame {
        public void paint (Graphics g){
            //Graphics2D g2 = (Graphics2D) g;
            replayDate.paintComponent(g);
        }
    }

    private static class CustomPoint extends Point {
        private Color color;
        public CustomPoint(int x, int y, Color color) {
            super(x, y);
            this.color = color;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }
}
