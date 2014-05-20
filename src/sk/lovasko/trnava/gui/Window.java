package sk.lovasko.trnava.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import javax.swing.*;

/** Main window class
 * 
 * @author daniel
 */
public class window extends JFrame {

    canvas c;
    int mouse_x, mouse_y, radius;
    double minx, miny, maxx, maxy;
    int limit;
    Component compo;
    boolean show_rect;
    BufferStrategy strategy;

    /** Overriden paint - drawing of semi-transparent yellow rectangle
     * 
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        g = strategy.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        super.paint(g);
        if (show_rect == true) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2d.setBackground(new Color(255, 170, 0, 128));
            g2d.setColor(Color.yellow);
            g2d.fillRect(mouse_x - radius / 2 * 3, mouse_y - radius, radius * 3, radius * 2);
        }

        strategy.show();
    }

    window() throws IOException {
        minx = -2.0;
        miny = -1.0;
        maxx = 1.0;
        maxy = 1.0;
        limit = 50;
        compo = getGlassPane();
        
        setTitle("Mandelbrot set");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar bar = new JMenuBar();
        JMenu renderers = new JMenu("Renderers");
        JMenu detail = new JMenu("Detail");
        JMenu ps = new JMenu("Paint strategy");
        JMenu cp = new JMenu("Color palette");
        
        JRadioButtonMenuItem serial = new JRadioButtonMenuItem("Serial");
        serial.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                c.change_to_serial();
                repaint();
            }
        });
        renderers.add(serial);
        JRadioButtonMenuItem parallel = new JRadioButtonMenuItem("Parallel");
        parallel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                c.change_to_parallel();
                repaint();
            }
        });
        renderers.add(parallel);

        ButtonGroup renderers_group = new ButtonGroup();
        renderers_group.add(serial);
        renderers_group.add(parallel);
        

        JRadioButtonMenuItem limit15 = new JRadioButtonMenuItem("Very low");
        limit15.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                limit = 15;
                c.change_limit(15);
                repaint();
            }
        });
        detail.add(limit15);

        JRadioButtonMenuItem limit50 = new JRadioButtonMenuItem("Low");
        limit50.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                limit = 50;
                c.change_limit(50);
                repaint();
            }
        });
        detail.add(limit50);

        JRadioButtonMenuItem limit250 = new JRadioButtonMenuItem("Medium");
        limit250.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                limit = 250;
                c.change_limit(250);
                repaint();
            }
        });
        detail.add(limit250);


        JRadioButtonMenuItem limit500 = new JRadioButtonMenuItem("High");
        limit500.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                limit = 500;
                c.change_limit(500);
                repaint();
            }
        });
        detail.add(limit500);
        JRadioButtonMenuItem limit5000 = new JRadioButtonMenuItem("Very high");
        limit5000.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                limit = 5000;
                c.change_limit(5000);
                repaint();
            }
        });
        detail.add(limit5000);

        JRadioButtonMenuItem limit10000 = new JRadioButtonMenuItem("Ultra high");
        limit10000.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                limit = 10000;
                c.change_limit(10000);
                repaint();
            }
        });
        detail.add(limit10000);

        ButtonGroup limit_group = new ButtonGroup();
        limit_group.add(limit15);
        limit_group.add(limit50);
        limit_group.add(limit250);
        limit_group.add(limit500);
        limit_group.add(limit5000);
        limit_group.add(limit10000);

               
        JRadioButtonMenuItem contrast = new JRadioButtonMenuItem("High contrast");
        contrast.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                c.change_to_contrast();
                repaint();
            }
        });

        JRadioButtonMenuItem distance = new JRadioButtonMenuItem("Jump distance gradient");
        distance.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                c.change_to_gradient();
                repaint();
            }
        });

        ps.add(contrast);
        ps.add(distance);

        ButtonGroup paint_group = new ButtonGroup();
        paint_group.add(contrast);
        paint_group.add(distance);

        
        JRadioButtonMenuItem blackwhite = new JRadioButtonMenuItem("Black & White");
        blackwhite.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                c.change_to_black_n_white();
                repaint();
            }
        });

        JRadioButtonMenuItem sunshine = new JRadioButtonMenuItem("Sunshine");
        sunshine.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                c.change_to_sunshine();
                repaint();
            }
        });

        cp.add(blackwhite);
        cp.add(sunshine);

        ButtonGroup palette_group = new ButtonGroup();
        palette_group.add(blackwhite);
        palette_group.add(sunshine);

        
        blackwhite.setSelected(true);
        contrast.setSelected(true);
        serial.setSelected(true);
        limit50.setSelected(true);
        
        bar.add(renderers);
        bar.add(detail);
        bar.add(ps);
        bar.add(cp);
        bar.setVisible(true);

        setJMenuBar(bar);


        enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK);
        setResizable(false);
        setVisible(true);
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        show_rect = true;
       
        c = new canvas();
        add(c);
        setSize(600, c.img.getHeight() + getJMenuBar().getHeight() + getInsets().top);

        radius = 50;
        mouse_x = 0;
        mouse_y = 0;


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
