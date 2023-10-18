package Enitity;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class userInfo {
    Integer count;
    String username;
    String password;
}
