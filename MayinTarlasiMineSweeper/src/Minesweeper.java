
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Minesweeper extends JFrame implements ActionListener, MouseListener{


    JFrame frame = new JFrame();               
    JButton yenile = new JButton("Yenile");     
    JButton pes = new JButton("Pes");     
    JPanel ButtonPanel = new JPanel();       
    Container alan = new Container();           
    int[][] adet;                             
    JButton[][] buton;                        
    int boyut;
    int diff;                              
    final int Mayın = 10;                        

    

    public Minesweeper(int boyut){
     super("MayınTarlası");                       

     this.boyut = boyut;   
     adet = new int[boyut][boyut];
     buton = new JButton[boyut][boyut];  

     frame.setSize(850,850);                       
     frame.setLayout(new BorderLayout());           
     frame.add(ButtonPanel,BorderLayout.SOUTH);     
     yenile.addActionListener(this);                 
     pes.addActionListener(this);                


     alan.setLayout(new GridLayout(boyut,boyut));    

     for(int a = 0; a < buton.length; a++)
     {
         for(int b = 0; b < buton[0].length; b++)
         {
             buton[a][b] = new JButton();            
             buton[a][b].addActionListener(this);     
             alan.add(buton[a][b]);                  
         }
     }
     

     ButtonPanel.add(yenile);                        
     ButtonPanel.add(pes);       // panele düğme ekleme.

     frame.add(alan,BorderLayout.CENTER);   
     mayınOlustur(boyut);                         //mayın doldurarak oyuna başlamak için çağrı fonksiyonu.

     frame.setLocationRelativeTo(null);      
     frame.setDefaultCloseOperation(EXIT_ON_CLOSE);    
     frame.setVisible(true);

    }
    /**
     * Kullanıcının oyunu kaybetip kaybetmediğini kontrol etme işlevi
     * 
     * Kullanıcı mayına tıkladı
    
     *Kullanıcıya oyunu kaybettiklerini söyleyen bir iletişim kutusu gösterir.
     */
    public void kayıpEtme(int m){

        for(int x = 0; x < boyut; x++)
        {
       for(int y = 0; y < boyut; y++)
       {
            if(buton[x][y].isEnabled())          //bir düğme tıklandığında devre dışı bırakılır
            {
              if(adet[x][y] != Mayın)
              {
                buton[x][y].setText(""+ adet[x][y]);                    
                 }

              else
                {
                  buton[x][y].setText("X");

                }
              buton[x][y].setEnabled(false);
                }
            }
        }
    JOptionPane.showMessageDialog(null, m==1? "Mayına Bastın!":"Pes Ettin",
                                 "Oyun Bitti", JOptionPane.ERROR_MESSAGE);
    } 
    

    public void kazanma() {
       boolean won = true;
       for(int i = 0; i < boyut; i++)
       {
           for(int j = 0; j < boyut; j++)
           {
               if(adet[i][j] != Mayın && buton[i][j].isEnabled())
               {
                   won = false;
               }
           }
       }
       if(won) 
       {
            JOptionPane.showMessageDialog(null,"Kazandın!", "Tebrikler!",
                                          JOptionPane.INFORMATION_MESSAGE);
       }   
    }



    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == yenile)              //resets grid
        {
            for(int x = 0; x < boyut; x++)
            {
                for(int y = 0; y < boyut; y++)
                {
                    buton[x][y].setEnabled(true);
                    buton[x][y].setText("");
                }
            }
            mayınOlustur(30);  //yeni bir oyunu tetikler
        }

        else if(ae.getSource() == pes) 
        {
                   kayıpEtme(0); // 
        }

        else // click was on a cell
        {
         for(int x = 0; x < boyut; x++)
            {
            for( int y = 0; y < boyut; y++)
                    {
               if(ae.getSource() == buton[x][y])
                        {
                            switch (adet[x][y]) {
                       case Mayın:
                  buton[x][y].setForeground(Color.RED);
                    buton[x][y].setIcon(new ImageIcon("")); 
                     kayıpEtme(1);                                    
                       break;
                          case 0:
                       buton[x][y].setText(adet[x][y] +"");
                           buton[x][y].setEnabled(false);
                            ArrayList<Integer> clear = new ArrayList<>();    
                        clear.add(x*100+y);
                               dominoEfekti(clear);
                              kazanma(); 
                           break;
                                default:
                                    buton[x][y].setText(""+adet[x][y]);
                                    buton[x][y].setEnabled(false);
                                    kazanma();                                         
                                    break;
                            }
                        }    
                    }
                }
        }


    }
   
    public void mayınOlustur(int s){
    ArrayList<Integer> list = new ArrayList<>();  
        for(int x = 0; x < s; x++)
        {
       for(int y = 0; y < s; y++)
           {
         list.add(x*100+y);                       
                                                   
       }
        }
        adet = new int[s][s];                  

        for(int a = 0; a < (int)(s * 1.5); a++)
        {
          int choice = (int)(Math.random() * list.size());
        adet [list.get(choice) / 100] [list.get(choice) % 100] = Mayın;    
         list.remove(choice);                                                                          
        }
      
        
        for(int x = 0; x < s; x++)
        {
        for(int y = 0; y < s; y++)
        {
           if(adet[x][y] != Mayın)
          {
             int yanKare = 0;
              if( x > 0 && y > 0 && adet[x-1][y-1] == Mayın) 
                {
                   yanKare++;
               }
               if( y > 0 && adet[x][y-1] == Mayın) 
                {
                    yanKare++;
                }
                if( y < boyut - 1 && adet[x][y+1] == Mayın) 
                {
                    yanKare++;
                }
                if( x < boyut - 1 && y > 0 && adet[x+1][y-1] == Mayın) 
                {
                    yanKare++;
                }
                if( x > 0 && adet[x-1][y] == Mayın)
                {
                    yanKare++;
                }
                if( x < boyut - 1 && adet[x+1][y] == Mayın)
                {
                    yanKare++;
                }
                if( x > 0 && y < boyut - 1 &&adet[x-1][y+1] == Mayın) 
                {
                    yanKare++;
                }
                if( x < boyut - 1 && y < boyut - 1 && adet[x+1][y+1] == Mayın) 
                {
                    yanKare++;
                }
                adet[x][y] = yanKare;                        
            }
           }
        }
    }


    

    public void dominoEfekti(ArrayList<Integer> toClear){
        if(toClear.isEmpty())
            return;                        

        int x = toClear.get(0) / 100;      
        int y = toClear.get(0) % 100;     
        toClear.remove(0);                  
            if(adet[x][y] == 0)
            {                                 

                if( x > 0 && y > 0 && buton[x-1][y-1].isEnabled()) 
                {
                    buton[x-1][y-1].setText(adet[x-1][y-1] + "");
                    buton[x-1][y-1].setEnabled(false);
                    if(adet[x-1][y-1] == 0)
                    {
                        toClear.add((x-1)*100 + (y-1));     
                                                                             
                    }
                }
                if( y > 0 && buton[x][y-1].isEnabled())
                {
                    buton[x][y-1].setText(adet[x][y-1] + "");
                    buton[x][y-1].setEnabled(false);
                    if(adet[x][y-1] == 0)
                    {
                        toClear.add(x*100 + (y-1));
                    }

                }
                if( y < boyut - 1 && buton[x][y+1].isEnabled()) 
                {
                    buton[x][y+1].setText(adet[x][y+1] + "");
                    buton[x][y+1].setEnabled(false);
                    if(adet[x][y+1] == 0)
                    {
                        toClear.add(x*100 + (y+1));
                    }

                }
                if( x < boyut - 1 && y > 0 && buton[x+1][y-1].isEnabled())
                {
                    buton[x+1][y-1].setText(adet[x+1][y-1] + "");
                    buton[x+1][y-1].setEnabled(false);
                    if(adet[x+1][y-1] == 0)
                    {
                        toClear.add((x+1)*100 + (y-1));
                    }

                }
                if( x > 0 && buton[x-1][y].isEnabled()) 
                {
                    buton[x-1][y].setText(adet[x-1][y] + "");
                    buton[x-1][y].setEnabled(false);
                    if(adet[x-1][y] == 0)
                    {
                        toClear.add((x-1)*100 + y);
                    }

                }
                if( x < boyut - 1 && buton[x+1][y].isEnabled())
                {
                    buton[x+1][y].setText(adet[x+1][y] + "");
                    buton[x+1][y].setEnabled(false);
                    if(adet[x+1][y] == 0)
                    {
                        toClear.add((x+1)*100 + y);
                    }

                }
                if( x > 0 && y < boyut - 1 && buton[x-1][y+1].isEnabled()) 
                {
                    buton[x-1][y+1].setText(adet[x-1][y+1] + "");
                    buton[x-1][y+1].setEnabled(false);
                    if(adet[x-1][y+1] == 0)
                    {
                        toClear.add((x-1)*100 + (y+1));
                    }

                }
                if( x < boyut - 1 && y < boyut - 1 && buton[x+1][y+1].isEnabled()) 
                {
                    buton[x+1][y+1].setText(adet[x+1][y+1] + "");
                    buton[x+1][y+1].setEnabled(false);
                    if(adet[x+1][y+1] == 0)
                    {
                        toClear.add((x+1)*100 + (y+1));
                    }

                }
            }
            dominoEfekti(toClear);      
    }

    //Main method.
    public static void main(String[] args){
        new Minesweeper(20); 


    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (SwingUtilities.isRightMouseButton(me)){
          
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    
    }

    @Override
    public void mouseEntered(MouseEvent me) {
       
    }

    @Override
    public void mouseExited(MouseEvent me) {
      
    }

}