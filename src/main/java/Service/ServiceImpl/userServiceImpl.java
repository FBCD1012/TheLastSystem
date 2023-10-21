package Service.ServiceImpl;

import Enitity.userInfo;
import Mapper.userInfoMapper;
import Service.userService;
import Utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

public class userServiceImpl implements userService {
    @Override
    public boolean isElements(String name) {
        boolean isElements=false;
        try(SqlSession sqlSession=MybatisUtils.SetAutoCommit(true)) {
            userInfoMapper mapper = sqlSession.getMapper(userInfoMapper.class);
            userInfo userName = mapper.getUserName(name);
            if (userName != null) {
                isElements=true;
            }
        }
        return isElements;
    }
}
