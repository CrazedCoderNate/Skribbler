package Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class CanvasBox {
    @Getter @Setter
    private int xPosition;
    @Getter @Setter
    private int yPosition;
    @Getter @Setter
    private int height;
    @Getter @Setter
    private int width;
}
