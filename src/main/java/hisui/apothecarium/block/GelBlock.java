package hisui.apothecarium.block;

import hisui.apothecarium.mixin.MapColorArrayAccessorMixin;
import hisui.apothecarium.util.ColorUtils;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GelBlock extends Block {

    private int color = 0xFFFFFF;

    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);

    private GelBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    public GelBlock(AbstractBlock.Settings settings, int color) {
        super(settings.dropsNothing().notSolid().breakInstantly().replaceable().pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.SNOW).strength(0.1f).mapColor(GelBlock.getClosestMapColor(new Color(color))));
        this.color = color;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        effect(entity);
    }

    protected void effect(Entity entity){
    }

    public int getColor(){
        return color;
    }

    private static MapColor getClosestMapColor(Color color1) {
        Integer nearestDistance = Integer.valueOf(Integer.MAX_VALUE);
        Color nearestColor = Color.WHITE;

        List<Color> mapColors = new ArrayList<>();
        List<MapColor> mapColors1 = Arrays.asList(MapColorArrayAccessorMixin.getCOLORS()).stream().filter(Objects::nonNull).collect(Collectors.toList());
        for(MapColor mapColor : mapColors1) {
            mapColors.add(ColorUtils.intToRGB(mapColor.color));
        }

        for(Color color2 : mapColors){
            int redDiff = color1.getRed() - color2.getRed();
            int greenDiff = color1.getGreen() - color2.getGreen();
            int blueDiff = color1.getBlue() - color2.getBlue();
            int distance = (int)Math.sqrt(redDiff * redDiff + greenDiff * greenDiff + blueDiff * blueDiff);
            if (nearestDistance > distance) {
                nearestDistance = distance;
                nearestColor = color2;
            }
        }
        return ColorUtils.colorToMapColor(nearestColor);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        switch (type) {
            case LAND: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        return Block.isFaceFullSquare(blockState.getCollisionShape(world, pos.down()), Direction.UP);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

}
