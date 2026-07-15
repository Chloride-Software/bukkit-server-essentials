package com.chloride.modutilities.util;

public class Messages {
    public static final String NO_PERMISSION = CC.RED + "No permission.";
    public static final String INVALID_PLAYER = CC.RED + "Invalid player.";

    public static final String VANISH_ENTER = CC.GREEN + "You are now vanished.";
    public static final String VANISH_EXIT = CC.RED + "You are no longer vanished.";
    public static final String VANISH_REJOIN = CC.GOLD + "Note: You are still vanished from last log-in.";
    public static final String VANISH_ACTION_DISALLOWED = CC.RED + "You cannot do this while vanished!";

    public static final String STAFF_CHAT_ENTER = CC.GREEN + "You are now in staff chat.";
    public static final String STAFF_CHAT_EXIT = CC.RED + "You are no longer in staff chat.";
    public static final String STAFF_CHAT_FORMAT = "&b&l[S] &r&b%s&7: &f%s";

    public static final String MOD_MODE_ENTER = CC.GREEN + "You are now in mod mode.";
    public static final String MOD_MODE_EXIT = CC.RED + "You are no longer in mod mode.";
    public static final String MOD_MODE_RANDOM_TP_EMPTY = CC.RED + "There are no online players to teleport to!";

    private Messages() {
        throw new RuntimeException("Cannot instantiate a utility class.");
    }
}
