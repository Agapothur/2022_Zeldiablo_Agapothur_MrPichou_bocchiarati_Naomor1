package gameLaby.laby;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SpriteLoader {
    ArrayList<ImagePattern> tab;
    List<String> tabnames;

    public SpriteLoader(String dossier){
        List<String> results = new ArrayList<String>();

        File[] files = new File("projet_zeldiablo/src/image").listFiles();

        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                results.add(file.getName());
            }
        }
        this.tab = new ArrayList<>();
        for (String file: results) {
            this.tab.add(new ImagePattern(new Image("image/" + file)));
        }
        this.tabnames = results;
    }

    public ImagePattern getImage(String file){
        int i = tabnames.indexOf(file);
        if (i != -1){
            return this.tab.get(i);
        }
        else {
            return null;
        }
    }
}
