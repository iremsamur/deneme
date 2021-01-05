
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author İREM SAMUR
 */
public class CalisanIslemleri {
    //Veritabanı işlemleri burada yapılır.
    private Connection con = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    public void calisanGuncelle(int id, String yeni_ad,String yeni_soyad,String yeni_departman, String yeni_maas){
        //Güncelleme işlemi için sorgu yazalım
        String sorgu = "Update calisanlar set ad = ? , soyad = ? , departman = ? , maaş = ? where id = ?";
        try {
            // id ye göre o kısımları değiştir anlamında mysql sorgusu yazdık
            preparedStatement = con.prepareStatement(sorgu);
            //soru işareti yerine sırasıyla gelenleri yazarım
            
            preparedStatement.setString(1, yeni_ad);
            preparedStatement.setString(2, yeni_soyad);
            preparedStatement.setString(3, yeni_departman);
            preparedStatement.setString(4, yeni_maas);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();
               
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void calisanSil(int id){
        String sorgu = "Delete From calisanlar where id = ?";
        try {
            preparedStatement = con.prepareStatement(sorgu);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();//Böylece id ye göre çalışan silinir
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    public void calisanEkle(String ad,String soyad,String departman,String maas){
        String sorgu = "Insert Into calisanlar (ad,soyad,departman,maaş) VALUES(?,?,?,?)";//preparedStatement kullandım. id yi otomatik atadığı için gerek yok
        try {
            preparedStatement = con.prepareStatement(sorgu);
            //Şimdi sırasıyla soru işaretleri yerine karşılık geldikleri değerleri veririm
            preparedStatement.setString(1, ad);
            preparedStatement.setString(2, soyad);
            preparedStatement.setString(3, departman);
            preparedStatement.setString(4, maas);
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public boolean girisYap(String kullanici_adi,String parola){
        String sorgu = "Select * From yöneticiler where username = ? and password = ?";
        try {
            preparedStatement = con.prepareStatement(sorgu);//sorguyu preparedStatement a gönderirim
            preparedStatement.setString(1, kullanici_adi);//1.soru işareti yerine kullanici adini gönderir
            preparedStatement.setString(2, parola);//2.soru işareti yerine parola yı atar
            ResultSet rs = preparedStatement.executeQuery();//sorgu sonucu değer varsa ResultSet'e döner yoksa değer döndürmez
            /*1.yolif(rs.next()==false){//Eğer kullanıcı adı ve parola varsa true döner yoksa false bunun kontrolünü yapıp döndürürüz
                return false;
            }
            else{
                return true;
            }
*/
            return rs.next();//2.yol
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
            return false;//Eğer try h içine hiç girmeme ihtimali olduğu için yani bir problem olursa sona bir tane return koymamamız gerekir. Yoksa program hata veriyor
        }
        
    }
    
    public ArrayList<Calisan> calisanlariGetir(){
        //Bu methot bize Calisan türünde bir ArrayList döndürecek.Yani Calisan classı türünde
        ArrayList<Calisan> cikti = new ArrayList<Calisan>();//Buyada cikti referanslı bir ArrayList tanımladım. Calisan Sınıfı türünden
        //Veritabanından aldığım her değeri bu cikti referansına atayacak ve en sonda bı cikti referanslı arraylist i döndürecek
        try {
            
            statement = con.createStatement();//Veritabanından bilgi alabilmek için statement oluşturduk
            String sorgu = "Select * From calisanlar";//calisanlar tablomdan bütün bilgileri bu sorgu ile alır.
            //Bu sorgu sonucu bir ResultSet döndürür
            ResultSet rs = statement.executeQuery(sorgu);//Bu sorguyu ResultSet e dönen sonuçları atarız
            while(rs.next()){
                int id = rs.getInt("id");//getInt ile veritabanından id leri alır id ye atar
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String departman = rs.getString("departman");
                String maas = rs.getString("maaş");
                cikti.add(new Calisan(id,ad,soyad,departman,maas));//Tüm id ad soyad gibi bilgileri add ile cikti arraylistine ekledim
                //Her yeni çalışan bu cikti ya eklenir
                
                
            }
            return cikti;
            
        } catch (SQLException ex) {
            Logger.getLogger(CalisanIslemleri.class.getName()).log(Level.SEVERE, null, ex);
            return null;//Hatayı gidermek için null döndüm
        }
        
        
        
    }
    public CalisanIslemleri(){
        //Veritabanı bağlanma işlemleri için constructor tanımladık. Yani sadece CalisanIslemleri classının veritabanına bağlanmasını sağlarım
        String dbUrl="jdbc:mysql://localhost:3306/deneme?useUnicode=true&characterEncoding=utf8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";//useUnicode=true&characterEncoding=utf8 diyerek türkçe karakterlerde javanın sorun çıkarmasına engel oluruz.
        try {
            con = DriverManager.getConnection(dbUrl,Database.kullanici_adi,Database.parola);//url burada ama kullanici adi ve parola yı Database den aldım.
            System.out.println("Bağlantı sağlandı.");
        } catch (SQLException ex) {
            System.out.println("Bağlantı sağlanamadı.");
        }
        //Bu şekilde burada veritabanına bağlantı sağlamış oluruz.
    }
    
    
}
