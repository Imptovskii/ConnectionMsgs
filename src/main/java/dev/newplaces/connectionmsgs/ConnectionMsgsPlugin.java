package dev.newplaces.connectionmsgs;

import java.util.HashSet;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ConnectionMsgsPlugin extends JavaPlugin implements Listener {
   private HashSet<Player> on = new HashSet();

   public void onEnable() {
      Iterator var1 = Bukkit.getServer().getOnlinePlayers().iterator();

      while(var1.hasNext()) {
         Player onlinePlayer = (Player)var1.next();
         this.on.add(onlinePlayer);
      }

      Bukkit.getServer().getPluginManager().registerEvents(this, this);
   }

   @EventHandler
   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (sender instanceof Player && command.getName().equalsIgnoreCase("toggleconnectionmsgs")) {
         Player player = (Player)sender;
         if (this.on.contains(player)) {
            this.on.remove(player);
            player.sendMessage(ChatColor.GOLD + "Сообщения подключения были скрыты.");
            return true;
         } else {
            this.on.add(player);
            player.sendMessage(ChatColor.GOLD + "Сообщения подключения снова видны.");
            return true;
         }
      } else {
         return false;
      }
   }

   @EventHandler
   public void OnJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      this.on.add(player);
      event.setJoinMessage((String)null);
      Iterator var3 = Bukkit.getServer().getOnlinePlayers().iterator();

      while(var3.hasNext()) {
         Player onlinePlayer = (Player)var3.next();
         if (this.on.contains(onlinePlayer)) {
            onlinePlayer.sendMessage(ChatColor.GRAY + player.getName() + " присоединился к серверу");
         }
      }

   }

   @EventHandler
   public void OnQuit(PlayerQuitEvent event) {
      Player player = event.getPlayer();
      this.on.remove(player);
      event.setQuitMessage((String)null);
      Iterator var3 = Bukkit.getServer().getOnlinePlayers().iterator();

      while(var3.hasNext()) {
         Player onlinePlayer = (Player)var3.next();
         if (this.on.contains(onlinePlayer)) {
            onlinePlayer.sendMessage(ChatColor.GRAY + player.getName() + " отключился от сервера");
         }
      }

   }
}