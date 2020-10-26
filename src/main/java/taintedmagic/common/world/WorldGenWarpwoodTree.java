package taintedmagic.common.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;
import taintedmagic.common.registry.BlockRegistry;
import thaumcraft.common.lib.world.WorldGenCustomFlowers;

/**
 * This class is based off of WorldGenSilverwoodTrees.class created by <Azanor> as part of Thaumcraft 4.
 */
public class WorldGenWarpwoodTree extends WorldGenAbstractTree
{
    private Block trunk;
    private Block leaves;
    private final int minTreeHeight;
    private final int randomTreeHeight;
    boolean worldgen = false;

    public WorldGenWarpwoodTree (boolean doBlockNotify, int minTreeHeight, int randomTreeHeight)
    {
        super(doBlockNotify);
        this.worldgen = !doBlockNotify;
        this.minTreeHeight = minTreeHeight;
        this.randomTreeHeight = randomTreeHeight;
        this.trunk = BlockRegistry.BlockWarpwoodLog;
        this.leaves = BlockRegistry.BlockWarpwoodLeaves;
    }

    public boolean generate (World world, Random random, int x, int y, int z)
    {
        int height = random.nextInt(this.randomTreeHeight) + this.minTreeHeight;
        boolean flag = true;
        if (y >= 1 && y + height + 1 <= 256)
        {
            for (int i1 = y; i1 <= y + 1 + height; ++i1)
            {
                byte spread = 1;
                if (i1 == y)
                {
                    spread = 0;
                }

                if (i1 >= y + 1 + height - 2)
                {
                    spread = 3;
                }

                for (int j1 = x - spread; j1 <= x + spread && flag; ++j1)
                {
                    for (int k1 = z - spread; k1 <= z + spread && flag; ++k1)
                    {
                        if (i1 >= 0 && i1 < 256)
                        {
                            Block block = world.getBlock(j1, i1, k1);
                            if (!block.isAir(world, j1, i1, k1) && !block.isLeaves(world, j1, i1, k1)
                                    && !block.isReplaceable(world, j1, i1, k1) && i1 > y)
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                Block block1 = world.getBlock(x, y - 1, z);
                boolean isSoil = block1.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (BlockSapling) Blocks.sapling);
                if (isSoil && y < 256 - height - 1)
                {
                    block1.onPlantGrow(world, x, y - 1, z, x, y, z);
                    int start = y + height - 5;
                    int end = y + height + 3 + random.nextInt(3);

                    int k2;
                    for (k2 = start; k2 <= end; ++k2)
                    {
                        int cty = MathHelper.clamp_int(k2, y + height - 3, y + height);

                        for (int xx = x - 5; xx <= x + 5; ++xx)
                        {
                            for (int zz = z - 5; zz <= z + 5; ++zz)
                            {
                                double d3 = (double) (xx - x);
                                double d4 = (double) (k2 - cty);
                                double d5 = (double) (zz - z);
                                double dist = d3 * d3 + d4 * d4 + d5 * d5;
                                if (dist < (double) (10 + random.nextInt(8))
                                        && world.getBlock(xx, k2, zz).canBeReplacedByLeaves(world, xx, k2, zz))
                                {
                                    this.setBlockAndNotifyAdequately(world, xx, k2, zz, this.leaves, 1);
                                }
                            }
                        }
                    }

                    int chance = (int) ((double) height * 1.5D);
                    boolean lastblock = false;

                    for (k2 = 0; k2 < height; ++k2)
                    {
                        Block block2 = world.getBlock(x, y + k2, z);
                        if (block2.isAir(world, x, y + k2, z) || block2.isLeaves(world, x, y + k2, z)
                                || block2.isReplaceable(world, x, y + k2, z))
                        {
                            this.setBlockAndNotifyAdequately(world, x, y + k2, z, this.trunk, 1);
                            this.setBlockAndNotifyAdequately(world, x - 1, y + k2, z, this.trunk, 1);
                            this.setBlockAndNotifyAdequately(world, x + 1, y + k2, z, this.trunk, 1);
                            this.setBlockAndNotifyAdequately(world, x, y + k2, z - 1, this.trunk, 1);
                            this.setBlockAndNotifyAdequately(world, x, y + k2, z + 1, this.trunk, 1);
                        }
                    }

                    this.setBlockAndNotifyAdequately(world, x, y + k2, z, this.trunk, 1);
                    this.setBlockAndNotifyAdequately(world, x - 1, y, z - 1, this.trunk, 1);
                    this.setBlockAndNotifyAdequately(world, x + 1, y, z + 1, this.trunk, 1);
                    this.setBlockAndNotifyAdequately(world, x - 1, y, z + 1, this.trunk, 1);
                    this.setBlockAndNotifyAdequately(world, x + 1, y, z - 1, this.trunk, 1);
                    if (random.nextInt(3) != 0)
                    {
                        this.setBlockAndNotifyAdequately(world, x - 1, y + 1, z - 1, this.trunk, 1);
                    }

                    if (random.nextInt(3) != 0)
                    {
                        this.setBlockAndNotifyAdequately(world, x + 1, y + 1, z + 1, this.trunk, 1);
                    }

                    if (random.nextInt(3) != 0)
                    {
                        this.setBlockAndNotifyAdequately(world, x - 1, y + 1, z + 1, this.trunk, 1);
                    }

                    if (random.nextInt(3) != 0)
                    {
                        this.setBlockAndNotifyAdequately(world, x + 1, y + 1, z - 1, this.trunk, 1);
                    }

                    this.setBlockAndNotifyAdequately(world, x - 2, y, z, this.trunk, 5);
                    this.setBlockAndNotifyAdequately(world, x + 2, y, z, this.trunk, 5);
                    this.setBlockAndNotifyAdequately(world, x, y, z - 2, this.trunk, 9);
                    this.setBlockAndNotifyAdequately(world, x, y, z + 2, this.trunk, 9);
                    this.setBlockAndNotifyAdequately(world, x - 2, y - 1, z, this.trunk, 1);
                    this.setBlockAndNotifyAdequately(world, x + 2, y - 1, z, this.trunk, 1);
                    this.setBlockAndNotifyAdequately(world, x, y - 1, z - 2, this.trunk, 1);
                    this.setBlockAndNotifyAdequately(world, x, y - 1, z + 2, this.trunk, 1);
                    this.setBlockAndNotifyAdequately(world, x - 1, y + (height - 4), z - 1, this.trunk, 1);
                    this.setBlockAndNotifyAdequately(world, x + 1, y + (height - 4), z + 1, this.trunk, 1);
                    this.setBlockAndNotifyAdequately(world, x - 1, y + (height - 4), z + 1, this.trunk, 1);
                    this.setBlockAndNotifyAdequately(world, x + 1, y + (height - 4), z - 1, this.trunk, 1);
                    if (random.nextInt(3) == 0)
                    {
                        this.setBlockAndNotifyAdequately(world, x - 1, y + (height - 5), z - 1, this.trunk, 1);
                    }

                    if (random.nextInt(3) == 0)
                    {
                        this.setBlockAndNotifyAdequately(world, x + 1, y + (height - 5), z + 1, this.trunk, 1);
                    }

                    if (random.nextInt(3) == 0)
                    {
                        this.setBlockAndNotifyAdequately(world, x - 1, y + (height - 5), z + 1, this.trunk, 1);
                    }

                    if (random.nextInt(3) == 0)
                    {
                        this.setBlockAndNotifyAdequately(world, x + 1, y + (height - 5), z - 1, this.trunk, 1);
                    }

                    this.setBlockAndNotifyAdequately(world, x - 2, y + (height - 4), z, this.trunk, 5);
                    this.setBlockAndNotifyAdequately(world, x + 2, y + (height - 4), z, this.trunk, 5);
                    this.setBlockAndNotifyAdequately(world, x, y + (height - 4), z - 2, this.trunk, 9);
                    this.setBlockAndNotifyAdequately(world, x, y + (height - 4), z + 2, this.trunk, 9);

                    WorldGenerator flowers = new WorldGenCustomFlowers(BlockRegistry.BlockNightshadeBush, 0);
                    flowers.generate(world, random, x, y, z);

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }
}