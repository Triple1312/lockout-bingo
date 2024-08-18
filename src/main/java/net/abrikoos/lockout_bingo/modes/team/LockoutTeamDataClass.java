package net.abrikoos.lockout_bingo.modes.team;

import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.List;

public record LockoutTeamDataClass(String name, List<String> playernames) { }
