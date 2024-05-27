package es.upm.Project.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;

import javax.swing.JButton;
import java.awt.geom.RoundRectangle2D;

public class JFrameBoton extends JButton {
	private String texto;
	private Color color;
	private Font fuente;
	private Color textoColor;
	private boolean presionado;
	private boolean ratonEncima;
	
    public JFrameBoton(String texto, Color color, Font fuente, Color textoColor) {
		super(texto);
		this.texto = texto;
		this.fuente = fuente;
		this.color = color;
		this.textoColor = textoColor;
		setBorder(null);
		
		addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	ratonEncima = false;
                presionado = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                presionado = false;
                repaint();
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
            	ratonEncima = true;
            	repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
            	ratonEncima = false;
            	repaint();
            }
        });
	}
    
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Font getFuente() {
		return fuente;
	}

	public void setFuente(Font fuente) {
		this.fuente = fuente;
	}

	public Color getTextoColor() {
		return textoColor;
	}

	public void setTextoColor(Color textoColor) {
		this.textoColor = textoColor;
	}

	@Override
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color aux = color;
		if (presionado) {
            color = color.darker().darker();
        } else if(ratonEncima) {
        	color = color.darker();
        }
		
	    Graphics2D g2d = (Graphics2D) g.create();

	    RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10);
	    g2d.setColor(color);
	    g2d.fill(roundedRectangle);

	    g2d.setColor(textoColor);
	    g2d.setFont(fuente);

	    int x = (getWidth() - g2d.getFontMetrics().stringWidth(getText())) / 2;
	    int y = (getHeight() - g2d.getFontMetrics().getHeight()) / 2 + g2d.getFontMetrics().getAscent();

	    g2d.drawString(getText(), x, y);

	    color = aux;
	    g2d.dispose();
    }
}
