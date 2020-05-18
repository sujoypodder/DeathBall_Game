package whitebell;


public class Bell
{

	public int x, y;

	public int animateY;

	public boolean black;

	public Bell(int x, int y, boolean black)
	{
		this.x = x;
		this.y = y;
		this.black = black;
	}

	public boolean pointInTile(int x, int y)
	{
		int width = Deathbell.TILE_WIDTH;
		int height = Deathbell.TILE_HEIGHT;
		
		return x > this.x * width && x < this.x * width + width && y > this.y * height && y < this.y * height + height;
	}

}