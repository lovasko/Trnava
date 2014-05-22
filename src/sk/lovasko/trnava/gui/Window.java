package sk.lovasko.trnava.gui;

import sk.lovasko.trnava.renderer.Renderer;
import sk.lovasko.trnava.renderer.SerialRenderer;
import sk.lovasko.trnava.renderer.ParallelRenderer;
import sk.lovasko.trnava.strategy.Strategy;
import sk.lovasko.trnava.strategy.SineStrategy;
import sk.lovasko.trnava.strategy.HighContrastStrategy;
import sk.lovasko.trnava.strategy.GradientStrategy;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import javax.swing.*;

private final class DetailListener implements ActionListener
{
	private final int detail;
	private final Window window;

	public
	DetailListener (final int detail, final Window window)
	{
		this.detail = detail;
		this.window = window;
	}

	public void 
	actionPerformed (ActionEvent ae) 
	{
		window.max_limit = detail;
		window.recompute();
	}
}

public class Window extends JFrame 
{
	private boolean show_rect;
	private int mouse_x;
	private int mouse_y;
	private int radius;

	private double minx; 
	private double miny;
	private double maxx; 
	private double maxy;
	public int max_limit;

	private Component component;
	private Canvas canvas;
	private BufferStrategy buffer_strategy;

	private SerialRenderer serial_renderer;
	private ParallelRenderer parallel_renderer;
	private Renderer renderer;

	private SineStrategy sine_strategy;
	private HighContrastStrategy high_contrast_strategy;
	private GradientStrategy gradient_strategy;
	private Strategy strategy;

	private SolidPalette white_black_solid_palette;
	private Palette palette;

	private JMenuBar menu_bar;
	private JMenu renderer_menu;
	private JMenu detail_menu; 
	private JMenu strategy_menu; 
	private JMenu palette_menu;

	private JRadioButtonMenuItem serial_renderer_item;
	private JRadioButtonMenuItem parallel_renderer_item;

	@Override
	public final void 
	paint(Graphics g) 
	{
		g = buffer_strategy.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;

		super.paint(g);
		if (show_rect) 
		{
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
			    0.5f));
			g2d.setBackground(new Color(255, 170, 0, 128));
			g2d.setColor(Color.yellow);
			g2d.fillRect(mouse_x - radius / 2 * 3, mouse_y - radius, radius * 3, 
					radius * 2);
		}

		buffer_strategy.show();
	}

	private final void
	set_defaults ()
	{
		minx = -2.0;
		miny = -1.0;
		maxx = 1.0;
		maxy = 1.0;
		max_limit = 50;
		renderer = serial_renderer;
		strategy = gradient_strategy;
		palette = white_black_solid_palette;
		radius = 50;
		mouse_x = 0;
		mouse_y = 0;
		show_rect = true;

		serial_renderer_item.setSelected(true);
		gradient_strategy_item.setSelected(true);
		detail_low_item.setSelected(true);
	}

	private final void
	init_menubar ()
	{
		menu_bar = new JMenuBar();
		renderer_menu = new JMenu("Renderer");
		detail_menu = new JMenu("Detail");
		strategy_menu = new JMenu("Paint strategy");
		palette_menu = new JMenu("Color palette");

		menu_bar.add(renderer_menu);
		menu_bar.add(detail_menu);
		menu_bar.add(strategy_menu);
		menu_bar.add(palette_menu);

		menu_bar.setVisible(true);
		setJMenuBar(menu_bar);
	}

	private final void
	init_renderer_menu ()
	{
		serial_renderer_item = new JRadioButtonMenuItem("Serial");
		parallel_renderer_item = new JRadioButtonMenuItem("Parallel");

		ButtonGroup renderer_group = new ButtonGroup();
		renderer_group.add(serial_renderer_item);
		renderer_group.add(parallel_renderer_item);

		renderer_menu.add(serial_renderer_item);
		renderer_menu.add(parallel_renderer_item);

		serial_renderer_item.addActionListener(new ActionListener() 
		{
			public void 
			actionPerformed (ActionEvent ae) 
			{
				renderer = serial_renderer;
				recompute();
			}
		}); 

		parallel_renderer_item.addActionListener(new ActionListener() 
		{
			public void 
			actionPerformed (ActionEvent ae) 
			{
				renderer = parallel_renderer;
				recompute();
			}
		}); 
	}

	private final void
	init_detail_menu ()
	{
		detail_very_low_item = new JRadioButtonMenuItem("Very low");
		detail_low_item = new JRadioButtonMenuItem("Low");
		detail_medium_item = new JRadioButtonMenuItem("Medium");
		detail_high_item = new JRadioButtonMenuItem("High");
		detail_very_high_item = new JRadioButtonMenuItem("Very high");
		detail_ultra_high_item = new JRadioButtonMenuItem("Ultra high");

		ButtonGroup detail_group = new ButtonGroup();
		detail_group.add(detail_very_low_item);
		detail_group.add(detail_low_item);
		detail_group.add(detail_medium_item);
		detail_group.add(detail_high_item);
		detail_group.add(detail_very_high_item);
		detail_group.add(detail_ultra_high_item);

		detail_menu.add(detail_very_low_item);
		detail_menu.add(detail_low_item);
		detail_menu.add(detail_medium_item);
		detail_menu.add(detail_high_item);
		detail_menu.add(detail_very_high_item);
		detail_menu.add(detail_ultra_high_item);

		detail_very_low_item.addActionListener(new DetailListener(15, this));
		detail_low_item.addActionListener(new DetailListener(50, this));
		detail_medium_item.addActionListener(new DetailListener(250, this));
		detail_high_item.addActionListener(new DetailListener(500, this));
		detail_very_high_item.addActionListener(new DetailListener(5000, this));
		detail_ultra_high_item.addActionListener(new DetailListener(10000, this));
	}

	private final void
	init_strategy_menu ()
	{
		high_contrast_strategy_item = new JRadioButtonMenuItem("High contrast");
		gradient_strategy_item = new JRadioButtonMenuItem("Jump distance gradient");
		sine_strategy_item = new JRadioButtonMenuItem("Sine of distance");

		ButtonGroup strategy_group = new ButtonGroup();
		strategy_group.add(serial_item);
		strategy_group.add(parallel_item);

		strategy_menu.add(serial_item);
		strategy_menu.add(parallel_item);
	}

	private final void
	init_renderers ()
	{
		renderer = null;
		serial_renderer = new SerialRenderer();
		parallel_renderer = new ParallelRenderer(4, new Dimension(60, 40));
	}

	private final void
	init_strategies ()
	{
		strategy = null;
		sine_strategy = new SineStrategy();
		high_contrast_strategy = new HighContrastStrategy();
		gradient_strategy = new GradientStrategy();
	}

	private final void
	initialize ()
	{
		component = getGlassPane();
		setTitle("Mandelbrot set");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		createBufferStrategy(2);
		buffer_strategy = getBufferStrategy();
		enableEvents(
		    AWTEvent.MOUSE_EVENT_MASK | 
		    AWTEvent.MOUSE_MOTION_EVENT_MASK | 
		    AWTEvent.MOUSE_WHEEL_EVENT_MASK);
	}

	public 
	Window () 
	{
		initialize();

		init_menubar();
		init_renderer_menu();
		init_detail_menu();
		init_strategy_menu();
		init_palette_menu();

		init_renderers();
		init_strategies();
		init_palettes();


	private final void
	init_palettes ()
	{
		palette = null;	
		white_black_solid_palette = new SolidPalette(Color.black.toRGB(),
		    Color.white.toRGB());
	}
		set_defaults();

		canvas = new Canvas();
		add(canvas);
		setSize(600, 400 + getJMenuBar().getHeight() + getInsets().top);

		addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent me) {
                if (me.getButton() == java.awt.event.MouseEvent.BUTTON1 && show_rect == true) {
                    int dx, dy;
                    dx = 0;
                    dy = getInsets().top + getJMenuBar().getHeight();
                    double ix = minx, ax = maxx, iy = miny, ay = maxy;
                    int startx = mouse_x - radius / 2 * 3;
                    int endx = mouse_x + radius / 2 * 3;
                    int starty = mouse_y - radius;
                    int endy = mouse_y + radius;

                    minx = ix + ((double) (startx - dx) * (ax - ix) / 600.0);
                    miny = iy + ((double) (starty - dy) * (ay - iy) / 400.0);
                    maxx = ix + ((double) (endx - dx) * (ax - ix) / 600.0);
                    maxy = iy + ((double) (endy - dy) * (ay - iy) / 400.0);


                    c.render(minx, miny, maxx, maxy, limit);

                    repaint();
                } else if (me.getButton() == java.awt.event.MouseEvent.BUTTON2) {
                    show_rect = !show_rect;
                    repaint();
                }

            }

            public void mousePressed(MouseEvent me) {
            }

            public void mouseReleased(MouseEvent me) {
            }

            public void mouseEntered(MouseEvent me) {
            }

            public void mouseExited(MouseEvent me) {
            }
        });

        addMouseMotionListener(new MouseMotionListener() {

            public void mouseDragged(MouseEvent me) {
            }

            public void mouseMoved(MouseEvent me) {
                mouse_x = me.getX();
                mouse_y = me.getY();

                if (mouse_x - radius / 2 * 3 < 0) {
                    mouse_x = 0 + radius / 2 * 3;
                }
                if (mouse_y - radius < getJMenuBar().getHeight() + getInsets().top) {
                    mouse_y = getInsets().top + getJMenuBar().getHeight() + radius;
                }
                
                if (mouse_x + radius / 2 * 3 > getWidth()) {
                    mouse_x = getWidth() - radius / 2 * 3;
                }
                if (mouse_y + radius > getHeight()) {
                    mouse_y = getHeight() - radius;
                }

                repaint();
            }
        });

        addMouseWheelListener(new MouseWheelListener() {

            public void mouseWheelMoved(MouseWheelEvent mwe) {
                radius += mwe.getWheelRotation() * 4;
                if (radius > 180) {
                    radius = 180;
                }
                if (radius < 20) {
                    radius = 20;
                }

                if (mouse_x - radius / 2 * 3 < 0) {
                    mouse_x = 0 + radius / 2 * 3;
                }
                if (mouse_y - radius < 0) {
                    mouse_y = 0 + radius;
                }
                if (mouse_x + radius / 2 * 3 > getWidth()) {
                    mouse_x = getWidth() - radius / 2 * 3;
                }
                if (mouse_y + radius > getHeight()) {
                    mouse_y = getHeight() - radius;
                }

                repaint();
            }
        });

        c.render(minx, miny, maxx, maxy, limit);
        repaint();
    }
}
