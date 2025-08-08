package at.mrcl.farmworld;

import at.mrcl.farmworld.util.Config;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PluginConfig extends Config {

    private boolean defaultBootstrapper = true;
    private boolean staticWorldNames = false;

}
