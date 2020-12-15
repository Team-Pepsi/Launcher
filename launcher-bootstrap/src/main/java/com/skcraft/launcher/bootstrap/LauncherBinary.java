/*
 * SK's Minecraft Launcher
 * Copyright (C) 2010-2014 Albert Pham <http://www.sk89q.com> and contributors
 * Please see LICENSE.txt for license information.
 */

package com.skcraft.launcher.bootstrap;

import lombok.Getter;
import lombok.extern.java.Log;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
public class LauncherBinary implements Comparable<LauncherBinary> {

    public static final Pattern PATTERN = Pattern.compile("^([0-9]+)\\.jar$");
    @Getter
    private final File path;
    private final long time;

    public LauncherBinary(File path) {
        this.path = path;
        String name = path.getName();
        Matcher m = PATTERN.matcher(name);
        if (!m.matches()) {
            throw new IllegalArgumentException("Invalid filename: " + path);
        }
        time = Long.parseLong(m.group(1));
    }

    public File getExecutableJar() throws IOException {
            return path;
    }

    @Override
    public int compareTo(LauncherBinary o) {
        if (time > o.time) {
            return -1;
        } else if (time < o.time) {
            return 1;
        } else {
            return 0;
        }
    }

    public void remove() {
        path.delete();
    }

    public static class Filter implements FileFilter {
        @Override
        public boolean accept(File file) {
            return file.isFile() && LauncherBinary.PATTERN.matcher(file.getName()).matches();
        }
    }
}
