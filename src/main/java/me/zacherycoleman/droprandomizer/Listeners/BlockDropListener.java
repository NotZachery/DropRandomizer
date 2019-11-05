package me.zacherycoleman.droprandomizer.Listeners;

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.enchantments.Enchantment;

import me.zacherycoleman.droprandomizer.Main;

public class BlockDropListener implements Listener
{
    Main pl = Main.getPlugin(Main.class);
    public static final List<Material> matlist = Arrays.asList(Material.values());
    public static final List<Enchantment> enchantlist = Arrays.asList(Enchantment.values());
    public static final Map<Material, Material> LookupTable = new HashMap<Material, Material>();


    private boolean StupidFuckingJava(Material m)
    {
        // This WOULD BE A FUCKING SWITCH STATEMENT
        // if JAVA would learn how they FUCK THEY WORK!
        if (m == Material.AIR)
            return false;
        if (m == Material.BEDROCK)
            return false;
        if (m == Material.BARRIER)
            return false;
        if (m == Material.COMMAND_BLOCK)
            return false;
        if (m == Material.COMMAND_BLOCK_MINECART)
            return false;
        if (m == Material.END_PORTAL)
            return false;
        if (m == Material.VOID_AIR)
            return false;
        if (m == Material.END_PORTAL_FRAME)
            return false;
        if (m == Material.NETHER_PORTAL)
            return false;
        if (m == Material.STRUCTURE_BLOCK)
            return false;
        if (m == Material.STRUCTURE_VOID)
            return false;
        if (m == Material.FROSTED_ICE)
            return false;
        if (m == Material.KELP_PLANT)
            return false;
        if (m == Material.BUBBLE_COLUMN)
            return false;
        if (m == Material.MELON_STEM)
            return false;
        if (m == Material.BEETROOTS)
            return false;
        if (m == Material.JIGSAW)
            return false;
        if (m == Material.PISTON_HEAD)
            return false;
        if (m == Material.POTATOES)
            return false;
        if (m.name().toLowerCase().contains("wall") || m.name().toLowerCase().contains("potted"))
            return false;
        
        return true;
    }

    private Material GetRandomMaterial(Material material)
    {
        // Check if the material is in the lookup table
        if (LookupTable.get(material) == null)
        {
            Material m = null;

            // Makes dumbfuck java's compiler not whine about "unreachable" statements which
            // are completely reachable but it can't calculate that.
            while (true)
            {
                m = matlist.get(new Random().nextInt(matlist.size()));
                // Prevent spawning of items that can't be useful in-game
                if (StupidFuckingJava(m))
                    break;
                else
                    continue;
            }
            // Put the item in the lookup table for later
            LookupTable.put(material, m);
            // return early to prevent double-lookup.
            return m;
        }

        // Return the lookup table version.
        return LookupTable.get(material);
    }

    private void EnchantItem(ItemStack is)
    {
        // Random enchantments and levels to the item.
        // TODO: Check for conflicting enchantments?
        for (int i = new Random().nextInt(10); i >= 0; i--)
        {
            Enchantment e = enchantlist.get(new Random().nextInt(enchantlist.size()));
            int level = new Random().nextInt(e.getMaxLevel());
            if (level < e.getStartLevel())
                level = e.getStartLevel();

            //pl.getLogger().info(String.format("Enchanting %s with %s and %d levels", is.getType().name(), e.toString(), level));
            // Make sure the item is enchantable
            if (e.canEnchantItem(is))
                is.addEnchantment(e, level);
        }
    }

    private void RandomizeItemMeta(ItemStack is)
    {
        if (is.getType() == Material.POTION || is.getType() == Material.SPLASH_POTION || is.getType() == Material.LINGERING_POTION)
        {
            // TODO: Randomize potion effects.
        }

        if (is.getType() == Material.FIREWORK_ROCKET)
        {
            // TODO: Randomize fireworks and maybe FIREWORK_CHARGE?
        }

        // TODO: Randomize Books/signs?
    }

    @EventHandler
    public void BlockDropItem (BlockBreakEvent event)
    {
        // Stop the block from dropping it's item
        event.setDropItems(false);

        // Create and drop our own item
        ItemStack is = new ItemStack(GetRandomMaterial(event.getBlock().getType()));

        this.EnchantItem(is);

        if (is.hasItemMeta())
            this.RandomizeItemMeta(is);

        //pl.getLogger().info(String.format("%s is digging %s, will drop %s", event.getPlayer().getDisplayName(), event.getBlock().getType().name(), is.getType().name()));

        Location location = event.getBlock().getLocation();
        World w = event.getBlock().getWorld();

        // Drop a random item at the location of the block
        // we'll drop a grass block for now, until we create a randomizer method
        w.dropItem(location, is);

    }
}
