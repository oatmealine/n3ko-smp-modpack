package zone.oat.n3komod.content.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class DrywallChunk extends Item {
  public DrywallChunk(Settings settings) {
    super(settings);
  }

  @Override
  public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
    ItemStack itemStack = super.finishUsing(stack, world, user);

    if (user instanceof PlayerEntity player) {
      Text name = player.getName();

      if (name.asString().equalsIgnoreCase("cardboxneko")) {
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 30));
      } else {
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 160, 3));
        user.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 180));
      }
    }

    return itemStack;
  }
}
