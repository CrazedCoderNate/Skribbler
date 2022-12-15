package Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class Skribbl {
    @Getter @Setter
    CanvasBox canvasBox;
    @Getter @Setter
    List<DrawColor> drawColors;
}
