/**
 * Created by dsheldon on 5/13/16.
 */

import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;

public class AHS2015_CheckersTournament extends PApplet {

    /**
     * Starting Board
     **/
 //   int [][] startingBoard = AIHelpers.endBoard;
    int [][] startingBoard = AIHelpers.startBoard;
    //int[][] startingBoard = AIHelpers.oddBoard;

    /**
     * Wether or not you want to watch all the moves
     **/
    boolean watchGame = true;

    /**
     * Number of games played between opponents
     **/
    int NUM_GAMES = 50;

    /**
     * The number of draw cycles in between each players turn
     **/
    public int RATE = 10;
    /**
     * Timer to only advance the turn of the game every RATE cycles through draw
     **/
    int timer;

    /**
     * Array of AIs available for tournament
     **/
    public CheckersAI[] players;

    /**
     * Indexes of currently playing players
     **/
    int p1Index, p2Index;

    /**
     * Array [3] of [0] as wins for P1, [1] as ties, and [2] as wins for P2
     **/
    public int[] curScore;

    /**
     * Array of full tournament scores
     **/
    public int[][][] allScores;

    /**
     * The count of games the current 2 players have played
     **/
    public int gameCount;

    /**
     * The visualizer to draw all the elements of the game
     **/
    private CheckersVisualizer vis;

    /**
     * The game of the CheckersGame
     **/
    private CheckersGame game;

    /**
     * Put your AIs into this array to have them compete in the tournament If
     * you want to play against the AI, make a new HumanAI as one of your
     * players new HumanAI (1) if it's going first, and new HumanAI (-1) if going
     * 2nd
     **/
    public CheckersAI[] setPlayers() {
        CheckersAI[] newPlayers = new CheckersAI[4];
        //	newPlayers[0] = new HumanAI(1);
         newPlayers[1] = new RandomAI();
        newPlayers[0] = new RandomAI();
        newPlayers[0] = new MinMaxAI(this, 2);
        newPlayers[1] = new MinMaxAI(this, 4);
        newPlayers[2] = new MinMaxAI(this, 6);
        newPlayers[3] = new MinMaxAI(this, 8);
        return newPlayers;
    }

    /**
     * Run once at the beginning of the program
     **/
    public void setup() {
        size(1200, 500);
        rectMode(PConstants.CENTER);
        imageMode(PConstants.CENTER);
        setupState();

        Board b1 = new Board (5,7,3,2040);
        Board b2 = new Board (startingBoard);


        System.out.println (CheckersGame.boardTo (startingBoard));
        System.out.println (b2.toString());


        System.out.println(CheckersGame.boardTo(CheckersVisualizer.getBoard(5,7,3,2040)));
    }

    public void setupState () {
        vis = new CheckersVisualizer(this);

        players = setPlayers();
        p1Index = 0;
        p2Index = 1;
        allScores = new int[players.length][players.length][3];
        curScore = allScores[p1Index][p2Index];

        if (players.length > 0)
            game = new CheckersGame(startingBoard, players[p1Index],
                    players[p2Index]);
        gameCount = 0;
        timer = millis() + RATE;
    }

    boolean hasPrepped = false;

    /**
     * Run over and over and over again, about 60 times a second
     **/
    public void draw() {
        vis.drawTournament(allScores, players);
        if (game != null) {
            vis.drawScreen(game.board, game.turn, game.winner, game.players,
                    curScore);
            if (game.needsTime()) {
                if (!hasPrepped) {
                    hasPrepped = !hasPrepped;
                    if (watchGame)
                        vis.printThinking();
                } else {
                    game.prepTurn();
                    hasPrepped = !hasPrepped;
                }
            }
            if (watchGame) {
                if (game.isPlaying()) {
                    if (millis() >= timer) {
                        game.takeTurn();
                        timer = millis()+RATE;
                    }
                }
            } else {
                game.playGame();
            }
            if (game.isGameOver()) {
                // Score the Game
                if (game.getWinner() == 1)
                    curScore[0]++;
                else if (game.getWinner() == -1)
                    curScore[2]++;
                else
                    curScore[1]++;
                // Go on to the next game
                gameCount++;
                if (gameCount == NUM_GAMES) { // Swap up opponents if done with
                    // this match up
                    p1Index += (p2Index + 1) / players.length;
                    p2Index = (p2Index + 1) % players.length;
                    gameCount = 0;
                }
                if (p1Index == players.length) {
                    game = null;
                } else {
                    curScore = allScores[p1Index][p2Index];

                    game = new CheckersGame(startingBoard, players[p1Index],
                            players[p2Index]);

                }
            }

        }
    }

    /**
     * Run when a key is pressed, forces the next turn -- used when RATE is set
     * very high and you want to look at each move
     **/
    public void keyPressed() {

        if (keyCode == PConstants.LEFT)
            game.goLeft();
        else if (keyCode == PConstants.RIGHT)
            game.goRight();
        else if (keyCode == PConstants.UP)
            game.goUp();
        else if (keyCode == PConstants.DOWN)
            game.goDown();
        else if (keyCode == ENTER) {
            game.goSelected();
        } else
            timer = millis();
    }

    /**
     * used for HumanAI -- so you can play against your AI
     */
    public void mouseClicked() {
        game.clicked(vis.getCellNum(mouseX, mouseY));
    }

    /**
     * Standard java main routine -- in processing it calls the PApplet.main
     *
     * @param _args in case your program needs some arguments
     */
    public static void main(String _args[]) {
        PApplet.main(new String[]{AHS2015_CheckersTournament.class
                .getName()});
    }
}
