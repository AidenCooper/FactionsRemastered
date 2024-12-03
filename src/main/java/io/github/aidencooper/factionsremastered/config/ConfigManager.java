package io.github.aidencooper.factionsremastered.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import io.github.aidencooper.factionsremastered.FactionsPlugin;
import io.github.aidencooper.factionsremastered.config.formatters.ScalarFormatter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class ConfigManager {
    @NotNull private static final ConfigPath DEFAULT_LANG = ConfigPath.EN_US;

    @NotNull private final FactionsPlugin plugin;
    @NotNull private final ConcurrentHashMap<ConfigPath, YamlDocument> documentsLoaded;

    @Getter(AccessLevel.PUBLIC) @NotNull private ConfigPath langPath;

    public ConfigManager(@NotNull FactionsPlugin plugin) {
        this.plugin = plugin;
        this.documentsLoaded = new ConcurrentHashMap<>();
        this.langPath = ConfigManager.DEFAULT_LANG;
    }

    @Nullable
    public YamlDocument get(@NotNull ConfigPath path) {
        return this.getDocumentsLoaded().get(path);
    }

    @NotNull
    public YamlDocument getLang() {
        return this.getDocumentsLoaded().get(this.getLangPath());
    }

    public void load(@NotNull ConfigPath path, boolean switchLangPath) throws IOException {
        if(this.getDocumentsLoaded().get(path) != null) return;

        YamlDocument document = YamlDocument.create(
                new File(plugin.getDataFolder(), path.getPath()),
                Objects.requireNonNull(plugin.getResource(path.getPath())),
                DumperSettings.builder().setScalarFormatter(new ScalarFormatter()).build(),
                GeneralSettings.DEFAULT,
                LoaderSettings.builder().setAutoUpdate(true).build(),
                UpdaterSettings.builder().setVersioning(new BasicVersioning("config-version")).build()
        );

        if(path.isLangFile() && path != this.getLangPath()) {
            if(switchLangPath) {
                this.unload(this.getLangPath());
                this.setLangPath(path);
                this.getDocumentsLoaded().put(path, document);
            }

            return;
        }

        this.getDocumentsLoaded().put(path, document);
    }

    public void unload(@NotNull ConfigPath path) throws IOException {
        if(this.getDocumentsLoaded().get(path) == null) return;

        this.save(path);
        this.getDocumentsLoaded().remove(path);
    }

    public void reload(@NotNull ConfigPath path) throws IOException {
        YamlDocument document = this.get(path);
        if(document == null) return;

        document.reload();

        if(path == ConfigPath.CONFIG) {
            ConfigPath locale;

            try {
                locale = ConfigPath.valueOf(document.getString("locale").toUpperCase());
            } catch (IllegalArgumentException exception) {
                this.load(ConfigManager.DEFAULT_LANG, true);
                this.getPlugin().getLogger().warning("Incorrect locale option in " + path.getPath() + ". Loaded " + this.getLangPath().getPath() + ".");
                return;
            }

            if(locale != this.getLangPath()) this.load(locale, true);
        }
    }

    public void save(@NotNull ConfigPath path) throws IOException {
        if(this.getDocumentsLoaded().get(path) == null) return;

        this.getDocumentsLoaded().get(path).save();
    }

    public void loadAll() throws IOException {
        for(ConfigPath name : ConfigPath.values()) this.load(name, false);
    }

    public void reloadAll() throws IOException {
        for(ConfigPath path : this.getDocumentsLoaded().keySet()) this.reload(path);
    }

    public void saveAll() throws IOException {
        for(ConfigPath path : this.getDocumentsLoaded().keySet()) this.save(path);
    }
}
