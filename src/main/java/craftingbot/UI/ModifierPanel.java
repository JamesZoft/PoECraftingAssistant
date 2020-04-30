package craftingbot.UI;

import craftingbot.Main;
import craftingbot.filtertypes.FilterBase;
import craftingbot.filtertypes.Mod;
import craftingbot.filtertypes.logicgroups.Count;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class ModifierPanel extends JPanel {
     
    public String resourcePath;
    public Main frame;
    public FilterBase filterbase;
    public Mod mod;
    public FilterTypePanel parent;
    
    public ModifierPanel(Main frame, FilterTypePanel parent, FilterBase filterbase, Mod mod)
    {
        String path = "src/main/resources";
        File file = new File(path);
        path = file.getAbsolutePath();
        this.resourcePath = path;
        this.frame = frame;
        this.filterbase = filterbase;
        this.mod = mod;
        this.parent = parent;
        
        Dimension size = new Dimension((int) (parent.getWidth() * 0.912),(int) (40)); // 0.912
        setSize(size);
        setPreferredSize(size);
        setBackground(new Color(60,60,60));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        CloseMPButton cb = new CloseMPButton(this);
        ModLabel ml = new ModLabel(this, mod.name);
        MPMinMax min = new MPMinMax(this, "min", true);
        MPMinMax max = new MPMinMax(this, "max", false);
        
        add(cb, Box.LEFT_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(15,0)), Box.LEFT_ALIGNMENT);
        add(ml, Box.LEFT_ALIGNMENT);
        
        add(Box.createHorizontalGlue());
        
        add(min, Box.RIGHT_ALIGNMENT);
        add(max, Box.RIGHT_ALIGNMENT);
        
        parent.add(this);
    }
}

class CloseMPButton extends JButton {
    public CloseMPButton(ModifierPanel parent)
    {
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(true);
        setOpaque(true);
        setPreferredSize(new Dimension((int) (parent.getWidth() * 0.06),(int) ((32))));
        setBackground(new Color(0,0,0));
        setIcon(new javax.swing.ImageIcon(parent.resourcePath + "/xbuttontransparentsmall.png")); // NOI18N
        setToolTipText("Remove this logic filter");
        addMouseListener(new BackgroundListener(this, new Color(80,80,80), new Color(0,0,0)));
        
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                parent.filterbase.print();
//                parent.parent.modifierpanels.remove(this);
                parent.filterbase.mods.remove(parent.mod);
                FilterTypePanel.reshow();
                parent.filterbase.print();
            }
        };
        addActionListener(actionListener);
    }
}

class ModLabel extends JLabel {
    public ModLabel(ModifierPanel parent, String text)
    {
        setText(text);
        setFont(parent.frame.getNewFont(14));
        setBackground(new Color(255,0,0));
        setForeground(new Color(255,255,255));
        setPreferredSize(new Dimension((int) (parent.getWidth() * 0.74),(int) ((32))));
    }
}

class MPMinMax extends JTextField {
    public String placeholder;
    public boolean isMin; // true = min, false = max;
    
    public MPMinMax(ModifierPanel parent, String placeholder, boolean isMin)
    {
        this.placeholder = placeholder;
        this.isMin = isMin;
        
        setText(placeholder);
        setFont(parent.frame.getNewFont(14));
        setBackground(new Color(0,0,0));
        setForeground(new Color(120,120,120));
        setHorizontalAlignment(SwingConstants.CENTER);
        
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(new Color(255,255,255));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(new Color(120,120,120));
                }
            }
        });
                
        KeyListener keyListener = new KeyListener()
        {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10)
                {
                    parent.requestFocusInWindow();
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        };
        
        addKeyListener(keyListener);
    }
}