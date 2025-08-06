package at.mrcl.farmworld;

import at.mrcl.farmworld.util.Config;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.World;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class FarmWorldConfig extends Config {

    private String name;
    private String permission;

    private World.Environment environment;
    private String generator;

    private boolean enabled = false;

}
