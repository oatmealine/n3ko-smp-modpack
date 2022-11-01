package zone.oat.n3komod.util;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.BlockView;

import java.util.List;

public class BlockWithTooltip extends Block {
  public List<String> tooltip;

  public BlockWithTooltip(Settings settings, List<String> tooltip) {
    super(settings);
    this.tooltip = tooltip;
  }

  @Override
  public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> origTooltip, TooltipContext tooltipContext) {
    this.tooltip.forEach((String tip) -> {
      origTooltip.add(new TranslatableText(tip));
    });
  }
}
