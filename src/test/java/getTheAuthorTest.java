import Mapper.noteInfoMapper;
import Utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

public class getTheAuthorTest {
     @Test
     public static  void Test1(){

     }
    public static void main(String[] args) {
        try(SqlSession sqlsession=MybatisUtils.SetAutoCommit(true)) {
            noteInfoMapper mapper = sqlsession.getMapper(noteInfoMapper.class);
            System.out.println(mapper.getNoteName("dongqing"));
        }
    }
}
