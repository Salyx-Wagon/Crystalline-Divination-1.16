package salyx.crystalline.divination.core.init;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import salyx.crystalline.divination.CrystalDiv;
import salyx.crystalline.divination.common.tiles.PedestalTile;
import salyx.crystalline.divination.common.tiles.RunicInterceptorTile;
import salyx.crystalline.divination.common.tiles.runes.BaseRuneTile;
import salyx.crystalline.divination.common.tiles.runes.StorageRuneTile;
import salyx.crystalline.divination.common.tiles.runes.ExportRuneTile;
import salyx.crystalline.divination.common.tiles.runes.ImportRuneTile;

public class TileEntityInit {
    
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPE = DeferredRegister
    .create(ForgeRegistries.TILE_ENTITIES, CrystalDiv.MOD_ID);


    public static final RegistryObject<TileEntityType<PedestalTile>> PEDESTAL_TILE_TYPE = TILE_ENTITY_TYPE
    .register("pedestal_tile_type", () -> TileEntityType.Builder.create(PedestalTile::new, BlockInit.PEDESTAL.get()).build(null));
    
    public static final RegistryObject<TileEntityType<RunicInterceptorTile>> RUNIC_INTERCEPTOR_TILE_TYPE = TILE_ENTITY_TYPE
    .register("runic_interceptor_tile_type", () -> TileEntityType.Builder.create(RunicInterceptorTile::new, BlockInit.RUNIC_INTERCEPTOR.get()).build(null));

    public static final RegistryObject<TileEntityType<BaseRuneTile>> BASE_RUNE_TILE_TYPE = TILE_ENTITY_TYPE
    .register("base_rune_tile_type", () -> TileEntityType.Builder.create(BaseRuneTile::new, BlockInit.BASE_RUNE.get()).build(null));

    public static final RegistryObject<TileEntityType<StorageRuneTile>> STORAGE_RUNE_TILE_TYPE = TILE_ENTITY_TYPE
    .register("storage_rune_tile_type", () -> TileEntityType.Builder.create(StorageRuneTile::new, BlockInit.STORAGE_RUNE.get()).build(null));
    
    public static final RegistryObject<TileEntityType<ExportRuneTile>> EXPORT_RUNE_TILE_TYPE = TILE_ENTITY_TYPE
    .register("export_rune_tile_type", () -> TileEntityType.Builder.create(ExportRuneTile::new, BlockInit.EXPORT_RUNE.get()).build(null));
    
    public static final RegistryObject<TileEntityType<ImportRuneTile>> IMPORT_RUNE_TILE_TYPE = TILE_ENTITY_TYPE
    .register("import_rune_tile_type", () -> TileEntityType.Builder.create(ImportRuneTile::new, BlockInit.IMPORT_RUNE.get()).build(null));
    
}
