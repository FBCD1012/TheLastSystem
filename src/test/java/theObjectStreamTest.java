//import Mapper.blobTestMapper;
//import Utils.MybatisUtils;
//import lombok.SneakyThrows;
//import org.apache.ibatis.session.SqlSession;
//import org.junit.jupiter.api.Test;
//
//import java.io.*;
//import java.util.Arrays;
//
//public class theObjectStreamTest {
//    @Test
//    public void test1(){
//    }
//    //常量无法进行实例化，无论是内部还是外部的常量，都是无法进行实例化操作的
//    @SneakyThrows
//    public static void main(String[] args) {
//        ByteArrayOutputStream byteOut=new ByteArrayOutputStream();
//        InnerContent innerContent = new InnerContent();
//        innerContent.setBicycleId(1).setBicycleName("xds").setBicycleFactory("chengdu").setTheInnerMute(4.17);
//        ObjectOutputStream outputStream=new ObjectOutputStream(byteOut);
//        outputStream.writeObject(innerContent);
//        System.out.println(Arrays.toString(byteOut.toByteArray()));
//
//        //关于字节数组的操作理解，如何实现？
//        String bytes;
//        try (SqlSession session= MybatisUtils.SetAutoCommit(true)){
//            blobTestMapper mapper = session.getMapper(blobTestMapper.class);
//            System.out.println(mapper.insertIntoblogtest(String.valueOf(byteOut)));
//            //没有流式操作转换字节肯定是不行的
//            System.out.println(mapper.selectFromBlogTest());
//            bytes= mapper.selectFromBlogTest();
//        }
//        //中间件的处理操作，如何实现才是必要的，继续debug操作！！！！
//        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes.getBytes());
//        ObjectInputStream objectInputStream=new ObjectInputStream(byteArrayInputStream);
//        InnerContent o = (InnerContent) objectInputStream.readObject();
//        System.out.println(o);
//    }
//    /**
//     * 对象序列化成字节码数据
//     *
//     * @param obj
//     * @return
//     */
//    public static byte[] setSerialize(Object obj) {
//        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream( );
//        ObjectOutputStream oos = null;
//        try {
//            oos = new ObjectOutputStream(byteOutStream);
//            oos.writeObject(obj);
//            oos.close( );
//            return byteOutStream.toByteArray( );
//        } catch (Exception e) {
//            e.printStackTrace( );
//        } finally {
//            try {
//                oos.close( );
//            } catch (IOException e) {
//                e.printStackTrace( );
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 反序列化  字节码文件转对象
//     *
//     * @param bytes
//     * @return
//     */
//    public static Object getSerialize(byte[] bytes) {
//        ByteArrayInputStream byteInStream = new ByteArrayInputStream(bytes);
//        ObjectInputStream ois = null;
//        try {
//            ois = new ObjectInputStream(byteInStream);
//            return ois.readObject( );
//        } catch (Exception e) {
//            e.printStackTrace( );
//        } finally {
//            try {
//                ois.close( );
//            } catch (IOException e) {
//                e.printStackTrace( );
//            }
//        }
//        return null;
//    }
//}
