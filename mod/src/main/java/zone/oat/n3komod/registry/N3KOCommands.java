package zone.oat.n3komod.registry;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.DimensionArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.dimension.DimensionType;

import static net.minecraft.server.command.CommandManager.*;

// boneless https://git.sleeping.town/unascribed/Lucium/src/branch/trunk/src/main/java/com/unascribed/lucium/command/GoToCommand.java

public class N3KOCommands {
  public static void init() {
    CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
      dispatcher.register(
        literal("dim")
          .requires((scs) -> scs.hasPermissionLevel(4))
          .then(CommandManager.argument("dimension", DimensionArgumentType.dimension())
            .executes(ctx -> handle(ctx))
          )
      );
    });
  }

  private static int handle(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
    try {
      ServerPlayerEntity player = ctx.getSource().getPlayer();
      ServerWorld w = DimensionArgumentType.getDimensionArgument(ctx, "dimension");

      double scale = DimensionType.getCoordinateScaleFactor(player.getWorld().getDimension(), w.getDimension());
      double x = player.getX()*scale;
      double z = player.getZ()*scale;
      double y = player.getY();

      player.teleport(w, x, y, z, player.getYaw(), player.getPitch());
      ctx.getSource().sendFeedback(() -> Text.translatable("commands.teleport.success.entity.single", new Object[]{
              player.getDisplayName(),
              w.getRegistryKey().getValue()+" ("+player.getBlockX()+", "+player.getBlockY()+", "+player.getBlockZ()+")"}), true);

      return 0;
    } catch (RuntimeException | Error e) {
      e.printStackTrace();
      throw e;
    }
  }
}
