/**
 * 
 */
package sc.plugin2010.renderer.twodimensional;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author ffi
 * 
 */
@SuppressWarnings("serial")
public class QuestionPanel extends JPanel
{
	private FrameRenderer	obs;
	private String			type;

	private final String	FONTTYPE	= "New Courier";
	private final int		SIZE		= 12;

	public QuestionPanel(String question, List<String> answers,
			FrameRenderer obs, String type)
	{

		Color bg = new Color(255, 255, 255, 120);

		setBackground(bg);

		this.type = type;

		this.obs = obs;

		AnswerListener awListener = new AnswerListener();

		JPanel buttonPanel = new TransparentPanel();

		FlowLayout buttonLayout = new FlowLayout();

		buttonPanel.setLayout(buttonLayout);

		int width = 50;

		// add Buttons with answers
		for (int i = 0; i < answers.size(); i++)
		{
			JButton jbut = new JButton(answers.get(i));
			jbut.setName(answers.get(i));
			jbut.addMouseListener(awListener);
			buttonPanel.add(jbut);
			width += jbut.getPreferredSize().width;
		}

		BorderLayout dialogLayout = new BorderLayout();
		setLayout(dialogLayout);

		JLabel textLabel = new JLabel(question, JLabel.CENTER);

		textLabel.setFont(new Font(FONTTYPE, Font.BOLD, SIZE));

		this.add(textLabel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);

		setSize(width, 200);

		setVisible(true);
	}

	private class AnswerListener extends MouseAdapter
	{
		@Override
		public void mouseReleased(MouseEvent e)
		{
			if (e.getButton() == MouseEvent.BUTTON1)
			{
				obs.answerQuestion(e.getComponent().getName(), type);
				// dispose();
			}
		}
	}
}
