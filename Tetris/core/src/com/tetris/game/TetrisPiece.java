package com.tetris.game;

import java.util.Random;

public class TetrisPiece
{
    private int grid [][] = new int[12][22];
    private boolean stop = false;
    private Block[] blocks;
    private Random r = new Random();
    private int colour;
    private boolean hit = false;

    TetrisPiece(int[][] g, Block[] b)
    {
        grid = g;
        blocks = b;
        colour = r.nextInt(5);
    }

    void Show() //places blocks on grid array
    {
        grid[blocks[0].x][blocks[0].y] = 2;
        grid[blocks[1].x][blocks[1].y] = 2;
        grid[blocks[2].x][blocks[2].y] = 2;
        grid[blocks[3].x][blocks[3].y] = 2;
    }

    void Placed() //sets blocks as placed down
    {
        grid[blocks[0].x][blocks[0].y] = 3;
        grid[blocks[1].x][blocks[1].y] = 3;
        grid[blocks[2].x][blocks[2].y] = 3;
        grid[blocks[3].x][blocks[3].y] = 3;
    }


    void delete() //removes block from grid array
    {
        grid[blocks[0].x][blocks[0].y] = 0;
        grid[blocks[1].x][blocks[1].y] = 0;
        grid[blocks[2].x][blocks[2].y] = 0;
        grid[blocks[3].x][blocks[3].y] = 0;
    }

    void AutoMoveDown() //moves block down and deletes it at previous position
    {
        if(grid[blocks[0].x][blocks[0].y-1]==1 || grid[blocks[1].x][blocks[1].y-1]==1 ||
                grid[blocks[2].x][blocks[2].y-1]==1 || grid[blocks[3].x][blocks[3].y-1]==1)
        {
            stop = true;
            Placed();
        }
        else if(grid[blocks[0].x][blocks[0].y-1]==3 || grid[blocks[1].x][blocks[1].y-1]==3 ||
                grid[blocks[2].x][blocks[2].y-1]==3 || grid[blocks[3].x][blocks[3].y-1]==3)
        {
            stop = true;
            Placed();
        }
        else
        {
            this.delete();
            blocks[0].y--;
            blocks[1].y--;
            blocks[2].y--;
            blocks[3].y--;
            this.Show();
        }
    }

    void MoveDown() //moves block down and deletes it at previous position
    {
        if(grid[blocks[0].x][blocks[0].y-1]==1 || grid[blocks[1].x][blocks[1].y-1]==1 ||
                grid[blocks[2].x][blocks[2].y-1]==1 || grid[blocks[3].x][blocks[3].y-1]==1)
        {
            stop = true;
            Placed();
        }
        else if(grid[blocks[0].x][blocks[0].y-1]==3 || grid[blocks[1].x][blocks[1].y-1]==3 ||
                grid[blocks[2].x][blocks[2].y-1]==3 || grid[blocks[3].x][blocks[3].y-1]==3)
        {
            stop = true;
            Placed();
        }
        else
        {
            this.delete();
            blocks[0].y--;
            blocks[1].y--;
            blocks[2].y--;
            blocks[3].y--;
            this.Show();
        }
    }

    void FallDown() //sends blocks to the bottom of screen
    {
        for(int y = 1; y < 21;y++)
        {
            if(grid[blocks[0].x][blocks[0].y-1]==1 || grid[blocks[1].x][blocks[1].y-1]==1 ||
                    grid[blocks[2].x][blocks[2].y-1]==1 || grid[blocks[3].x][blocks[3].y-1]==1)
            {
                stop = true;
                Placed();
            }
            else if(grid[blocks[0].x][blocks[0].y-1]==3 || grid[blocks[1].x][blocks[1].y-1]==3 ||
                    grid[blocks[2].x][blocks[2].y-1]==3 || grid[blocks[3].x][blocks[3].y-1]==3)
            {
                stop = true;
                Placed();
            }
            else
            {
                this.delete();
                blocks[0].y--;
                blocks[1].y--;
                blocks[2].y--;
                blocks[3].y--;
                this.Show();
            }
        }
    }

    void MoveRight() //moves blocks right and deletes them at their previous position
    {
        hit = false;
        if(grid[blocks[0].x+1][blocks[0].y]==1 || grid[blocks[1].x+1][blocks[1].y]==1 ||
                grid[blocks[2].x+1][blocks[2].y]==1 || grid[blocks[3].x+1][blocks[3].y]==1)
        {
            //block cant move right
            hit = true;
        }
        else if(grid[blocks[0].x+1][blocks[0].y]==3 || grid[blocks[1].x+1][blocks[1].y]==3 ||
                grid[blocks[2].x+1][blocks[2].y]==3 || grid[blocks[3].x+1][blocks[3].y]==3)
        {
            //block cant move right
            hit = true;
        }
        else
        {
            this.delete();
            blocks[0].x++;
            blocks[1].x++;
            blocks[2].x++;
            blocks[3].x++;
            this.Show();
        }
    }

    void MoveLeft() //moves blocks left and deletes them at their previous position
    {
        hit = false;
        if(grid[blocks[0].x-1][blocks[0].y]==1 || grid[blocks[1].x-1][blocks[1].y]==1 ||
                grid[blocks[2].x-1][blocks[2].y]==1 || grid[blocks[3].x-1][blocks[3].y]==1)
        {
            //block cant move left
            hit = true;
        }
        else if(grid[blocks[0].x-1][blocks[0].y]==3 || grid[blocks[1].x-1][blocks[1].y]==3 ||
                grid[blocks[2].x-1][blocks[2].y]==3 || grid[blocks[3].x-1][blocks[3].y]==3)
        {
            //block cant move left
            hit = true;
        }
        else
        {
            this.delete();
            blocks[0].x--;
            blocks[1].x--;
            blocks[2].x--;
            blocks[3].x--;
            this.Show();
        }
    }

    void Rotate()
    {
        hit = false;
        this.delete();

        int xpivot = blocks[2].x;
        int ypivot = blocks[2].y;

        int x0 = blocks[0].x - xpivot;
        int y0 = blocks[0].y - ypivot;

        int yy0 = x0; //for the sake easiness
        int xx0 = y0;

        int vx0 = 0 * (yy0) + (xx0) * -1;
        int vy0 = 1 * (yy0) + 0 * (xx0);

        int nx0 = xpivot + vx0;
        int ny0 = ypivot + vy0;

        //----------------------------

        int x1 = blocks[1].x - xpivot;
        int y1 = blocks[1].y - ypivot;

        int yy1 = x1; //
        int xx1 = y1;

        int vx1 = 0 * (yy1) + (xx1) * -1;
        int vy1 = 1 * (yy1) + 0 * (xx1);

        int nx1 = xpivot + vx1;
        int ny1 = ypivot + vy1;

        //----------------------------

        int x3 = blocks[3].x - xpivot;
        int y3 = blocks[3].y - ypivot;

        int yy3 = x3; //
        int xx3 = y3;

        int vx3 = 0 * (yy3) + (xx3) * -1;
        int vy3 = 1 * (yy3) + 0 * (xx3);

        int nx3 = xpivot + vx3;
        int ny3 = ypivot + vy3;

        //----------------------------

        if (grid[nx0][ny0] == 1 || grid[nx1][ny1] == 1 || grid[nx3][ny3] == 1 || grid[blocks[2].x][blocks[2].y]==1 ||
                grid[nx0][ny0] == 3 || grid[nx1][ny1] == 3 || grid[nx3][ny3] == 3 || grid[blocks[2].x][blocks[2].y]==3 ||
                grid[nx0][ny0] == -1 || grid[nx1][ny1] == -1 || grid[nx3][ny3] == -1 || grid[blocks[2].x][blocks[2].y]==-1)
        {
            hit = true;
        }
        else
        {
            blocks[0].x = nx0;
            blocks[0].y = ny0;

            blocks[1].x = nx1;
            blocks[1].y = ny1;

            blocks[3].x = nx3;
            blocks[3].y = ny3;
        }

        this.Show();
    }

    boolean getStop()
    {
        return stop;
    }

    boolean getHit()
    {
        return hit;
    }

    int getColour()
    {
        return colour;
    }
}
