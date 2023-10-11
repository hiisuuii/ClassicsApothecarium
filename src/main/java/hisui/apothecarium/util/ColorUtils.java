package hisui.apothecarium.util;

import hisui.apothecarium.mixin.MapColorArrayAccessorMixin;
import net.minecraft.block.MapColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ColorUtils {

    private static final List<MapColor> mapColors = Arrays.asList(MapColorArrayAccessorMixin.getCOLORS()).stream().filter(Objects::nonNull).collect(Collectors.toList());

    public static Color intToRGB(int iColor){
        return new Color(iColor);
    }

    public static int RGBtoInt(Color color){
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int value = ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                ((b & 0xFF) << 0);
        return value;
    }

    public static MapColor colorToMapColor(Color color){
        MapColor returnColor = MapColor.WHITE;
        for(int i = 0; i < mapColors.size(); i++) {
            MapColor mapColor = mapColors.get(i);
            if (mapColor.color == ColorUtils.RGBtoInt(color)) {
                returnColor = mapColor;
                break;
            }
        }
        return returnColor;
    }
}
