//package saveobject;
//
//import java.io.BufferedInputStream;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.ObjectInputStream;
//import java.sql.Blob;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//public class ObjectStream {
//    private static Connection conn;                                      //连接
//    private PreparedStatement pres;                                      //PreparedStatement对象
//
//    static{
//        try {
//            Class.forName("com.mysql.jdbc.Driver");              //加载驱动
//            System.out.println("数据库加载成功!!!");
//            String url="jdbc:mysql://localhost:3306/testdb";
//            String user="root";
//            String password="20130436";
//
//            conn=DriverManager.getConnection(url,user,password); //建立连接
//            System.out.println("数据库连接成功!!!");
//        } catch (ClassNotFoundException | SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    /*
//     * 向数据库中的表testobj中插入多个Person对象
//     * params:
//     * 	persons:Person对象list
//     */
//    public void savePerson(List<Person> persons){
//        String sql="insert into objtest(obj) values(?)";
//
//        try {
//            pres=conn.prepareStatement(sql);
//            for(int i=0;i<persons.size();i++){
//                pres.setObject(1, persons.get(i));
//
//                pres.addBatch();                                   //实现批量插入
//            }
//
//            pres.executeBatch();                                      //批量插入到数据库中
//
//            if(pres!=null)
//                pres.close();
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    /*
//     * 从数据库中读出存入的对象
//     * return:
//     * 	list:Person对象列表
//     */
//    public List<Person> getPerson(){
//        List<Person> list=new ArrayList<Person>();
//        String sql="select obj from objtest";
//
//        try {
//            pres=conn.prepareStatement(sql);
//
//            ResultSet res=pres.executeQuery();
//            while(res.next()){
//                Blob inBlob=res.getBlob(1);                             //获取blob对象
//
//                InputStream is=inBlob.getBinaryStream();                //获取二进制流对象
//                BufferedInputStream bis=new BufferedInputStream(is);    //带缓冲区的流对象
//
//                byte[] buff=new byte[(int) inBlob.length()];
//                while(-1!=(bis.read(buff, 0, buff.length))){            //一次性全部读到buff中
//                    ObjectInputStream in=new ObjectInputStream(new ByteArrayInputStream(buff));
//                    Person p=(Person)in.readObject();                   //读出对象
//
//                    list.add(p);
//                }
//
//            }
//        } catch (SQLException | IOException | ClassNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return list;
//    }
//}