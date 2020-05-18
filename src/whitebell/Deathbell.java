package whitebell;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Deathbell implements ActionListener, MouseListener
{

	public final static int COLUMNS = 3, ROWS = 3, TILE_WIDTH = 250, TILE_HEIGHT = 300;

	public static Deathbell dttwt;

	public ArrayList<Bell> bells;

	public Main renderer;

	public Random random;

	public int score, milSecDelay;

	public boolean gameOver;

	public Deathbell()
	{
		JFrame frame = new JFrame("Don't Touch The White Tile!");
		Timer timer = new Timer(20, this);

		renderer = new Main();
		random = new Random();

		frame.setSize(TILE_WIDTH * COLUMNS, TILE_HEIGHT * ROWS);
		frame.add(renderer);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addMouseListener(this);
		frame.setResizable(false);

		start();

		timer.start();
	}

	public void start()
	{
		score = 0;
		gameOver = false;
		bells = new ArrayList<Bell>();

		for (int x = 0; x < COLUMNS; x++)
		{
			for (int y = 0; y < ROWS; y++)
			{
				boolean canBeBlack = true;

				for (Bell tile : bells)
				{
					if (tile.y == y && tile.black)
					{
						canBeBlack = false;
					}
				}

				if (!canBeBlack)
				{
					bells.add(new Bell(x, y, false));
				}
				else
				{
					bells.add(new Bell(x, y, random.nextInt(2) == 0 || x == 2));
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		renderer.repaint();

		for (int i = 0; i < bells.size(); i++)
		{
			Bell tile = bells.get(i);

			if (tile.animateY < 0)
			{
				tile.animateY += TILE_HEIGHT / 5;
			}
		}

		milSecDelay++;
	}

	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, TILE_WIDTH * COLUMNS, TILE_HEIGHT * ROWS);

		g.setFont(new Font("Arial", 1, 100));

		if (!gameOver)
		{
			for (Bell tile : bells)
			{
				g.setColor(tile.black ? Color.BLACK : Color.WHITE);
				g.fillRect(tile.x * TILE_WIDTH, tile.y * TILE_HEIGHT + tile.animateY, TILE_WIDTH, TILE_HEIGHT);
				g.setColor(tile.black ? Color.WHITE : Color.BLACK);
				g.drawRect(tile.x * TILE_WIDTH, tile.y * TILE_HEIGHT + tile.animateY, TILE_WIDTH, TILE_HEIGHT);
			}

			g.setColor(Color.RED);
			g.drawString(String.valueOf(score), TILE_WIDTH, 100);
		}
		else
		{
			g.setColor(Color.BLACK);
			g.drawString("Game Over!", 100, TILE_HEIGHT);
		}
	}

	public static void main(String[] args)
	{
		dttwt = new Deathbell();
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		boolean clicked = false;

		if (!gameOver)
		{
			for (int i = 0; i < bells.size(); i++)
			{
				Bell tile = bells.get(i);

				if (tile.pointInTile(e.getX(), e.getY()) && !clicked)
				{
					if (e.getY() > TILE_HEIGHT * (ROWS - 1))
					{
						if (tile.black)
						{
							for (int j = 0; j < bells.size(); j++)
							{
								if (bells.get(j).y == ROWS)
								{
									bells.remove(j);
								}

								bells.get(j).y++;
								bells.get(j).animateY -= TILE_HEIGHT;
							}

							score += Math.max(100 - milSecDelay, 10);

							System.out.println("You've scored " + Math.max(100 - milSecDelay, 10) + " points!");

							milSecDelay = 0;

							boolean canBeBlack = true;

							for (int x = 0; x < COLUMNS; x++)
							{
								boolean black = random.nextInt(2) == 0 || x == COLUMNS - 1;

								Bell newTile = null;

								if (canBeBlack && black)
								{
									newTile = new Bell(x, 0, true);
									canBeBlack = false;
								}
								else
								{
									newTile = new Bell(x, 0, false);
								}

								newTile.animateY -= TILE_HEIGHT;

								bells.add(newTile);
							}
						}
						else
						{
							gameOver = true;
						}

						clicked = true;
					}
					else
					{
						gameOver = true;
					}
				}
			}
		}
		else
		{
			start();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}
}