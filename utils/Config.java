import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create config file yml easily for spigot !
 *
 * @author Tatasy
 * @version 1.0.0
 */
public class Config {

    private Plugin plugin;
    private String fileName;
    private File file;
    private FileConfiguration fileConfiguration;

    public Config(String fileName, Plugin plugin){
        this.plugin = plugin;
        this.fileName = fileName;
        file = new File(plugin.getDataFolder(), fileName);
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void loadConfig() {
        if (!file.exists()) {
            InputStream in = plugin.getResource(Paths.get(fileName).getFileName().toString());
            if (in == null) {
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                } catch (IOException e) {
                    plugin.getLogger().severe("Could not create file " + fileName);
                }
            } else {
                File outFile = new File(plugin.getDataFolder(), fileName);
                int lastIndex = fileName.lastIndexOf(47);
                File outDir = new File(plugin.getDataFolder(), fileName.substring(0, fileName.substring(0, Math.max(lastIndex, 0)));
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }
                try {
                    if(outFile.exists()) {
                        plugin.getLogger().warning("Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
                    }else {
                        OutputStream out = new FileOutputStream(outFile);
                        byte[] buf = new byte[1024];

                        int len;
                        while((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        out.close();
                        in.close();
                    }
                } catch (IOException e) {
                    plugin.getLogger().severe("Could not save " + outFile.getName() + " to " + outFile);
                }
            }
        }
    }

    public void saveConfig() {
        try {
            fileConfiguration.save(file);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        saveConfig();
    }

    public File getFile() {
        return file;
    }

    public void setValue(String path, Object value) {
        fileConfiguration.set(path, value);
    }

    public Object getValue(String path) {
        return fileConfiguration.get(path);
    }

    public boolean contains(String path) {
        return fileConfiguration.contains(path);
    }

    public String getString(String path) {
        return ChatColor.translateAlternateColorCodes('&', fileConfiguration.getString(path));
    }

    public int getInt(String path) {
        return fileConfiguration.getInt(path);
    }

    public boolean getBoolean(String path) {
        return fileConfiguration.getBoolean(path);
    }

    public Double getDouble(String path) {
        return fileConfiguration.getDouble(path);
    }

    public Long getLong(String path) {
        return fileConfiguration.getLong(path);
    }

    public List<String> getStringList(String path) {
        return fileConfiguration.getStringList(path).stream()
            .map(str -> ChatColor.translateAlternateColorCodes('&', str))
            .collect(Collectors.toList());
    }

    public List<Integer> getIntegerList(String path) {
        return fileConfiguration.getIntegerList(path);
    }

    public List<Double> getDoubleList(String path) {
        return fileConfiguration.getDoubleList(path);
    }

    public List<Long> getLongList(String path) {
        return fileConfiguration.getLongList(path);
    }

    public List<Float> getFloatList(String path) {
        return fileConfiguration.getFloatList(path);
    }

    public ConfigurationSection createSection(String path) {
        return fileConfiguration.createSection(path);
    }

    public ConfigurationSection getConfigurationSection(String path) {
        return fileConfiguration.getConfigurationSection(path);
    }

}
