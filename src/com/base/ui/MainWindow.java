package com.base.ui;

import com.base.IFilteredActionReceiver;
import com.base.IScreenToCellConverter;
import com.base.Journal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame implements IVisualAdapter {
    public final JButton endTurnBtn = new JButton("End Turn");
    private final WorldMapView view = new WorldMapView();
    private final JTextArea journalText = new JTextArea();
    private final Input input = new Input();
    private IMenuActionListener listener;

    public MainWindow() throws HeadlessException {
        String AuthorInfo = " --- Author: Bartosz Strzelecki, Index: 184529 --- ";
        JMenuBar bar = setupMenuBar();
        setJMenuBar(bar);
        setTitle(AuthorInfo);
        JScrollPane scroll = new JScrollPane(journalText);
        JScrollPane centerPane = new JScrollPane(view);
        getContentPane().add(input);
        getContentPane().add(centerPane, BorderLayout.CENTER);
        getContentPane().add(scroll, BorderLayout.EAST);
        getContentPane().add(endTurnBtn, BorderLayout.PAGE_END);

        endTurnBtn.setBounds(20, 20, 50, 50);
        journalText.setEditable(false);
        journalText.setText(AuthorInfo);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(40, 40, 800, 600);
    }

    public void setCellConverter(IScreenToCellConverter converter) {
        view.setCellConverter(converter);
    }

    public void setEndTurnButtonAction(ActionListener action) {
        endTurnBtn.addActionListener(action);
    }

    public void setOnKeyStrokeAction(IFilteredActionReceiver receiver) {
        input.setActionReceiver(receiver);
    }

    public void setMenuActionListener(IMenuActionListener listener) {
        this.listener = listener;
    }


    public void setOnEntitySpawnAction(IEntitySpawnActionListener receiver) {
        view.setOnEntitySpawnAction(receiver);
    }

    @Override
    public void updateMap(Image img) {
        view.setImage(img);
        repaint();
    }

    @Override
    public void updateJournal(Journal journal) {
        journalText.setText(journal.toString());
    }

    private JMenuBar setupMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File");

        JMenuItem save = new JMenuItem("Save", KeyEvent.VK_S);
        save.addActionListener(e -> {
            FileDialog fd = new FileDialog(getFrames()[0], "Select storage location");
            fd.setMultipleMode(false);
            fd.setMode(FileDialog.SAVE);
            fd.setVisible(true);
            String picked = fd.getFile();
            listener.onSave(picked);
        });
        JMenuItem load = new JMenuItem("Load", KeyEvent.VK_L);
        load.addActionListener(e -> {
            FileDialog fd = new FileDialog(getFrames()[0], "Choose a file");
            fd.setMultipleMode(false);
            fd.setMode(FileDialog.LOAD);
            fd.setVisible(true);
            String picked = fd.getFile();
            listener.onLoad(picked);
        });
        JMenuItem clear = new JMenuItem("Clear");
        clear.addActionListener(c -> listener.onClear());
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));

        file.add(save);
        file.add(load);
        file.add(clear);
        file.add(exit);
        bar.add(file);

        JMenu edit = new JMenu("Edit");

        JMenuItem reSize = new JMenuItem("Resize");
        reSize.addActionListener(e -> {
            JOptionPane sizePicker = new JOptionPane();
            JSlider xSlider = new JSlider();
            JSlider ySlider = new JSlider();
            xSlider.setMinimum(5);
            xSlider.setMaximum(100);
            ySlider.setMinimum(5);
            ySlider.setMaximum(100);

            xSlider.setMajorTickSpacing(10);
            xSlider.setMinorTickSpacing(1);
            xSlider.createStandardLabels(10, 10);
            xSlider.setPaintTicks(true);
            xSlider.setPaintLabels(true);

            ySlider.setMajorTickSpacing(10);
            ySlider.setMinorTickSpacing(1);
            ySlider.createStandardLabels(10, 10);
            ySlider.setPaintTicks(true);
            ySlider.setPaintLabels(true);

            sizePicker.setMessage(new Object[]{"Pick new size.\nX:", xSlider, "\nY:", ySlider});
            JDialog dialog = sizePicker.createDialog(this, "My Slider");
            dialog.setVisible(true);
            listener.onSetSize(xSlider.getValue(), ySlider.getValue());
        });

        JMenuItem switchMap = new JMenuItem("Switch map mode");
        switchMap.addActionListener(e -> listener.onMapChange());

        JMenuItem refit = new JMenuItem("Refit", KeyEvent.VK_R);
        refit.addActionListener(e -> pack());

        edit.add(refit);
        edit.add(switchMap);
        edit.add(reSize);
        bar.add(edit);
        return bar;
    }
}
