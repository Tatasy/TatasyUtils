import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutTitle.EnumTitleAction;

/**
 * Manage Title and ActionBar easily in 1.12.2 !
 * Spigot version : 1.12.2 !
 *
 * @author Tatasy
 * @version 1.0.0
 */
public class TitleManager {

	/**
	 * Send a title whit a subtitle.
	 *
	 * @param player The player.
	 * @param title The title that will appear.
	 * @param subtitle The subtitle that will appear.
	 * @param durationInSeconds The duration when the title remains visible (in seconds).
	 */
	public static void sendTitle(Player player, String title, String subtitle, int durationInSeconds) {
		IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + title + "\"}");
		IChatBaseComponent chatSubtitle = ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
		
		PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
		PacketPlayOutTitle packetSubtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubtitle);
		
		getPlayerNMS(player).playerConnection.sendPacket(packetTitle);
		getPlayerNMS(player).playerConnection.sendPacket(packetSubtitle);
		
		sendTitleTime(player, durationInSeconds);
	}

	/**
	 * Send a title.
	 *
	 * @param player The player.
	 * @param title The title that will appear.
	 * @param durationInSeconds The duration when the title remains visible (in seconds).
	 */
	public static void sendTitle(Player player, String title, int durationInSeconds) {
		IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + title + "\"}");
		
		PacketPlayOutTitle packetTitle = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
		
		getPlayerNMS(player).playerConnection.sendPacket(packetTitle);
		
		sendTitleTime(player, durationInSeconds);
	}

	/**
	 * Send a actionbar.
	 *
	 * @param player The player.
	 * @param message The message that will appear.
	 */
	public static void sendActionBar(Player player, String message) {
		IChatBaseComponent chatActionBar = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		
		PacketPlayOutTitle packetActionBar = new PacketPlayOutTitle(EnumTitleAction.ACTIONBAR, chatActionBar);
		
		getPlayerNMS(player).playerConnection.sendPacket(packetActionBar);
	}

	/**
	 * Send a title time.
	 *
	 * @param player The player.
	 * @param durationInSeconds The time when the title remains visible.
	 */
	private static void sendTitleTime(Player player, int durationInSeconds){
		PacketPlayOutTitle packetTitleTimes = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, 20, durationInSeconds * 20, 20);
		
		getPlayerNMS(player).playerConnection.sendPacket(packetTitleTimes);
	}

	/**
	 * Get EntityPlayer of the player.
	 *
	 * @param player the player.
	 * @return A EntityPlayer of the player.
	 */
	private static EntityPlayer getPlayerNMS(Player player){
		return ((CraftPlayer)player).getHandle();
	}

}
