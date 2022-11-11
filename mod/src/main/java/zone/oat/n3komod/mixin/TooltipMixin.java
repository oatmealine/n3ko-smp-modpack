package zone.oat.n3komod.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import zone.oat.n3komod.N3KOMod;
import zone.oat.n3komod.util.BlockWithTooltip;

import java.util.List;

// stolen from Yttr: https://git.sleeping.town/unascribed/Yttr/src/commit/8052ec2aa059f7870fa53b21c86727d5e0e34bb8/src/main/java/com/unascribed/yttr/mixin/tooltip/client/MixinItemStack.java
// except i made it Better >:)

@Environment(EnvType.CLIENT)
@Mixin(ItemStack.class)
public class TooltipMixin {
  @Inject(at=@At(value="INVOKE", target="net/minecraft/item/Item.appendTooltip(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/item/TooltipContext;)V"),
          method="getTooltip", locals= LocalCapture.CAPTURE_FAILHARD)
  public void getTooltip(@Nullable PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> ci, List<Text> tooltip) {
    ItemStack self = (ItemStack)(Object)this;
    Identifier id = Registry.ITEM.getId(self.getItem());
    if (id.getNamespace().equals(N3KOMod.NAMESPACE)) {
      String keyPrefix = self.getTranslationKey() + ".tooltip";
      BlockWithTooltip.appendTooltip(tooltip, keyPrefix, I18n.translate(keyPrefix + ".hidden").trim().equalsIgnoreCase("true"), id.getPath().equals("lez_plush") ? 0xffdfe8 : 0xFFFFFF);
    }
  }
}
