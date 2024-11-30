package bg.sofia.uni.fmi.mjt.glovo.controlcenter.map;

import static bg.sofia.uni.fmi.mjt.glovo.Utils.nullCheck;

public record MapEntity(Location location, MapEntityType type) {
    public MapEntity {
        nullCheck(location, "Location cannot be null");
        nullCheck(type, "Map entity type cannot be null");
    }
}
