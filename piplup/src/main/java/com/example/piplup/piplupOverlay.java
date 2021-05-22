package com.example.piplup;

import net.runelite.api.Point;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Singleton
public class piplupOverlay extends Overlay
{

    private final Client client;
    private final piplupPlugin plugin;
    private final piplupConfig config;
    //private final PanelComponent panelComponent = new PanelComponent();



    @Inject
    private piplupOverlay(Client client, piplupPlugin plugin, piplupConfig config)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);

        this.client = client;
        this.plugin = plugin;
        this.config = config;
    }




    @Override
    public Dimension render(Graphics2D graphics) {
        final Color mycolor = config.myColor();


        //panelComponent.getChildren().clear();
        String overlayTitle = "Current World:";

        // Build overlay title
        //panelComponent.getChildren().add(TitleComponent.builder()
        //        .text(overlayTitle)
        //        .color(Color.GREEN)
        //        .build());

        // Set the size of the overlay (width)
        //panelComponent.setPreferredSize(new Dimension(
        //        graphics.getFontMetrics().stringWidth(overlayTitle) + 30,
        //        0));

        // Add a line on the overlay for world number
        //panelComponent.getChildren().add(LineComponent.builder()
        //        .left("Number:")
        //        .right(Integer.toString(client.getWorld()))
        //        .build());

        // Show world type goes here ...



        if (config.booleanownPlayerConfig()) {
            Actor localplayer = client.getLocalPlayer();

            try {
                drawImp(graphics, localplayer, "Owo", mycolor);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (config.booleanplayerPictureConfig()) {
            for (Player player : client.getPlayers()) {
                if (player.getName().equals("i is jay")) {
                    File file = new File(config.spoonPath());
                    Image image = null;
                    try {
                        image = ImageIO.read(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    BufferedImage bimage = (BufferedImage) image;
                    Actor actor = player;

                    LocalPoint localpoint = actor.getLocalLocation();
                    OverlayUtil.renderImageLocation(client, graphics, localpoint, bimage, 180);


                }

                if (player.getName().toLowerCase().equals("zacster") || player.getName().toLowerCase().equals("cheshiredoge") || player.getName().toLowerCase().equals("alpoopi") || player.getName().toLowerCase().equals("versix") || player.getName().toLowerCase().equals("rubiak")) {
                    File file = new File(config.torchicPath());
                    Image image = null;
                    try {
                        image = ImageIO.read(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    BufferedImage bimage = (BufferedImage) image;
                    Actor actor = player;

                    LocalPoint localpoint = actor.getLocalLocation();
                    OverlayUtil.renderImageLocation(client, graphics, localpoint, bimage, 180);

                }
            }
        }

        if (config.booleanprojectileConfig()) {
            for (Projectile p : client.getProjectiles()) {
                if (true) {
                    Point point = getProjectilePoint(p);
                    if (point != null) {
                        Point textLocation = new Point(point.getX(), point.getY());
                        renderTextLocation(graphics, Integer.toString(p.getRemainingCycles() / 30), Color.GREEN, textLocation);

                        File file = new File(config.piplupPath());
                        Image image = null;
                        try {
                            image = ImageIO.read(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Image image1 = new ImageIcon(url).getImage();

                        BufferedImage bimage = (BufferedImage) image;

                        OverlayUtil.renderImageLocation(graphics, point, bimage);
                    }
                }
            }
        }



        //return panelComponent.render(graphics);
        return null;
    }



    private void drawImp(Graphics2D graphics, Actor actor, String text, Color color) throws IOException {
        //Polygon poly = actor.getCanvasTilePoly();
        if (true)
        {
            //OverlayUtil.renderPolygon(graphics, poly, color);



            String pathname = config.piplupPath();
            File file = new File(pathname);
            Image image = ImageIO.read(file);

            //Image image1 = new ImageIcon(url).getImage();

            BufferedImage bimage = (BufferedImage) image;

            //OverlayUtil.renderActorOverlayImage(graphics, actor, bimage, color, 180);


            LocalPoint localpoint = actor.getLocalLocation();
            OverlayUtil.renderImageLocation(client, graphics, localpoint, bimage, 180);


        }

        Point textLocation = actor.getCanvasTextLocation(graphics, text, actor.getLogicalHeight());
        if (textLocation != null)
        {
            OverlayUtil.renderTextLocation(graphics, textLocation, text, Color.cyan);
        }
    }


    private Point getProjectilePoint(Projectile p)
    {
        int x = (int) p.getX();
        int y = (int) p.getY();
        int z = (int) p.getZ();
        return Perspective.localToCanvas(client, new LocalPoint(x, y), 0, Perspective.getTileHeight(client, new LocalPoint(x, y), p.getFloor()) - z);
    }

    public void renderTextLocation(Graphics2D graphics, String txtString, Color fontColor, Point canvasPoint)
    {
        if (canvasPoint != null)
        {
            graphics.setFont(new Font(FontManager.getRunescapeSmallFont().toString(), 1, 20));
            final Point canvasCenterPoint = new Point(canvasPoint.getX(), canvasPoint.getY());
            final Point canvasCenterPoint_shadow = new Point(canvasPoint.getX() + 1, canvasPoint.getY() + 1);
            OverlayUtil.renderTextLocation(graphics, canvasCenterPoint_shadow, txtString, Color.BLACK);
            OverlayUtil.renderTextLocation(graphics, canvasCenterPoint, txtString, fontColor);
        }
    }

}
