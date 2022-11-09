package zone.oat.n3komod.util;

import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.BlockView;

import java.util.ArrayList;
import java.util.List;

public class BlockWithTooltip extends Block {
  protected String tooltipPrefix;
  protected boolean onlyShowWithShift;

  public BlockWithTooltip(Settings settings, String tooltipPrefix, boolean onlyShowWithShift) {
    super(settings);
    this.tooltipPrefix = tooltipPrefix;
    this.onlyShowWithShift = onlyShowWithShift;
  }
  public BlockWithTooltip(Settings settings, String tooltipPrefix) { this(settings, tooltipPrefix, false); }

  @Override
  public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> origTooltip, TooltipContext tooltipContext) {
    appendTooltip(origTooltip, this.tooltipPrefix, this.onlyShowWithShift);
  }

  public static void appendTooltip(List<Text> origTooltip, String tooltipPrefix, boolean onlyShowWithShift) {
    if (Screen.hasShiftDown() || (!onlyShowWithShift)) {
      origTooltip.addAll(getTooltipsFromPrefix(tooltipPrefix));
    } else {
      origTooltip.add(new TranslatableText("tooltip.n3ko.more_info", KeyBinding.getLocalizedName("key.keyboard.left.shift").get()));
    }
  }

  public static List<Text> getTooltipsFromPrefix(String tooltipPrefix) {
    if (I18n.hasTranslation(tooltipPrefix)) { // single tooltip
      return List.of(new TranslatableText(tooltipPrefix));
    } else {
      ArrayList<Text> tooltips = new ArrayList<>();
      int index = 0;
      while (I18n.hasTranslation(tooltipPrefix + "." + index)) {
        tooltips.add(new TranslatableText(tooltipPrefix + "." + index));
        index++;
      }
      return tooltips;
    }
  }
}
