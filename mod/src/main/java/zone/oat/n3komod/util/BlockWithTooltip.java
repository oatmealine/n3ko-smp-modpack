package zone.oat.n3komod.util;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class BlockWithTooltip {
  public static void appendTooltip(List<Text> origTooltip, String tooltipPrefix, boolean onlyShowWithShift) {
    appendTooltip(origTooltip, tooltipPrefix, onlyShowWithShift, 0xFFFFFF);
  }
  public static void appendTooltip(List<Text> origTooltip, String tooltipPrefix, boolean onlyShowWithShift, int color) {
    if (Screen.hasShiftDown() || (!onlyShowWithShift)) {
      origTooltip.addAll(getTooltipsFromPrefix(tooltipPrefix, color));
    } else {
      origTooltip.add(Text.translatable("tooltip.n3ko.more_info", KeyBinding.getLocalizedName("key.keyboard.left.shift").get()));
    }
  }

  public static List<Text> getTooltipsFromPrefix(String tooltipPrefix, int recolor) {
    if (I18n.hasTranslation(tooltipPrefix)) { // single tooltip
      return List.of(Text.translatable(tooltipPrefix));
    } else {
      ArrayList<Text> tooltips = new ArrayList<>();
      int index = 0;
      while (I18n.hasTranslation(tooltipPrefix + "." + index)) {
        MutableText tooltip = Text.translatable(tooltipPrefix + "." + index);
        tooltip.setStyle(Style.EMPTY.withColor(recolor));
        tooltips.add(tooltip);
        index++;
      }
      return tooltips;
    }
  }
}
