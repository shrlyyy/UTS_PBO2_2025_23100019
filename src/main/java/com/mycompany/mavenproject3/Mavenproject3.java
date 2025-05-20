package com.mycompany.mavenproject3;

import javax.swing.*;
import java.awt.*;

public class Mavenproject3 extends JFrame implements Runnable {
    private String text;
    private int x;
    private int width;
    private BannerPanel bannerPanel;
    private JButton addProductButton;

    public Mavenproject3() {

        ProductForm form = new ProductForm();
        this.text = form.getProductBannerText();

        setTitle("WK. STI Chill");
        setSize(600, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel teks berjalan
        bannerPanel = new BannerPanel();
        add(bannerPanel, BorderLayout.CENTER);

        // Tombol "Kelola Produk"
        JPanel bottomPanel = new JPanel();
        addProductButton = new JButton("Kelola Produk");
        bottomPanel.add(addProductButton);
        add(bottomPanel, BorderLayout.SOUTH);

        form.setProductChangeListener(() -> {
            SwingUtilities.invokeLater(() -> {
                updateBannerText(form.getProductBannerText());
            });
        });
    
        addProductButton.addActionListener(e -> {
        form.setVisible(true);
        updateBannerText(form.getProductBannerText());
        });

        setVisible(true);

        Thread thread = new Thread(this);
        thread.start();
    }

    class BannerPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString(text, x, getHeight() / 2);
        }
    }

    @Override
    public void run() {
        width = getWidth();
        x = width;
        while (true) {
            x -= 5;
            if (x + bannerPanel.getFontMetrics(new Font("Arial", Font.BOLD, 18)).stringWidth(text) < 0) {
                x = width;
            }
            bannerPanel.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void updateBannerText(String newText) {
        this.text = newText;
        x = -bannerPanel.getFontMetrics(new Font("Arial", Font.BOLD, 18)).stringWidth(newText);
    }

    public static void main(String[] args) {
        new Mavenproject3();
    }
}
