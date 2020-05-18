  
package whitebell;

import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Main extends JPanel
{

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		if (Deathbell.dttwt != null)
		{
			Deathbell.dttwt.render(g);
		}
	}
	
}
