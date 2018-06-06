import app.BookingSystem;
import dao.TableDAO;
import staffUI.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        MainWindow window1=new MainWindow("餐馆预约");
        BookingSystem.addObserver(window1);
     
    }
}
