package Utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class MybatisUtils {
    private static final SqlSessionFactory sqlSessionFactory;
    static {
        try {
            sqlSessionFactory=new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("configFile/MybatisConfig.xml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static SqlSession SetAutoCommit(boolean flags){
        return sqlSessionFactory.openSession(flags);
    }
}
