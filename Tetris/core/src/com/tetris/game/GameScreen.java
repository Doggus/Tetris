package com.tetris.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.Random;

public class GameScreen implements Screen,ApplicationListener
{
    private SpriteBatch batch; //batch is used to draw textures to the screen
    private BitmapFont font; //BitmapFont is used to draw text
    private BitmapFont Cfont;

    private Texture img;
    private Texture border; // Textures are variable to load images into
    private Texture Gblock;
    private Texture Rblock;
    private Texture Yblock;
    private Texture Pblock;
    private Texture Bblock;
    private Texture background;
    private Texture Score;

    private int grid[][] = new int[14][24];  //basic grid which the game is played on (game grid is actually 10x20)
    private Block[] b = new Block[4];
    private Block[] bb = new Block[4];
    private TetrisPiece tp; //tetris piece object

    private Block myblock1;
    private Block myblock2;
    private Block myblock3;
    private Block myblock4;

    private Block block1;
    private Block block2;
    private Block block3;
    private Block block4;

    private Random r = new Random();
    private long frame = 0;

    private float time = System.nanoTime(); //used to do time calculations
    private int speed = 0;
    private int score = 0;
    private int pre = 0;

    private Music music;
    private Sound PieceRotateFail;
    private Sound PieceRotateLR;
    private Sound SpecialLineClearSingle;
    private Sound SpecialLineClearDouble;
    private Sound SpecialLineClearTriple;
    private Sound SpecialLineClearQuadruple;
    private Sound PieceFall;
    private Sound PieceHardDrop;//
    private Sound PieceMoveLR;
    private Sound PieceTouchLR;

    public GameScreen(Game g)
    {
        batch = new SpriteBatch(); //batch is what libgdx uses to draw things
        font = new BitmapFont(); //for displaying text or variables
        font.setColor(Color.RED); //sets text colour
        Cfont = new BitmapFont();
        Cfont.setColor(Color.VIOLET);

        img = new Texture("grid.png");
        border = new Texture("new_border.png");
        Gblock = new Texture("green_block.png");
        Rblock = new Texture("red_block.png");
        Yblock = new Texture("yellow_block.png");
        Pblock = new Texture("purple_block.png");
        Bblock = new Texture("blue_block.png");
        background = new Texture("Arcade_background.jpg");
        Score = new Texture("rsz_Score.jpg");

        int bran = r.nextInt(7); //random block
        if (bran == 0) //line piece
        {
            myblock1 = new Block(grid, 6, 21);
            myblock2 = new Block(grid, 6, 20); //pivot
            myblock3 = new Block(grid, 6, 19);
            myblock4 = new Block(grid, 6, 18);
        } else if (bran == 1) // T piece
        {
            myblock1 = new Block(grid, 5, 21);
            myblock2 = new Block(grid, 6, 21); //pivot
            myblock3 = new Block(grid, 6, 20);
            myblock4 = new Block(grid, 7, 21);
        } else if (bran == 2) // L piece
        {
            myblock1 = new Block(grid, 6, 21);
            myblock2 = new Block(grid, 6, 20); //pivot
            myblock3 = new Block(grid, 6, 19);
            myblock4 = new Block(grid, 7, 19);
        } else if (bran == 3) //Z piece
        {
            myblock1 = new Block(grid, 5, 21);
            myblock2 = new Block(grid, 6, 21); //pivot
            myblock3 = new Block(grid, 6, 20);
            myblock4 = new Block(grid, 7, 20);
        } else if (bran == 4) //reverse L piece
        {
            myblock1 = new Block(grid, 7, 21);
            myblock2 = new Block(grid, 7, 20);//pivot
            myblock3 = new Block(grid, 7, 19);
            myblock4 = new Block(grid, 6, 19);
        } else if (bran == 5) //reverse Z piece
        {
            myblock1 = new Block(grid, 7, 21);
            myblock2 = new Block(grid, 6, 21); //pivot
            myblock3 = new Block(grid, 6, 20);
            myblock4 = new Block(grid, 5, 20);
        } else if (bran == 6) // square piece
        {
            myblock1 = new Block(grid, 6, 21);
            myblock2 = new Block(grid, 7, 21); //pivot
            myblock3 = new Block(grid, 6, 20);
            myblock4 = new Block(grid, 7, 20);
        }

        b[0] = myblock1;
        b[1] = myblock2;
        b[2] = myblock3;
        b[3] = myblock4;

        tp = new TetrisPiece(grid, b);

        //setting boundaries(1) and playable field(0) and surrounding unplayable field(-1)
        for (int y = 0; y < 24; y++) {
            for (int x = 0; x < 14; x++) {
                if (x == 1 || x == 12 || y == 1 || y == 22)
                {
                    grid[x][y] = 1;
                }
                else if(x==0 || x==13 || y ==0 || y== 23)
                {
                    grid[x][y] = -1;
                }
                else {
                    grid[x][y] = 0;
                }

            }
        }

        tp.Show(); //creates initial block
        create(); //creates music + sound effects
        play(); //plays music

    }

    @Override
    public void show() {
    }


    @Override
    public void render(float delta)
    {

        //Repeating blocks
        if (tp.getStop() == true)
        {
            //used to speed up game with every block
            if (speed!=1000) {
                speed += 5;
            }

            //if same shape is chosen randomise again
            int ran = r.nextInt(7);
            if (ran==pre)
            {
                ran = r.nextInt(7);
            }
            pre = ran;
            //---------------------------------------

            if (ran == 0) //line piece
            {
                block1 = new Block(grid, 6, 21);
                block2 = new Block(grid, 6, 20); //pivot
                block3 = new Block(grid, 6, 19);
                block4 = new Block(grid, 6, 18);
            } else if (ran == 1) // T piece
            {
                block1 = new Block(grid, 5, 21);
                block2 = new Block(grid, 6, 21); //pivot
                block3 = new Block(grid, 6, 20);
                block4 = new Block(grid, 7, 21);
            } else if (ran == 2) // L piece
            {
                block1 = new Block(grid, 6, 21);
                block2 = new Block(grid, 6, 20); //pivot
                block3 = new Block(grid, 6, 19);
                block4 = new Block(grid, 7, 19);
            } else if (ran == 3) //Z piece
            {
                block1 = new Block(grid, 5, 21);
                block2 = new Block(grid, 6, 21); //pivot
                block3 = new Block(grid, 6, 20);
                block4 = new Block(grid, 7, 20);
            } else if (ran == 4) //reverse L piece
            {
                block1 = new Block(grid, 7, 21);
                block2 = new Block(grid, 7, 20);//pivot
                block3 = new Block(grid, 7, 19);
                block4 = new Block(grid, 6, 19);
            } else if (ran == 5) //reverse Z piece
            {
                block1 = new Block(grid, 7, 21);
                block2 = new Block(grid, 6, 21); //pivot
                block3 = new Block(grid, 6, 20);
                block4 = new Block(grid, 5, 20);
            } else if (ran == 6) // square piece
            {
                block1 = new Block(grid, 6, 21);
                block2 = new Block(grid, 7, 21); //pivot
                block3 = new Block(grid, 6, 20);
                block4 = new Block(grid, 7, 20);
            }

            bb[0] = block1;
            bb[1] = block2;
            bb[2] = block3;
            bb[3] = block4;

            tp = new TetrisPiece(grid, bb);
            tp.Show();

        }
        //-------------------------------------------------
        //line removal for scoring

        int snd = 0;
        int c = 23;
        for (int y = 2; y < 22; y++)
        {
            if (grid[2][y] == 3 && grid[3][y] == 3 && grid[4][y] == 3 && grid[5][y] == 3 && grid[6][y] == 3 &&
                        grid[7][y] == 3 && grid[8][y] == 3 && grid[9][y] == 3 && grid[10][y] == 3 && grid[11][y] == 3)
            {
                //clears line
                for (int x = 2; x < 12; x++)
                {
                    grid[x][y] = 0;
                }

                //score goes up
                score += 10;

                //used for sound effects
                snd++;

                //makes blocks above cleared line fall down
                for(int yy = y;yy<c;yy++)
                {
                    for (int xx = 2;xx<12;xx++)
                    {
                        grid[xx][yy] = grid[xx][yy+1];

                    }
                    c--; //NB! for above
                }

            }

        }

        //different sound effect depending on lines cleared
        if (snd==1)
        {
            SpecialLineClearSingle.play();
        }
        else if (snd==2)
        {
            SpecialLineClearDouble.play();
        }
        else if (snd==3)
        {
            SpecialLineClearTriple.play();
        }
        else if (snd==4)
        {
            SpecialLineClearQuadruple.play();
        }

        //--------------------------------------------------------------------
        //controls

        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) && tp.getStop() == false) //moves block right when key is pressed
        {
            create();
            tp.MoveRight();
            long v = PieceMoveLR.play();
            PieceMoveLR.setVolume(v,0.4f);
            if (tp.getHit()==true)
            {
                PieceMoveLR.dispose();
                long vf = PieceTouchLR.play();
                PieceTouchLR.setVolume(vf,0.5f);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) && tp.getStop() == false) //moves block left when key is pressed
        {
            create();
            tp.MoveLeft();
            long v = PieceMoveLR.play();
            PieceMoveLR.setVolume(v,0.4f);
            if (tp.getHit()==true)
            {
                PieceMoveLR.dispose();
                long vf = PieceTouchLR.play();
                PieceTouchLR.setVolume(vf,0.5f);
            }
        }

        //moves down when held
        frame++;
        if (frame % 5 == 0) {
            if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            {
                tp.MoveDown();
            }
        }

        //sent straight to the bottom
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
        {
            tp.FallDown();

            if(tp.getStop()==true)
            {
                Long v = PieceHardDrop.play();
                PieceHardDrop.setVolume(v,0.2f);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && tp.getStop() == false) //moves block down when key is pressed
        {
            create();
            tp.Rotate();
            long v = PieceRotateLR.play();
            PieceRotateLR.setVolume(v,0.1f);

            if(tp.getHit() ==true)
            {
                PieceRotateLR.dispose();
                long vf = PieceRotateFail.play();
                PieceRotateFail.setVolume(vf,0.3f);
            }
        }

        if ((System.nanoTime() - time) / (1000-speed) > 500000 && tp.getStop() == false) //dictates speed of falling block
        {                                                          //currently moves block down once every 0.5 seconds
            time = System.nanoTime();
            tp.AutoMoveDown();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q))
        {
            Gdx.app.exit();
            System.out.println("Game was quit");
        }

        //--------------------------------------------------------------
        //sound effect

        if(tp.getStop()==true)
        {
            PieceFall.play(); //sound effect
        }

        //---------------------------------------------------------------
        //drawing

        //Gdx.gl.glClearColor(1, 0, 0, 1); //background of pure colour
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clears visual buffer for each render

        //background
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(background, 0, Gdx.graphics.getHeight()); //background image
        batch.end();

        //displaying controls
        batch.begin();
        String controls = "Controls:";
        String left = "Left Arrow key: Move left";
        String right = "Right Arrow key: Move Right";
        String down = "Down Arrow key: Move Down";
        String up = "Up Arrow key: Rotate";
        String space = "Space bar: Send to bottom";
        String q = "Q key: Quit game";
        Cfont.draw(batch, controls, 10, 455);
        Cfont.draw(batch, left, 10,435);
        Cfont.draw(batch, right, 10,415);
        Cfont.draw(batch, down, 10,395);
        Cfont.draw(batch, up, 10,375);
        Cfont.draw(batch, space, 10,355);
        Cfont.draw(batch, q, 10,335);
        batch.end();

        //drawing score
        batch.begin();
        batch.draw(Score, 535, 335);
        font.getData().setScale(2);

        if (score==0) {
            font.draw(batch, String.valueOf(score), 575, 400);
        }
        else if (score>=10 && score <=90)
        {
            font.draw(batch, String.valueOf(score), 568, 400);
        }
        else if (score>=100 && score <= 990)
        {
            font.draw(batch, String.valueOf(score), 558, 400);
        }
        else
        {
            font.draw(batch, String.valueOf(score), 550, 400);
        }
        batch.end();

        //borders, grid, and blocks
        batch.begin(); //begins drawing things
        for (int y = 1; y < 23; y++)//used to extend length
        {
            for (int x = 1; x < 13; x++) //used to extend width
            {
                if (grid[x][y] == 1) {
                    batch.draw(border, (x * 20) + 200, (y * 20)); //draws the border
                } else if (grid[x][y] == 0) {
                    batch.draw(img, (x * 20) + 200, (y * 20)); //draws the grid
                } else {
                    if (tp.getColour() == 0) {
                        batch.draw(Gblock, (x * 20) + 200, (y * 20)); //draws the block green
                    }

                    if (tp.getColour() == 1) {
                        batch.draw(Rblock, (x * 20) + 200, (y * 20)); //draws the block red
                    }

                    if (tp.getColour() == 2) {
                        batch.draw(Yblock, (x * 20) + 200, (y * 20)); //draws the block yellow
                    }

                    if (tp.getColour() == 3) {
                        batch.draw(Pblock, (x * 20) + 200, (y * 20)); //draws the block purple
                    }

                    if (tp.getColour() == 4) {
                        batch.draw(Bblock, (x * 20) + 200, (y * 20)); //draws the block blue
                    }
                }
            }
        }
        batch.end(); //finish drawing things


    }

    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void hide()
    {
    }

    @Override
    public void dispose()
    {
        music.dispose();

        PieceRotateFail.dispose();
        PieceRotateLR.dispose();
        SpecialLineClearSingle.dispose();
        PieceFall.dispose();
        PieceHardDrop.dispose();
        PieceMoveLR.dispose();
        PieceTouchLR.dispose();
    }

    //---------------------------------------
    //application listener


    @Override
    public void create() { //creates music and sound effects

        music = Gdx.audio.newMusic(Gdx.files.internal("data/Tetrominoes.mp3"));

        PieceRotateFail = Gdx.audio.newSound(Gdx.files.internal("data/SFX_PieceRotateFail.ogg"));
        PieceRotateLR = Gdx.audio.newSound(Gdx.files.internal("data/SFX_PieceRotateLR.ogg"));
        PieceTouchLR = Gdx.audio.newSound(Gdx.files.internal("data/SFX_PieceTouchLR.ogg"));
        SpecialLineClearSingle = Gdx.audio.newSound(Gdx.files.internal("data/SFX_SpecialLineClearSingle.ogg"));
        SpecialLineClearDouble = Gdx.audio.newSound(Gdx.files.internal("data/SFX_SpecialLineClearDouble.ogg"));
        SpecialLineClearTriple = Gdx.audio.newSound(Gdx.files.internal("data/SFX_SpecialLineClearTriple.ogg"));
        SpecialLineClearQuadruple = Gdx.audio.newSound(Gdx.files.internal("data/SFX_PieceHold.ogg"));
        PieceFall = Gdx.audio.newSound(Gdx.files.internal("data/SFX_PieceFall.ogg"));
        PieceHardDrop = Gdx.audio.newSound(Gdx.files.internal("data/SFX_PieceHardDrop.ogg"));
        PieceMoveLR = Gdx.audio.newSound(Gdx.files.internal("data/SFX_PieceMoveLR.ogg"));

    }

    @Override
    public void render()
    {

    }

    public void play()
    {
        music.setLooping(true);
        music.setVolume(0.2f);
        music.play();
    }

}
