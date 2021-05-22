package com.example.piplup;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.ImageUtil;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class piplupInvenOverlay extends WidgetItemOverlay {

    private final ItemManager itemManager;
    private final piplupPlugin plugin;
    private final piplupConfig config;
    private final Cache<Long, Image> fillCache;

    private final ArrayList<Integer> customItemID = new ArrayList<Integer>(10476);

    @Inject
    private piplupInvenOverlay(ItemManager itemManager, piplupPlugin plugin, piplupConfig config)
    {
        this.itemManager = itemManager;
        this.plugin = plugin;
        this.config = config;
        showOnEquipment();
        showOnInventory();
        fillCache = CacheBuilder.newBuilder()
                .concurrencyLevel(1)
                .maximumSize(32)
                .build();
    }

    @Override
    public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem)
    {
        String group = "0";
        if (!customItemID.contains(itemId)){group = "1";} //if not in custom items, exit

        if (group == "1")
        {
            final Color color = Color.GREEN;
            if (color != null)
            {
                Rectangle bounds = widgetItem.getCanvasBounds();
                if (false) //outline
                {
                    final BufferedImage outline = itemManager.getItemOutline(itemId, widgetItem.getQuantity(), color);
                    graphics.drawImage(outline, (int) bounds.getX(), (int) bounds.getY(), null);
                }

                if (true) //image
                {
                    //final Image image = getFillImage(color, widgetItem.getId(), widgetItem.getQuantity());
                    //graphics.drawImage(image, (int) bounds.getX(), (int) bounds.getY(), null);
                    if (itemId == 10476) {
                        File file = new File(config.rarecandyPath());
                        Image rare_candy = null;
                        try {
                            rare_candy = ImageIO.read(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        graphics.drawImage(rare_candy, (int) bounds.getX(), (int) bounds.getY(), null);
                    }
                }

                if (false) //fill color
                {
                    int heightOffSet = (int) bounds.getY() + (int) bounds.getHeight() + 2;
                    graphics.setColor(color);
                    graphics.drawLine((int) bounds.getX(), heightOffSet, (int) bounds.getX() + (int) bounds.getWidth(), heightOffSet);
                }
            }
        }
    }

    private Image getFillImage(Color color, int itemId, int qty)
    {
        long key = (((long) itemId) << 32) | qty;
        Image image = fillCache.getIfPresent(key);
        if (image == null)
        {
            final Color fillColor = ColorUtil.colorWithAlpha(color, 30);
            image = ImageUtil.fillImage(itemManager.getImage(itemId, qty, false), fillColor);
            fillCache.put(key, image);
        }
        return image;
    }

    void invalidateCache()
    {
        fillCache.invalidateAll();
    }
}
