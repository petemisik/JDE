package com.jde.winman;

import com.sun.jna.Pointer;
import com.sun.jna.platform.unix.X11;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

public class JWinMan {
    private final X11 x11 = X11.INSTANCE;
    private final X11.Display display;

    public JWinMan() {
        display = x11.XOpenDisplay(null);

        if (display == null) {
            throw new RuntimeException("Failed to open X Display");
        }
        System.out.println("JWin: Successfully connected to X Display");
    }

    public void listAllWindows() {
        X11.Window rootWindow = x11.XDefaultRootWindow(display);

        PointerByReference children_return = new PointerByReference();
        IntByReference nchildren_return = new IntByReference();

        x11.XQueryTree(display, rootWindow, new X11.WindowByReference(), new X11.WindowByReference(), children_return, nchildren_return);
        
        System.out.println("JWinMan: Found " + nchildren_return.getValue() + " windows.");

        if (nchildren_return.getValue() > 0) {
            Pointer childrenPtr = children_return.getValue();
            long[] windows = childrenPtr.getLongArray(0, nchildren_return.getValue());

            for (long windowId : windows) {
                X11.Window win = new X11.Window(windowId);
                X11.XTextProperty name = new X11.XTextProperty();
                x11.XGetWMName(display, win, name);

                if (name.value != null && !name.value.isEmpty()) {
                    System.out.println(" -> Window.ID: " + win + ", Name: '" + name.value + "'");
                }
            }
        }
    }

    public static void main(String[] args) {
        JWinMan windowManager = new JWinMan();
        windowManager.listAllWindows();
    }
}

