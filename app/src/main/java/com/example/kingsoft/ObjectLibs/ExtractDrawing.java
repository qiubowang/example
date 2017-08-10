package com.example.kingsoft.ObjectLibs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingsoft on 2017/6/9.
 */

public class ExtractDrawing {
    String fillType;
    float x = 0;
    float y = 0;
    float width = 0;
    float height = 0;
    int shapeId;
    ExtractShapeText shapeText = null;

    public ExtractDrawing(){
        shapeText = new ExtractShapeText();
    }

}
