package Service;

public interface userService {
    /**
     * &#064;Param  传入的用户名称
     * &#064;Autor  FBCD
     * &#064;Decription  检索用户是否在数据库中的逻辑插口  */
    boolean isElements(String name);
}
