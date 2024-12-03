package io.github.aidencooper.factionsremastered.config.formatters;

import dev.dejvokep.boostedyaml.libs.org.snakeyaml.engine.v2.common.ScalarStyle;
import dev.dejvokep.boostedyaml.libs.org.snakeyaml.engine.v2.nodes.Tag;
import dev.dejvokep.boostedyaml.utils.format.Formatter;
import dev.dejvokep.boostedyaml.utils.format.NodeRole;
import org.jetbrains.annotations.NotNull;

public class ScalarFormatter implements Formatter<ScalarStyle, String> {
    @NotNull
    @Override
    public ScalarStyle format(@NotNull Tag tag, @NotNull String string, @NotNull NodeRole nodeRole, @NotNull ScalarStyle scalarStyle) {
        return tag == Tag.STR && nodeRole != NodeRole.KEY ? ScalarStyle.DOUBLE_QUOTED : ScalarStyle.PLAIN;
    }
}
