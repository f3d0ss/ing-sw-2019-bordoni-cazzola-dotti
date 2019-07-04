package it.polimi.ingsw.view.gui;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageCache {
    private static Map<String, Image> cachedImages = new HashMap<>();

    static Image getImage(String uri) {
        if (!cachedImages.containsKey(uri))
            cachedImages.put(uri, new Image(ImageCache.class.getResource(uri).toExternalForm()));
        return cachedImages.get(uri);
    }
}
