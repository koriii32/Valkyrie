package me.kori32.valkyrie.listener;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.lang.reflect.Method;

public class OreBreakListener implements Listener {

    private static final String[] antixrayores = {
            "COAL_ORE", "GOLD_ORE", "IRON_ORE", "COPPER_ORE",
            "DEEPSLATE_COPPER_ORE", "REDSTONE_ORE", "NETHER_GOLD_ORE",
            "EMERALD_ORE", "DEEPSLATE_EMERALD_ORE", "LAPIS_ORE",
            "DIAMOND_ORE", "DEEPSLATE_COAL_ORE", "DEEPSLATE_GOLD_ORE",
            "DEEPSLATE_IRON_ORE", "DEEPSLATE_REDSTONE_ORE",
            "DEEPSLATE_LAPIS_ORE", "DEEPSLATE_DIAMOND_ORE",
            "ANCIENT_DEBRIS"
    };

    @EventHandler
    public void breakevent(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        String type = blocktypestring(block);

        for (String ore : antixrayores) {
            if (type.equals(ore)) {
                String formattedname = formatname(type);
                String alertmsg = "§6§lVALKYRIE §8§l» §6" + player.getName() + " §fhas found §6" + formattedname;

                player.getServer().getOnlinePlayers().stream()
                        .filter(p -> p.hasPermission("valkyrie.alerts"))
                        .forEach(p -> p.sendMessage(alertmsg));
                break;
            }
        }
    }

    private String blocktypestring(Block block) {
        try {
            Method getType = block.getClass().getMethod("getType");
            Object material = getType.invoke(block);
            Method name = material.getClass().getMethod("name");
            return (String) name.invoke(material);
        } catch (Exception e) {
            e.printStackTrace();
            return "UNKNOWN";
        }
    }

    private String formatname(String type) {
        String cleaned = type.replace("_ORE", "")
                .toLowerCase()
                .replace("_", " ");

        StringBuilder result = new StringBuilder();
        for (String word : cleaned.split(" ")) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        String finalresult = result.toString().trim();

        switch (finalresult) {
            case "Redstone": return "Redstone";
            case "Lapis": return "Lapis Lazuli";
            case "Ancient Debris": return "Ancient Debris";
            case "Nether Gold": return "Nether Gold";
            default: return finalresult + " Ore";
        }
    }
}