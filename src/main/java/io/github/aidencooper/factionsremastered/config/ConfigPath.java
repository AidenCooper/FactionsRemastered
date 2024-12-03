package io.github.aidencooper.factionsremastered.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public enum ConfigPath {
    CONFIG("config.yml", false),
    EN_US("lang/en_US.yml", true),
    ES_ES("lang/es_ES.yml", true);

    @NotNull private final String path;
    private final boolean langFile;

    @Override
    public String toString() {
        return this.getPath();
    }


}
