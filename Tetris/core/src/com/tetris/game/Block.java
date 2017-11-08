package com.tetris.game;

public class Block {
    int grid[][]; //for interacting with the grid
    int x, y; //coordinates of block

    Block(int[][] g, int xvar, int yvar) //constructs block object at set of
    {                                    //co-ordinates on game game grid
        grid = g;

        x = xvar;
        y = yvar;
    }

}
